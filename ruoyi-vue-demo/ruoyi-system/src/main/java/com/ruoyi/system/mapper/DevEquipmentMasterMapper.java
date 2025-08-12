package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DevEquipmentMaster;
import com.ruoyi.system.domain.DevEquipmentComponents;
import org.springframework.data.repository.query.Param;

/**
 * 设备主档Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public interface DevEquipmentMasterMapper 
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
     * 删除设备主档
     * 
     * @param id 设备主档主键
     * @return 结果
     */
    public int deleteDevEquipmentMasterById(Long id);

    /**
     * 批量删除设备主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevEquipmentMasterByIds(Long[] ids);

    /**
     * 批量删除设备部件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevEquipmentComponentsByEquipmentIds(Long[] ids);
    
    /**
     * 批量新增设备部件
     * 
     * @param devEquipmentComponentsList 设备部件列表
     * @return 结果
     */
    public int batchDevEquipmentComponents(List<DevEquipmentComponents> devEquipmentComponentsList);
    

    /**
     * 通过设备主档主键删除设备部件信息
     * 
     * @param id 设备主档ID
     * @return 结果
     */
    public int deleteDevEquipmentComponentsByEquipmentId(Long id);

    /**
     * 查询即将到达最大使用年限的设备主档列表
     *
     * @param days 提前提醒的天数
     * @return 设备主档列表
     */
    public List<DevEquipmentMaster> selectMasterLifecycleAlerts(@Param("days") int days);

    /**
     * 查询即将到达质保时间的设备主档列表
     *
     * @param days 提前提醒的天数
     * @return 设备主档列表
     */
    public List<DevEquipmentMaster> selectMasterWarrantyAlerts(@Param("days") int days);

}
