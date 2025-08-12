package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.DevAnnualMaintenancePlan;
import com.ruoyi.system.domain.DevEquipmentComponents;
import com.ruoyi.system.domain.DevPersonnel;
import org.springframework.data.repository.query.Param;

/**
 * 设备部件Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public interface DevEquipmentComponentsMapper 
{
    /**
     * 批量新增年度作业计划 (新增)
     */
    public int batchDevAnnualMaintenancePlan(List<DevAnnualMaintenancePlan> devAnnualMaintenancePlanList);

    /**
     * 通过设备部件主键删除年度作业计划信息 (新增)
     */
    public int deleteDevAnnualMaintenancePlanByComponentId(Long id);

    /*

    /**
     * 根据部件ID批量删除年度作业计划
     * * @param ids 需要删除的设备部件主键集合
     * @return 结果
     */
    public int deleteDevAnnualMaintenancePlanByComponentIds(Long[] ids);

    /**
     * 查询设备部件
     * 
     * @param id 设备部件主键
     * @return 设备部件
     */
    public DevEquipmentComponents selectDevEquipmentComponentsById(Long id);

    /**
     * 查询设备部件列表
     * 
     * @param devEquipmentComponents 设备部件
     * @return 设备部件集合
     */
    public List<DevEquipmentComponents> selectDevEquipmentComponentsList(DevEquipmentComponents devEquipmentComponents);

    /**
     * 新增设备部件
     * 
     * @param devEquipmentComponents 设备部件
     * @return 结果
     */
    public int insertDevEquipmentComponents(DevEquipmentComponents devEquipmentComponents);

    /**
     * 修改设备部件
     * 
     * @param devEquipmentComponents 设备部件
     * @return 结果
     */
    public int updateDevEquipmentComponents(DevEquipmentComponents devEquipmentComponents);

    /**
     * 删除设备部件
     * 
     * @param id 设备部件主键
     * @return 结果
     */
    public int deleteDevEquipmentComponentsById(Long id);

    /**
     * 批量删除设备部件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevEquipmentComponentsByIds(Long[] ids);

    /**
     * 批量删除维护人员信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevPersonnelByPersonnelIds(Long[] ids);
    
    /**
     * 批量新增维护人员信息
     * 
     * @param devPersonnelList 维护人员信息列表
     * @return 结果
     */
    public int batchDevPersonnel(List<DevPersonnel> devPersonnelList);
    

    /**
     * 通过设备部件主键删除维护人员信息信息
     * 
     * @param id 设备部件ID
     * @return 结果
     */
    public int deleteDevPersonnelByPersonnelId(Long id);


    /**
     * 查询即将到达最大使用年限的设备部件列表
     *
     * @param days 提前提醒的天数
     * @return 设备部件列表
     */
    public List<DevEquipmentComponents> selectComponentLifecycleAlerts(@Param("days") int days);
    /**
     * 查询即将到达质保时间的设备部件列表
     *
     * @param days 提前提醒的天数
     * @return 设备部件列表
     */
    public List<DevEquipmentComponents> selectComponentWarrantyAlerts(@Param("days") int days);

}
