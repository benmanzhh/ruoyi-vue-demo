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
import com.ruoyi.system.domain.DevVendors;
import com.ruoyi.system.service.IDevVendorsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商信息Controller
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
@RestController
@RequestMapping("/system/vendors")
public class DevVendorsController extends BaseController
{
    @Autowired
    private IDevVendorsService devVendorsService;

    /**
     * 查询供应商信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:vendors:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevVendors devVendors)
    {
        startPage();
        List<DevVendors> list = devVendorsService.selectDevVendorsList(devVendors);
        return getDataTable(list);
    }

    /**
     * 导出供应商信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:vendors:export')")
    @Log(title = "供应商信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DevVendors devVendors)
    {
        List<DevVendors> list = devVendorsService.selectDevVendorsList(devVendors);
        ExcelUtil<DevVendors> util = new ExcelUtil<DevVendors>(DevVendors.class);
        util.exportExcel(response, list, "供应商信息数据");
    }

    /**
     * 获取供应商信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:vendors:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devVendorsService.selectDevVendorsById(id));
    }

    /**
     * 新增供应商信息
     */
    @PreAuthorize("@ss.hasPermi('system:vendors:add')")
    @Log(title = "供应商信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevVendors devVendors)
    {
        return toAjax(devVendorsService.insertDevVendors(devVendors));
    }

    /**
     * 修改供应商信息
     */
    @PreAuthorize("@ss.hasPermi('system:vendors:edit')")
    @Log(title = "供应商信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevVendors devVendors)
    {
        return toAjax(devVendorsService.updateDevVendors(devVendors));
    }

    /**
     * 删除供应商信息
     */
    @PreAuthorize("@ss.hasPermi('system:vendors:remove')")
    @Log(title = "供应商信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devVendorsService.deleteDevVendorsByIds(ids));
    }
}
