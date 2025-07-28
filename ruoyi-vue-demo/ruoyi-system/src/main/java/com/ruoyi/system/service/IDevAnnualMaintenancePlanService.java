package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DevAnnualMaintenancePlan;

/**
 * 年度作业计划Service接口
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
public interface IDevAnnualMaintenancePlanService 
{
    /**
     * 查询年度作业计划
     * 
     * @param planId 年度作业计划主键
     * @return 年度作业计划
     */
    public DevAnnualMaintenancePlan selectDevAnnualMaintenancePlanByPlanId(Long planId);

    /**
     * 查询年度作业计划列表
     * 
     * @param devAnnualMaintenancePlan 年度作业计划
     * @return 年度作业计划集合
     */
    public List<DevAnnualMaintenancePlan> selectDevAnnualMaintenancePlanList(DevAnnualMaintenancePlan devAnnualMaintenancePlan);

    /**
     * 新增年度作业计划
     * 
     * @param devAnnualMaintenancePlan 年度作业计划
     * @return 结果
     */
    public int insertDevAnnualMaintenancePlan(DevAnnualMaintenancePlan devAnnualMaintenancePlan);

    /**
     * 修改年度作业计划
     * 
     * @param devAnnualMaintenancePlan 年度作业计划
     * @return 结果
     */
    public int updateDevAnnualMaintenancePlan(DevAnnualMaintenancePlan devAnnualMaintenancePlan);

    /**
     * 批量删除年度作业计划
     * 
     * @param planIds 需要删除的年度作业计划主键集合
     * @return 结果
     */
    public int deleteDevAnnualMaintenancePlanByPlanIds(Long[] planIds);

    /**
     * 删除年度作业计划信息
     * 
     * @param planId 年度作业计划主键
     * @return 结果
     */
    public int deleteDevAnnualMaintenancePlanByPlanId(Long planId);
}
