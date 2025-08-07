package com.ruoyi.quartz.task;

import com.google.gson.Gson;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.system.domain.DevMaintenanceBlacklist;
import com.ruoyi.system.service.IDevMaintenanceBlacklistService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 自动同步年度法定节假日到维护黑名单的定时任务
 * (已深度优化春节假期合并与数据去重逻辑)
 *
 * @author ruoyi
 */
@Component("holidayBlacklistSyncTask")
public class HolidayBlacklistSyncTask {

    private static final Logger log = LoggerFactory.getLogger(HolidayBlacklistSyncTask.class);

    @Autowired
    private IDevMaintenanceBlacklistService blacklistService;

    @Autowired
    private ISysJobService jobService;

    /**
     * 任务主入口方法
     * @param jobId 当前任务的ID，需要在RuoYi后台配置中传入
     */
    @Transactional
    public void syncHolidays(Long jobId) {
        int currentYear = LocalDate.now().getYear();
        log.info("开始执行【{}年度节假日同步任务】，当前任务ID: {}", currentYear, jobId);

        // 步骤 1: 从API获取节假日数据
        Map<String, HolidayInfo> holidayApiData = getHolidaysFromApi(currentYear);
        if (holidayApiData == null) {
            log.error("【{}年度节假日同步任务】从API获取数据失败，任务将在下次调度时重试。", currentYear);
            return; // 失败则退出，等待下次重试
        }

        // 步骤 2: 处理API数据，合并春节假期
        List<DevMaintenanceBlacklist> holidaysFromApi = processHolidayData(holidayApiData);
        if (holidaysFromApi.isEmpty()) {
            log.warn("【{}年度节假日同步任务】API未返回任何有效的节假日信息。任务视为成功并停止。", currentYear);
            pauseJob(jobId); // 数据为空也视为完成，停止任务
            return;
        }

        // 步骤 3: 提取所有待处理节假日的“原因”
        List<String> reasonsFromApi = holidaysFromApi.stream()
                .map(DevMaintenanceBlacklist::getReason)
                .collect(Collectors.toList());

        // 步骤 4: 使用精确的原因列表，查询数据库中已存在的记录
        List<DevMaintenanceBlacklist> existingHolidays = blacklistService.selectBlacklistByReasons(reasonsFromApi);
        Set<String> existingHolidayReasons = existingHolidays.stream()
                .map(DevMaintenanceBlacklist::getReason)
                .collect(Collectors.toSet());

        // 步骤 5: 对比并筛选出需要新增的节假日
        List<DevMaintenanceBlacklist> holidaysToInsert = new ArrayList<>();
        for (DevMaintenanceBlacklist newHoliday : holidaysFromApi) {
            // 核心去重逻辑：只有当数据库中不存在相同“设置原因”的记录时，才添加到待插入列表
            if (!existingHolidayReasons.contains(newHoliday.getReason())) {
                holidaysToInsert.add(newHoliday);
            } else {
                log.info("节假日 '{}' 已存在于数据库中，将跳过。", newHoliday.getReason());
            }
        }

        // 步骤 6: 批量插入新的节假日到数据库
        if (!holidaysToInsert.isEmpty()) {
            for (DevMaintenanceBlacklist holiday : holidaysToInsert) {
                blacklistService.insertDevMaintenanceBlacklist(holiday);
            }
            log.info("【{}年度节假日同步任务】成功同步了 {} 个新的节假日到维护黑名单。", currentYear, holidaysToInsert.size());
        } else {
            log.info("【{}年度节假日同步任务】所有节假日均已存在于数据库中，无需新增。");
        }

        // 步骤 7: 任务成功完成，暂停本年度的任务
        log.info("【{}年度节假日同步任务】本年度节假日同步检查已成功完成，任务将自动停止。");
        pauseJob(jobId);
    }

    private Map<String, HolidayInfo> getHolidaysFromApi(int year) {
        String apiUrl = "https://timor.tech/api/holiday/year/" + year;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            request.addHeader("User-Agent", "Mozilla/5.0");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ApiResponse apiResponse = new Gson().fromJson(jsonResponse, ApiResponse.class);

                if (apiResponse != null && apiResponse.getCode() == 0) {
                    return apiResponse.getHoliday();
                } else {
                    log.error("API请求失败，业务错误: {}", apiResponse != null ? apiResponse.getMsg() : "无详细信息");
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("网络请求或JSON解析失败", e);
            return null;
        }
    }

    /**
     * 核心优化：处理API数据，自动合并春节假期
     */
    private List<DevMaintenanceBlacklist> processHolidayData(Map<String, HolidayInfo> holidayData) {
        Map<String, DevMaintenanceBlacklist> processedFestivals = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        new TreeMap<>(holidayData).forEach((dateStr, dayInfo) -> {
            if (dayInfo.isHoliday()) {
                String originalHolidayName = dayInfo.getName();
                String processedHolidayName;

                if (originalHolidayName.contains("春节") || originalHolidayName.contains("除夕") || originalHolidayName.startsWith("初")) {
                    processedHolidayName = "春节";
                } else {
                    processedHolidayName = originalHolidayName;
                }

                LocalDate currentDate = LocalDate.parse(dayInfo.getDate(), formatter);

                processedFestivals.compute(processedHolidayName, (name, holiday) -> {
                    if (holiday == null) {
                        holiday = new DevMaintenanceBlacklist();
                        holiday.setReason(LocalDate.now().getYear() + "年" + name);
                        holiday.setStartDate(DateUtils.localDateToDate(currentDate));
                    }
                    holiday.setEndDate(DateUtils.localDateToDate(currentDate));
                    return holiday;
                });
            }
        });
        return new ArrayList<>(processedFestivals.values());
    }

    private void pauseJob(Long jobId) {
        if (jobId == null) {
            log.warn("任务ID为空，无法自动暂停任务。请手动操作。");
            return;
        }
        try {
            SysJob job = new SysJob();
            job.setJobId(jobId);
            job.setStatus("1"); // 1 表示暂停
            jobService.changeStatus(job);
            log.info("任务ID {} 已被成功暂停。", jobId);
        } catch (Exception e) {
            log.error("自动暂停任务ID {} 失败，请手动操作。", jobId, e);
        }
    }

    // --- 用于GSON解析的内部类 ---
    private static class ApiResponse {
        private int code;
        private String msg;
        private Map<String, HolidayInfo> holiday;
        public int getCode() { return code; }
        public String getMsg() { return msg; }
        public Map<String, HolidayInfo> getHoliday() { return holiday; }
    }

    private static class HolidayInfo {
        private boolean holiday;
        private String name;
        private String date;
        public boolean isHoliday() { return holiday; }
        public String getName() { return name; }
        public String getDate() { return date; }
    }
}