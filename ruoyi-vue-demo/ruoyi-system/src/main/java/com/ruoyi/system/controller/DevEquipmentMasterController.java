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
import com.ruoyi.system.domain.DevEquipmentMaster;
import com.ruoyi.system.service.IDevEquipmentMasterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备主档Controller
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@RestController
@RequestMapping("/system/master")
public class DevEquipmentMasterController extends BaseController
{
    @Autowired
    private IDevEquipmentMasterService devEquipmentMasterService;

    /**
     * 查询设备主档列表
     */
    @PreAuthorize("@ss.hasPermi('system:master:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevEquipmentMaster devEquipmentMaster)
    {
        startPage();
        List<DevEquipmentMaster> list = devEquipmentMasterService.selectDevEquipmentMasterList(devEquipmentMaster);
        return getDataTable(list);
    }

    /**
     * 导出设备主档列表
     */
    @PreAuthorize("@ss.hasPermi('system:master:export')")
    @Log(title = "设备主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DevEquipmentMaster devEquipmentMaster)
    {
        List<DevEquipmentMaster> list = devEquipmentMasterService.selectDevEquipmentMasterList(devEquipmentMaster);
        ExcelUtil<DevEquipmentMaster> util = new ExcelUtil<DevEquipmentMaster>(DevEquipmentMaster.class);
        util.exportExcel(response, list, "设备主档数据");
    }

    /**
     * 获取设备主档详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:master:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devEquipmentMasterService.selectDevEquipmentMasterById(id));
    }

    /**
     * 新增设备主档
     */
    @PreAuthorize("@ss.hasPermi('system:master:add')")
    @Log(title = "设备主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevEquipmentMaster devEquipmentMaster)
    {
        return toAjax(devEquipmentMasterService.insertDevEquipmentMaster(devEquipmentMaster));
    }

    /**
     * 修改设备主档
     */
    @PreAuthorize("@ss.hasPermi('system:master:edit')")
    @Log(title = "设备主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevEquipmentMaster devEquipmentMaster)
    {
        return toAjax(devEquipmentMasterService.updateDevEquipmentMaster(devEquipmentMaster));
    }

    /**
     * 删除设备主档
     */
    @PreAuthorize("@ss.hasPermi('system:master:remove')")
    @Log(title = "设备主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devEquipmentMasterService.deleteDevEquipmentMasterByIds(ids));
    }
}
