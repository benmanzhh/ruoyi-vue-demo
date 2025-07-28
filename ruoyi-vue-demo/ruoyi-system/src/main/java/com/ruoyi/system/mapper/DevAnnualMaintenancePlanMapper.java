package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DevAnnualMaintenancePlan;
import com.ruoyi.system.domain.DevWorkCompletionDetails;
import com.ruoyi.system.domain.DevArchivedComponents;

/**
 * 年度作业计划Mapper接口
 * * @author ruoyi
 * @date 2025-07-25
 */
public interface DevAnnualMaintenancePlanMapper
{
    /**
     * 查询年度作业计划
     * @param planId 年度作业计划主键
     * @return 年度作业计划
     */
    public DevAnnualMaintenancePlan selectDevAnnualMaintenancePlanByPlanId(Long planId);

    /**
     * 查询年度作业计划列表
     * @param devAnnualMaintenancePlan 年度作业计划
     * @return 年度作业计划集合
     */
    public List<DevAnnualMaintenancePlan> selectDevAnnualMaintenancePlanList(DevAnnualMaintenancePlan devAnnualMaintenancePlan);

    /**
     * 新增年度作业计划
     * @param devAnnualMaintenancePlan 年度作业计划
     * @return 结果
     */
    public int insertDevAnnualMaintenancePlan(DevAnnualMaintenancePlan devAnnualMaintenancePlan);

    /**
     * 修改年度作业计划
     * @param devAnnualMaintenancePlan 年度作业计划
     * @return 结果
     */
    public int updateDevAnnualMaintenancePlan(DevAnnualMaintenancePlan devAnnualMaintenancePlan);

    /**
     * 删除年度作业计划
     * @param planId 年度作业计划主键
     * @return 结果
     */
    public int deleteDevAnnualMaintenancePlanByPlanId(Long planId);

    /**
     * 批量删除年度作业计划
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevAnnualMaintenancePlanByPlanIds(Long[] planIds);

    /**
     * 批量新增维护作业完成情况详情
     * @param devWorkCompletionDetailsList 维护作业完成情况详情列表
     * @return 结果
     */
    public int batchDevWorkCompletionDetails(List<DevWorkCompletionDetails> devWorkCompletionDetailsList);

    /**
     * 批量新增设备归档历史
     * @param devArchivedComponentsList 设备归档历史列表
     * @return 结果
     */
    public int batchDevArchivedComponents(List<DevArchivedComponents> devArchivedComponentsList);


    /**
     * 通过年度作业计划主键删除维护作业完成情况详情信息
     * @param planId 年度作业计划ID
     * @return 结果
     */
    public int deleteDevWorkCompletionDetailsByPlanId(Long planId);

    /**
     * 通过年度作业计划主键批量删除维护作业完成情况详情信息
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevWorkCompletionDetailsByPlanIds(Long[] planIds);

    /**
     * 通过年度作业计划主键删除设备归档历史信息
     * @param planId 年度作业计划ID
     * @return 结果
     */
    public int deleteDevArchivedComponentsByPlanId(Long planId);

    /**
     * 通过年度作业计划主键批量删除设备归档历史信息
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevArchivedComponentsByPlanIds(Long[] planIds);
}