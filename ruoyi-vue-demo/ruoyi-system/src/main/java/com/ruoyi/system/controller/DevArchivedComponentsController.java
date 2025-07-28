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
import com.ruoyi.system.domain.DevArchivedComponents;
import com.ruoyi.system.service.IDevArchivedComponentsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备归档历史Controller
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
@RestController
@RequestMapping("/system/components")
public class DevArchivedComponentsController extends BaseController
{
    @Autowired
    private IDevArchivedComponentsService devArchivedComponentsService;

    /**
     * 查询设备归档历史列表
     */
    @PreAuthorize("@ss.hasPermi('system:components:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevArchivedComponents devArchivedComponents)
    {
        startPage();
        List<DevArchivedComponents> list = devArchivedComponentsService.selectDevArchivedComponentsList(devArchivedComponents);
        return getDataTable(list);
    }

    /**
     * 导出设备归档历史列表
     */
    @PreAuthorize("@ss.hasPermi('system:components:export')")
    @Log(title = "设备归档历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DevArchivedComponents devArchivedComponents)
    {
        List<DevArchivedComponents> list = devArchivedComponentsService.selectDevArchivedComponentsList(devArchivedComponents);
        ExcelUtil<DevArchivedComponents> util = new ExcelUtil<DevArchivedComponents>(DevArchivedComponents.class);
        util.exportExcel(response, list, "设备归档历史数据");
    }

    /**
     * 获取设备归档历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:components:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devArchivedComponentsService.selectDevArchivedComponentsById(id));
    }

    /**
     * 新增设备归档历史
     */
    @PreAuthorize("@ss.hasPermi('system:components:add')")
    @Log(title = "设备归档历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevArchivedComponents devArchivedComponents)
    {
        return toAjax(devArchivedComponentsService.insertDevArchivedComponents(devArchivedComponents));
    }

    /**
     * 修改设备归档历史
     */
    @PreAuthorize("@ss.hasPermi('system:components:edit')")
    @Log(title = "设备归档历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevArchivedComponents devArchivedComponents)
    {
        return toAjax(devArchivedComponentsService.updateDevArchivedComponents(devArchivedComponents));
    }

    /**
     * 删除设备归档历史
     */
    @PreAuthorize("@ss.hasPermi('system:components:remove')")
    @Log(title = "设备归档历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devArchivedComponentsService.deleteDevArchivedComponentsByIds(ids));
    }
}
