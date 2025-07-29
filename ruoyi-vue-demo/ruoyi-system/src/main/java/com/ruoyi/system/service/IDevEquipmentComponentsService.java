package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DevEquipmentComponents;

/**
 * 设备部件Service接口
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public interface IDevEquipmentComponentsService 
{
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
     * 批量删除设备部件
     * 
     * @param ids 需要删除的设备部件主键集合
     * @return 结果
     */
    public int deleteDevEquipmentComponentsByIds(Long[] ids);

    /**
     * 删除设备部件信息
     * 
     * @param id 设备部件主键
     * @return 结果
     */
    public int deleteDevEquipmentComponentsById(Long id);
}
