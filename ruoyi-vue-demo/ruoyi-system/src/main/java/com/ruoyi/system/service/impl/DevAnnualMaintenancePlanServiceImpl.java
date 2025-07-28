package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.DevWorkCompletionDetails;
import com.ruoyi.system.domain.DevArchivedComponents;
import com.ruoyi.system.mapper.DevAnnualMaintenancePlanMapper;
import com.ruoyi.system.domain.DevAnnualMaintenancePlan;
import com.ruoyi.system.service.IDevAnnualMaintenancePlanService;

/**
 * 年度作业计划Service业务层处理
 * * @author ruoyi
 * @date 2025-07-25
 */
@Service
public class DevAnnualMaintenancePlanServiceImpl implements IDevAnnualMaintenancePlanService
{
    @Autowired
    private DevAnnualMaintenancePlanMapper devAnnualMaintenancePlanMapper;

    @Override
    public DevAnnualMaintenancePlan selectDevAnnualMaintenancePlanByPlanId(Long planId)
    {
        return devAnnualMaintenancePlanMapper.selectDevAnnualMaintenancePlanByPlanId(planId);
    }

    @Override
    public List<DevAnnualMaintenancePlan> selectDevAnnualMaintenancePlanList(DevAnnualMaintenancePlan devAnnualMaintenancePlan)
    {
        return devAnnualMaintenancePlanMapper.selectDevAnnualMaintenancePlanList(devAnnualMaintenancePlan);
    }

    @Transactional
    @Override
    public int insertDevAnnualMaintenancePlan(DevAnnualMaintenancePlan devAnnualMaintenancePlan)
    {
        devAnnualMaintenancePlan.setCreateTime(DateUtils.getNowDate());
        int rows = devAnnualMaintenancePlanMapper.insertDevAnnualMaintenancePlan(devAnnualMaintenancePlan);
        insertDevWorkCompletionDetails(devAnnualMaintenancePlan);
        insertDevArchivedComponents(devAnnualMaintenancePlan);
        return rows;
    }

    @Transactional
    @Override
    public int updateDevAnnualMaintenancePlan(DevAnnualMaintenancePlan devAnnualMaintenancePlan)
    {
        devAnnualMaintenancePlan.setUpdateTime(DateUtils.getNowDate());
        devAnnualMaintenancePlanMapper.deleteDevWorkCompletionDetailsByPlanId(devAnnualMaintenancePlan.getPlanId());
        devAnnualMaintenancePlanMapper.deleteDevArchivedComponentsByPlanId(devAnnualMaintenancePlan.getPlanId());
        insertDevWorkCompletionDetails(devAnnualMaintenancePlan);
        insertDevArchivedComponents(devAnnualMaintenancePlan);
        return devAnnualMaintenancePlanMapper.updateDevAnnualMaintenancePlan(devAnnualMaintenancePlan);
    }

    @Transactional
    @Override
    public int deleteDevAnnualMaintenancePlanByPlanIds(Long[] planIds)
    {
        devAnnualMaintenancePlanMapper.deleteDevWorkCompletionDetailsByPlanIds(planIds);
        devAnnualMaintenancePlanMapper.deleteDevArchivedComponentsByPlanIds(planIds);
        return devAnnualMaintenancePlanMapper.deleteDevAnnualMaintenancePlanByPlanIds(planIds);
    }

    @Transactional
    @Override
    public int deleteDevAnnualMaintenancePlanByPlanId(Long planId)
    {
        devAnnualMaintenancePlanMapper.deleteDevWorkCompletionDetailsByPlanId(planId);
        devAnnualMaintenancePlanMapper.deleteDevArchivedComponentsByPlanId(planId);
        return devAnnualMaintenancePlanMapper.deleteDevAnnualMaintenancePlanByPlanId(planId);
    }

    /**
     * 新增维护作业完成情况详情信息
     */
    public void insertDevWorkCompletionDetails(DevAnnualMaintenancePlan devAnnualMaintenancePlan)
    {
        List<DevWorkCompletionDetails> devWorkCompletionDetailsList = devAnnualMaintenancePlan.getDevWorkCompletionDetailsList();
        Long planId = devAnnualMaintenancePlan.getPlanId();
        if (StringUtils.isNotNull(devWorkCompletionDetailsList))
        {
            List<DevWorkCompletionDetails> list = new ArrayList<DevWorkCompletionDetails>();
            for (DevWorkCompletionDetails devWorkCompletionDetails : devWorkCompletionDetailsList)
            {
                devWorkCompletionDetails.setPlanId(planId);
                list.add(devWorkCompletionDetails);
            }
            if (list.size() > 0)
            {
                devAnnualMaintenancePlanMapper.batchDevWorkCompletionDetails(list);
            }
        }
    }

    /**
     * 新增设备归档历史信息
     */
    public void insertDevArchivedComponents(DevAnnualMaintenancePlan devAnnualMaintenancePlan)
    {
        List<DevArchivedComponents> devArchivedComponentsList = devAnnualMaintenancePlan.getDevArchivedComponentsList();
        Long planId = devAnnualMaintenancePlan.getPlanId();
        if (StringUtils.isNotNull(devArchivedComponentsList))
        {
            List<DevArchivedComponents> list = new ArrayList<DevArchivedComponents>();
            for (DevArchivedComponents devArchivedComponents : devArchivedComponentsList)
            {
                devArchivedComponents.setPlanId(planId);
                list.add(devArchivedComponents);
            }
            if (list.size() > 0)
            {
                devAnnualMaintenancePlanMapper.batchDevArchivedComponents(list);
            }
        }
    }
}
