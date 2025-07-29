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
import com.ruoyi.system.domain.DevPersonnel;
import com.ruoyi.system.service.IDevPersonnelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维护人员信息Controller
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
@RestController
@RequestMapping("/system/personnel")
public class DevPersonnelController extends BaseController
{
    @Autowired
    private IDevPersonnelService devPersonnelService;

    /**
     * 查询维护人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:personnel:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevPersonnel devPersonnel)
    {
        startPage();
        List<DevPersonnel> list = devPersonnelService.selectDevPersonnelList(devPersonnel);
        return getDataTable(list);
    }

    /**
     * 导出维护人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:personnel:export')")
    @Log(title = "维护人员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DevPersonnel devPersonnel)
    {
        List<DevPersonnel> list = devPersonnelService.selectDevPersonnelList(devPersonnel);
        ExcelUtil<DevPersonnel> util = new ExcelUtil<DevPersonnel>(DevPersonnel.class);
        util.exportExcel(response, list, "维护人员信息数据");
    }

    /**
     * 获取维护人员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:personnel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devPersonnelService.selectDevPersonnelById(id));
    }

    /**
     * 新增维护人员信息
     */
    @PreAuthorize("@ss.hasPermi('system:personnel:add')")
    @Log(title = "维护人员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevPersonnel devPersonnel)
    {
        return toAjax(devPersonnelService.insertDevPersonnel(devPersonnel));
    }

    /**
     * 修改维护人员信息
     */
    @PreAuthorize("@ss.hasPermi('system:personnel:edit')")
    @Log(title = "维护人员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevPersonnel devPersonnel)
    {
        return toAjax(devPersonnelService.updateDevPersonnel(devPersonnel));
    }

    /**
     * 删除维护人员信息
     */
    @PreAuthorize("@ss.hasPermi('system:personnel:remove')")
    @Log(title = "维护人员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devPersonnelService.deleteDevPersonnelByIds(ids));
    }
}
