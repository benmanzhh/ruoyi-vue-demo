package com.ruoyi.system.service.impl;

// 引入所有必需的类
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DevEquipmentComponents;
import com.ruoyi.system.domain.DevEquipmentMaster;
import com.ruoyi.system.mapper.DevEquipmentMasterMapper;
import com.ruoyi.system.service.IDevEquipmentMasterService;

/**
 * 设备主档Service业务层处理
 * * @author ruoyi
 * @date 2025-07-29
 */
@Service
public class DevEquipmentMasterServiceImpl implements IDevEquipmentMasterService
{
    private static final Logger log = LoggerFactory.getLogger(DevEquipmentMasterServiceImpl.class);

    @Autowired
    private DevEquipmentMasterMapper devEquipmentMasterMapper;

    @Override
    public DevEquipmentMaster selectDevEquipmentMasterById(Long id)
    {
        return devEquipmentMasterMapper.selectDevEquipmentMasterById(id);
    }

    @Override
    public List<DevEquipmentMaster> selectDevEquipmentMasterList(DevEquipmentMaster devEquipmentMaster)
    {
        return devEquipmentMasterMapper.selectDevEquipmentMasterList(devEquipmentMaster);
    }

    @Transactional
    @Override
    public int insertDevEquipmentMaster(DevEquipmentMaster devEquipmentMaster)
    {
        devEquipmentMaster.setCreateTime(DateUtils.getNowDate());
        calculateAndSetDerivedDates(devEquipmentMaster);

        int rows = devEquipmentMasterMapper.insertDevEquipmentMaster(devEquipmentMaster);
        insertDevEquipmentComponents(devEquipmentMaster);

        analyzeAndUpdateRulesFromFile(devEquipmentMaster);
        return rows;
    }

    @Transactional
    @Override
    public int updateDevEquipmentMaster(DevEquipmentMaster devEquipmentMaster)
    {
        devEquipmentMaster.setUpdateTime(DateUtils.getNowDate());
        calculateAndSetDerivedDates(devEquipmentMaster);

        devEquipmentMasterMapper.deleteDevEquipmentComponentsByEquipmentId(devEquipmentMaster.getId());
        insertDevEquipmentComponents(devEquipmentMaster);
        int rows = devEquipmentMasterMapper.updateDevEquipmentMaster(devEquipmentMaster);

        analyzeAndUpdateRulesFromFile(devEquipmentMaster);
        return rows;
    }

    @Transactional
    @Override
    public int deleteDevEquipmentMasterByIds(Long[] ids)
    {
        devEquipmentMasterMapper.deleteDevEquipmentComponentsByEquipmentIds(ids);
        return devEquipmentMasterMapper.deleteDevEquipmentMasterByIds(ids);
    }

    @Transactional
    @Override
    public int deleteDevEquipmentMasterById(Long id)
    {
        devEquipmentMasterMapper.deleteDevEquipmentComponentsByEquipmentId(id);
        return devEquipmentMasterMapper.deleteDevEquipmentMasterById(id);
    }

    public void insertDevEquipmentComponents(DevEquipmentMaster devEquipmentMaster)
    {
        List<DevEquipmentComponents> devEquipmentComponentsList = devEquipmentMaster.getDevEquipmentComponentsList();
        Long id = devEquipmentMaster.getId();
        if (StringUtils.isNotNull(devEquipmentComponentsList))
        {
            List<DevEquipmentComponents> list = new ArrayList<DevEquipmentComponents>();
            for (DevEquipmentComponents devEquipmentComponents : devEquipmentComponentsList)
            {
                devEquipmentComponents.setEquipmentId(id);
                list.add(devEquipmentComponents);
            }
            if (list.size() > 0)
            {
                devEquipmentMasterMapper.batchDevEquipmentComponents(list);
            }
        }
    }

    private void calculateAndSetDerivedDates(DevEquipmentMaster devEquipmentMaster)
    {
        if (devEquipmentMaster.getInstallDate() != null)
        {
            LocalDate installDate = devEquipmentMaster.getInstallDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (devEquipmentMaster.getLifespanYears() != null && devEquipmentMaster.getLifespanYears() > 0)
            {
                LocalDate maxLifespanDate = installDate.plusYears(devEquipmentMaster.getLifespanYears());
                devEquipmentMaster.setMaxLifespanDate(Date.from(maxLifespanDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }

            if (devEquipmentMaster.getWarrantyYears() != null && devEquipmentMaster.getWarrantyYears() > 0)
            {
                LocalDate warrantyEndDate = installDate.plusYears(devEquipmentMaster.getWarrantyYears());
                devEquipmentMaster.setWarrantyEndDate(Date.from(warrantyEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        }
    }

    private void analyzeAndUpdateRulesFromFile(DevEquipmentMaster masterRecord) {
        String fileUrl = masterRecord.getRulesFileUrl();
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }

        String extractedContent = extractContentFromPdf(fileUrl);

        if (StringUtils.isNotEmpty(extractedContent)) {
            String truncatedRules = extractedContent.substring(0, Math.min(extractedContent.length(), 10));
            log.info("[维修规范回填] - 为设备ID '{}' 提取内容成功，截取前10位为: '{}'", masterRecord.getId(), truncatedRules);

            DevEquipmentMaster updateObject = new DevEquipmentMaster();
            updateObject.setId(masterRecord.getId());
            updateObject.setMaintenanceRules(truncatedRules);

            devEquipmentMasterMapper.updateDevEquipmentMaster(updateObject);
        }
    }

    private String extractContentFromPdf(String fileUrl) {
        String filePath = RuoYiConfig.getProfile() + fileUrl.replace("/profile", "");
        File pdfFile = new File(filePath);

        if (!pdfFile.exists()) {
            log.warn("[维修规范分析] - 文件不存在: {}", filePath);
            return null;
        }

        log.info("[维修规范分析] - 开始分析文件: {}", filePath);
        // 核心修正: 使用 Arrays.asList 替代 List.of (Java 8 兼容)
        List<String> searchPatterns = Arrays.asList("附件[一二三四五六七八九十]+[\\s:：]*技术规范书", "附件[一二三四五六七八九十]+[\\s:：]*技术规范");

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            Map<String, Object> lastMatch = findLastMatch(document, pdfStripper, searchPatterns);

            if (lastMatch != null) {
                int startPage = (int) lastMatch.get("page");
                String startMarker = (String) lastMatch.get("text");
                log.info("[维修规范分析] - 找到提取起点: '{}' (第 {} 页)", startMarker, startPage);
                return extractAppendixContent(document, pdfStripper, startPage, startMarker);
            } else {
                // 核心修正: 使用标准 warn 方法替代 lambda 写法 (Java 8 兼容)
                log.warn("[维修规范分析] - 未找到匹配的技术规范书附件标题");
            }
        } catch (IOException e) {
            log.error("[维修规范分析] - 读取PDF文件时发生错误", e);
        }
        return null;
    }

    private Map<String, Object> findLastMatch(PDDocument document, PDFTextStripper stripper, List<String> patterns) throws IOException {
        List<Map<String, Object>> allOccurrences = new ArrayList<>();
        for (int pageNum = 1; pageNum <= document.getNumberOfPages(); pageNum++) {
            stripper.setStartPage(pageNum);
            stripper.setEndPage(pageNum);
            String pageText = stripper.getText(document);
            for (String patternString : patterns) {
                Matcher matcher = Pattern.compile(patternString).matcher(pageText);
                if (matcher.find()) {
                    Map<String, Object> matchInfo = new HashMap<>();
                    matchInfo.put("page", pageNum);
                    matchInfo.put("text", matcher.group(0));
                    allOccurrences.add(matchInfo);
                    break;
                }
            }
        }
        return allOccurrences.isEmpty() ? null : allOccurrences.get(allOccurrences.size() - 1);
    }

    private String extractAppendixContent(PDDocument document, PDFTextStripper stripper, int startPage, String startMarker) throws IOException {
        Pattern endMarkerPattern = getNextAppendixMarkerPattern(startMarker);
        StringBuilder extractedContent = new StringBuilder();

        for (int pageNum = startPage; pageNum <= document.getNumberOfPages(); pageNum++) {
            stripper.setStartPage(pageNum);
            stripper.setEndPage(pageNum);
            String pageText = stripper.getText(document);

            if (pageNum > startPage && endMarkerPattern != null && endMarkerPattern.matcher(pageText).find()) {
                break;
            }
            // 核心修正: 使用 .trim() 替代 .strip() (Java 8 兼容)
            String cleanText = (pageNum == startPage) ? pageText.replace(startMarker, "").trim() : pageText.trim();
            extractedContent.append(cleanText).append("\n");
        }
        return extractedContent.toString();
    }

    private Pattern getNextAppendixMarkerPattern(String fullStartMarker) {
        Pattern pattern = Pattern.compile("附件([一二三四五六七八九十]+)");
        Matcher matcher = pattern.matcher(fullStartMarker);
        if (!matcher.find()) return null;

        // 核心修正: 使用传统 HashMap 替代 Map.of (Java 8 兼容)
        Map<String, Integer> chineseNumbers = new HashMap<>();
        chineseNumbers.put("一", 1);
        chineseNumbers.put("二", 2);
        chineseNumbers.put("三", 3);
        chineseNumbers.put("四", 4);
        chineseNumbers.put("五", 5);
        chineseNumbers.put("六", 6);
        chineseNumbers.put("七", 7);
        chineseNumbers.put("八", 8);
        chineseNumbers.put("九", 9);
        chineseNumbers.put("十", 10);

        Map<Integer, String> reverseMapping = new HashMap<>();
        for (Map.Entry<String, Integer> entry : chineseNumbers.entrySet()) {
            reverseMapping.put(entry.getValue(), entry.getKey());
        }

        Integer currentNum = chineseNumbers.get(matcher.group(1));
        if (currentNum == null || currentNum >= 10) return null;
        String nextChinese = reverseMapping.get(currentNum + 1);
        if (nextChinese == null) return null;
        return Pattern.compile("附件" + nextChinese + "[\\s:：]*");
    }
}
