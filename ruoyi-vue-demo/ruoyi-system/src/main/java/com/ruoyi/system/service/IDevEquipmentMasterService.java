package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DevEquipmentMaster;

/**
 * 设备主档Service接口
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public interface IDevEquipmentMasterService 
{
    /**
     * 查询设备主档
     * 
     * @param id 设备主档主键
     * @return 设备主档
     */
    public DevEquipmentMaster selectDevEquipmentMasterById(Long id);

    /**
     * 查询设备主档列表
     * 
     * @param devEquipmentMaster 设备主档
     * @return 设备主档集合
     */
    public List<DevEquipmentMaster> selectDevEquipmentMasterList(DevEquipmentMaster devEquipmentMaster);

    /**
     * 新增设备主档
     * 
     * @param devEquipmentMaster 设备主档
     * @return 结果
     */
    public int insertDevEquipmentMaster(DevEquipmentMaster devEquipmentMaster);

    /**
     * 修改设备主档
     * 
     * @param devEquipmentMaster 设备主档
     * @return 结果
     */
    public int updateDevEquipmentMaster(DevEquipmentMaster devEquipmentMaster);

    /**
     * 批量删除设备主档
     * 
     * @param ids 需要删除的设备主档主键集合
     * @return 结果
     */
    public int deleteDevEquipmentMasterByIds(Long[] ids);

    /**
     * 删除设备主档信息
     * 
     * @param id 设备主档主键
     * @return 结果
     */
    public int deleteDevEquipmentMasterById(Long id);
}
