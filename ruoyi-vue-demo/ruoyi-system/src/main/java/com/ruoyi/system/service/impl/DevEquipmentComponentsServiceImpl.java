package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.UUID; // 引入UUID类
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.DevAnnualMaintenancePlan;
import com.ruoyi.system.domain.DevPersonnel;
import com.ruoyi.system.mapper.DevEquipmentComponentsMapper;
import com.ruoyi.system.domain.DevEquipmentComponents;
import com.ruoyi.system.service.IDevEquipmentComponentsService;

/**
 * 设备部件Service业务层处理
 * * @author ruoyi
 * @date 2025-07-29
 */
@Service
public class DevEquipmentComponentsServiceImpl implements IDevEquipmentComponentsService
{
    @Autowired
    private DevEquipmentComponentsMapper devEquipmentComponentsMapper;

    @Override
    public DevEquipmentComponents selectDevEquipmentComponentsById(Long id)
    {
        return devEquipmentComponentsMapper.selectDevEquipmentComponentsById(id);
    }

    @Override
    public List<DevEquipmentComponents> selectDevEquipmentComponentsList(DevEquipmentComponents devEquipmentComponents)
    {
        return devEquipmentComponentsMapper.selectDevEquipmentComponentsList(devEquipmentComponents);
    }

    @Transactional
    @Override
    public int insertDevEquipmentComponents(DevEquipmentComponents devEquipmentComponents)
    {
        devEquipmentComponents.setCreateTime(DateUtils.getNowDate());
        int rows = devEquipmentComponentsMapper.insertDevEquipmentComponents(devEquipmentComponents);
        insertDevPersonnel(devEquipmentComponents);
        insertDevAnnualMaintenancePlan(devEquipmentComponents);
        return rows;
    }

    @Transactional
    @Override
    public int updateDevEquipmentComponents(DevEquipmentComponents devEquipmentComponents)
    {
        devEquipmentComponents.setUpdateTime(DateUtils.getNowDate());

        devEquipmentComponentsMapper.deleteDevPersonnelByPersonnelId(devEquipmentComponents.getPersonnelId());
        devEquipmentComponentsMapper.deleteDevAnnualMaintenancePlanByComponentId(devEquipmentComponents.getId());

        insertDevPersonnel(devEquipmentComponents);
        insertDevAnnualMaintenancePlan(devEquipmentComponents);

        return devEquipmentComponentsMapper.updateDevEquipmentComponents(devEquipmentComponents);
    }

    @Transactional
    @Override
    public int deleteDevEquipmentComponentsByIds(Long[] ids)
    {
        devEquipmentComponentsMapper.deleteDevPersonnelByPersonnelIds(ids);
        devEquipmentComponentsMapper.deleteDevAnnualMaintenancePlanByComponentIds(ids);
        return devEquipmentComponentsMapper.deleteDevEquipmentComponentsByIds(ids);
    }

    @Transactional
    @Override
    public int deleteDevEquipmentComponentsById(Long id)
    {
        devEquipmentComponentsMapper.deleteDevPersonnelByPersonnelId(id);
        devEquipmentComponentsMapper.deleteDevAnnualMaintenancePlanByComponentId(id);
        return devEquipmentComponentsMapper.deleteDevEquipmentComponentsById(id);
    }

    /**
     * 新增维护人员信息信息
     */
    public void insertDevPersonnel(DevEquipmentComponents devEquipmentComponents)
    {
        List<DevPersonnel> devPersonnelList = devEquipmentComponents.getDevPersonnelList();
        if (StringUtils.isNotNull(devPersonnelList))
        {
            List<DevPersonnel> list = new ArrayList<>();
            for (DevPersonnel devPersonnel : devPersonnelList)
            {
                // 核心修正: 检查personnelId是否为空，如果为空则生成一个UUID
                if (StringUtils.isEmpty(devPersonnel.getPersonnelId()))
                {
                    devPersonnel.setPersonnelId(UUID.randomUUID().toString());
                }
                list.add(devPersonnel);
            }
            if (list.size() > 0)
            {
                devEquipmentComponentsMapper.batchDevPersonnel(list);
            }
        }
    }

    /**
     * 新增年度作业计划信息
     */
    public void insertDevAnnualMaintenancePlan(DevEquipmentComponents devEquipmentComponents)
    {
        List<DevAnnualMaintenancePlan> devAnnualMaintenancePlanList = devEquipmentComponents.getDevAnnualMaintenancePlanList();
        Long componentId = devEquipmentComponents.getId();
        if (StringUtils.isNotNull(devAnnualMaintenancePlanList))
        {
            List<DevAnnualMaintenancePlan> list = new ArrayList<>();
            for (DevAnnualMaintenancePlan plan : devAnnualMaintenancePlanList)
            {
                plan.setComponentId(componentId);
                list.add(plan);
            }
            if (list.size() > 0)
            {
                devEquipmentComponentsMapper.batchDevAnnualMaintenancePlan(list);
            }
        }
    }
}