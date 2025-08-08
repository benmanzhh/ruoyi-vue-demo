package com.ruoyi.system.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        // 核心优化: 在插入数据库之前，调用日期计算方法
        calculateAndSetDerivedDates(devEquipmentComponents);

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

        // 核心优化: 在更新数据库之前，调用日期计算方法
        calculateAndSetDerivedDates(devEquipmentComponents);

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
        Long componentId = devEquipmentComponents.getId();
        // 核心修正: 获取主表中填写的 personnelId (类型为Long)
        Long personnelIdFromMaster = devEquipmentComponents.getPersonnelId();


        if (StringUtils.isNotNull(devPersonnelList))
        {
            List<DevPersonnel> list = new ArrayList<>();
            for (DevPersonnel devPersonnel : devPersonnelList)
            {
                // 核心修正: 将主表中填写的 personnelId (Long)
                // 直接设置到子表对象的 personnelId 字段中
                if (personnelIdFromMaster != null) {
                    devPersonnel.setPersonnelId(personnelIdFromMaster);
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

        String componentId = devEquipmentComponents.getComponentId();

        if (StringUtils.isNotNull(devAnnualMaintenancePlanList))
        {
            List<DevAnnualMaintenancePlan> list = new ArrayList<>();
            for (DevAnnualMaintenancePlan plan : devAnnualMaintenancePlanList)
            {
                plan.setComponentId(Long.valueOf(componentId));

                list.add(plan);
            }
            if (list.size() > 0)
            {
                devEquipmentComponentsMapper.batchDevAnnualMaintenancePlan(list);
            }
        }
    }

    /**
     * (新增的辅助方法)
     * 根据投入时间、使用年限和质保年限，计算并设置最大年限和质保时间。
     *
     * @param devEquipmentComponents 设备部件对象
     */
    private void calculateAndSetDerivedDates(DevEquipmentComponents devEquipmentComponents)
    {
        // 确保投入时间不为空，这是计算的基础
        if (devEquipmentComponents.getInstallDate() != null)
        {
            // 将java.util.Date转换为现代的java.time.LocalDate以便于计算
            LocalDate installDate = devEquipmentComponents.getInstallDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // 1. 计算最大使用年限
            // 检查使用年限字段是否存在且大于0
            if (devEquipmentComponents.getLifespanYears() != null && devEquipmentComponents.getLifespanYears() > 0)
            {
                LocalDate maxLifespanDate = installDate.plusYears(devEquipmentComponents.getLifespanYears());
                // 将计算结果转换回java.util.Date并设置回对象
                devEquipmentComponents.setMaxLifespanDate(Date.from(maxLifespanDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }

            // 2. 计算质保到期时间
            // 检查质保年限字段是否存在且大于0
            if (devEquipmentComponents.getWarrantyYears() != null && devEquipmentComponents.getWarrantyYears() > 0)
            {
                LocalDate warrantyEndDate = installDate.plusYears(devEquipmentComponents.getWarrantyYears());
                // 将计算结果转换回java.util.Date并设置回对象
                devEquipmentComponents.setWarrantyEndDate(Date.from(warrantyEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        }
    }
}