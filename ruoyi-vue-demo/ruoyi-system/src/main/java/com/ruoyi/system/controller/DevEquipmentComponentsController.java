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
import com.ruoyi.system.domain.DevEquipmentComponents;
import com.ruoyi.system.service.IDevEquipmentComponentsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备部件Controller
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@RestController
@RequestMapping("/system/equipcomponents")
public class DevEquipmentComponentsController extends BaseController
{
    @Autowired
    private IDevEquipmentComponentsService devEquipmentComponentsService;

    /**
     * 查询设备部件列表
     */
    @PreAuthorize("@ss.hasPermi('system:equipcomponents:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevEquipmentComponents devEquipmentComponents)
    {
        startPage();
        List<DevEquipmentComponents> list = devEquipmentComponentsService.selectDevEquipmentComponentsList(devEquipmentComponents);
        return getDataTable(list);
    }

    /**
     * 导出设备部件列表
     */
    @PreAuthorize("@ss.hasPermi('system:equipcomponents:export')")
    @Log(title = "设备部件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DevEquipmentComponents devEquipmentComponents)
    {
        List<DevEquipmentComponents> list = devEquipmentComponentsService.selectDevEquipmentComponentsList(devEquipmentComponents);
        ExcelUtil<DevEquipmentComponents> util = new ExcelUtil<DevEquipmentComponents>(DevEquipmentComponents.class);
        util.exportExcel(response, list, "设备部件数据");
    }

    /**
     * 获取设备部件详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:equipcomponents:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devEquipmentComponentsService.selectDevEquipmentComponentsById(id));
    }

    /**
     * 新增设备部件
     */
    @PreAuthorize("@ss.hasPermi('system:equipcomponents:add')")
    @Log(title = "设备部件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevEquipmentComponents devEquipmentComponents)
    {
        return toAjax(devEquipmentComponentsService.insertDevEquipmentComponents(devEquipmentComponents));
    }

    /**
     * 修改设备部件
     */
    @PreAuthorize("@ss.hasPermi('system:equipcomponents:edit')")
    @Log(title = "设备部件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevEquipmentComponents devEquipmentComponents)
    {
        return toAjax(devEquipmentComponentsService.updateDevEquipmentComponents(devEquipmentComponents));
    }

    /**
     * 删除设备部件
     */
    @PreAuthorize("@ss.hasPermi('system:equipcomponents:remove')")
    @Log(title = "设备部件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devEquipmentComponentsService.deleteDevEquipmentComponentsByIds(ids));
    }
}
