package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DevMaintenanceBlacklist;
import com.ruoyi.system.service.IDevMaintenanceBlacklistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维护作业黑名单Controller
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@RestController
@RequestMapping("/system/blacklist")
public class DevMaintenanceBlacklistController extends BaseController
{
    @Autowired
    private IDevMaintenanceBlacklistService devMaintenanceBlacklistService;

    /**
     * 查询维护作业黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('system:blacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevMaintenanceBlacklist devMaintenanceBlacklist)
    {
        startPage();
        List<DevMaintenanceBlacklist> list = devMaintenanceBlacklistService.selectDevMaintenanceBlacklistList(devMaintenanceBlacklist);
        return getDataTable(list);
    }

    /**
     * 导出维护作业黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('system:blacklist:export')")
    @Log(title = "维护作业黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DevMaintenanceBlacklist devMaintenanceBlacklist)
    {
        List<DevMaintenanceBlacklist> list = devMaintenanceBlacklistService.selectDevMaintenanceBlacklistList(devMaintenanceBlacklist);
        ExcelUtil<DevMaintenanceBlacklist> util = new ExcelUtil<DevMaintenanceBlacklist>(DevMaintenanceBlacklist.class);
        util.exportExcel(response, list, "维护作业黑名单数据");
    }

    /**
     * 获取维护作业黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:blacklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devMaintenanceBlacklistService.selectDevMaintenanceBlacklistById(id));
    }

    /**
     * 新增维护作业黑名单
     */
    @PreAuthorize("@ss.hasPermi('system:blacklist:add')")
    @Log(title = "维护作业黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevMaintenanceBlacklist devMaintenanceBlacklist)
    {
        return toAjax(devMaintenanceBlacklistService.insertDevMaintenanceBlacklist(devMaintenanceBlacklist));
    }

    /**
     * 修改维护作业黑名单
     */
    @PreAuthorize("@ss.hasPermi('system:blacklist:edit')")
    @Log(title = "维护作业黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevMaintenanceBlacklist devMaintenanceBlacklist)
    {
        return toAjax(devMaintenanceBlacklistService.updateDevMaintenanceBlacklist(devMaintenanceBlacklist));
    }

    /**
     * 删除维护作业黑名单
     */
    @PreAuthorize("@ss.hasPermi('system:blacklist:remove')")
    @Log(title = "维护作业黑名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devMaintenanceBlacklistService.deleteDevMaintenanceBlacklistByIds(ids));
    }
}
