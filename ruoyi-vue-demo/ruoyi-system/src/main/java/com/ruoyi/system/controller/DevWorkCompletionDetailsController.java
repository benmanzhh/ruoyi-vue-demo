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
import com.ruoyi.system.domain.DevWorkCompletionDetails;
import com.ruoyi.system.service.IDevWorkCompletionDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维护作业完成情况详情Controller
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
@RestController
@RequestMapping("/system/details")
public class DevWorkCompletionDetailsController extends BaseController
{
    @Autowired
    private IDevWorkCompletionDetailsService devWorkCompletionDetailsService;

    /**
     * 查询维护作业完成情况详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevWorkCompletionDetails devWorkCompletionDetails)
    {
        startPage();
        List<DevWorkCompletionDetails> list = devWorkCompletionDetailsService.selectDevWorkCompletionDetailsList(devWorkCompletionDetails);
        return getDataTable(list);
    }

    /**
     * 导出维护作业完成情况详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:details:export')")
    @Log(title = "维护作业完成情况详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DevWorkCompletionDetails devWorkCompletionDetails)
    {
        List<DevWorkCompletionDetails> list = devWorkCompletionDetailsService.selectDevWorkCompletionDetailsList(devWorkCompletionDetails);
        ExcelUtil<DevWorkCompletionDetails> util = new ExcelUtil<DevWorkCompletionDetails>(DevWorkCompletionDetails.class);
        util.exportExcel(response, list, "维护作业完成情况详情数据");
    }

    /**
     * 获取维护作业完成情况详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:details:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devWorkCompletionDetailsService.selectDevWorkCompletionDetailsById(id));
    }

    /**
     * 新增维护作业完成情况详情
     */
    @PreAuthorize("@ss.hasPermi('system:details:add')")
    @Log(title = "维护作业完成情况详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevWorkCompletionDetails devWorkCompletionDetails)
    {
        return toAjax(devWorkCompletionDetailsService.insertDevWorkCompletionDetails(devWorkCompletionDetails));
    }

    /**
     * 修改维护作业完成情况详情
     */
    @PreAuthorize("@ss.hasPermi('system:details:edit')")
    @Log(title = "维护作业完成情况详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevWorkCompletionDetails devWorkCompletionDetails)
    {
        return toAjax(devWorkCompletionDetailsService.updateDevWorkCompletionDetails(devWorkCompletionDetails));
    }

    /**
     * 删除维护作业完成情况详情
     */
    @PreAuthorize("@ss.hasPermi('system:details:remove')")
    @Log(title = "维护作业完成情况详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devWorkCompletionDetailsService.deleteDevWorkCompletionDetailsByIds(ids));
    }
}
