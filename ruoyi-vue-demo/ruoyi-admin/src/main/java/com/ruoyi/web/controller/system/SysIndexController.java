package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;

/**
 * 首页
 *
 * @author ruoyi
 */
@RestController
public class SysIndexController
{
    @Autowired
    private com.ruoyi.system.service.IDevEquipmentMasterService masterService;

    /** 系统基础配置 */
    @Autowired
    private RuoYiConfig ruoyiConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", ruoyiConfig.getName(), ruoyiConfig.getVersion());
    }

//    /**
//     * 获取生命周期提醒
//     */
//    @GetMapping("/lifecycle-alerts")
//    public com.ruoyi.common.core.domain.AjaxResult getLifecycleAlerts()
//    {
//        return com.ruoyi.common.core.domain.AjaxResult.success(masterService.getLifecycleAlerts());
//    }
//
//    /**
//     * 获取质保提醒
//     */
//    @GetMapping("/warranty-alerts")
//    public com.ruoyi.common.core.domain.AjaxResult getWarrantyAlerts()
//    {
//        return com.ruoyi.common.core.domain.AjaxResult.success(masterService.getWarrantyAlerts());
//    }

    /**
     * 获取统一的生命周期与质保提醒
     */
    @GetMapping("/combined-alerts")
    public com.ruoyi.common.core.domain.AjaxResult getCombinedAlerts()
    {
        return com.ruoyi.common.core.domain.AjaxResult.success(masterService.getCombinedAlerts());
    }
}
