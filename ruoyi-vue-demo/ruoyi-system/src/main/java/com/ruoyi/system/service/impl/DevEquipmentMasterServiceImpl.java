package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.DevEquipmentComponents;
import com.ruoyi.system.mapper.DevEquipmentMasterMapper;
import com.ruoyi.system.domain.DevEquipmentMaster;
import com.ruoyi.system.service.IDevEquipmentMasterService;

/**
 * 设备主档Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@Service
public class DevEquipmentMasterServiceImpl implements IDevEquipmentMasterService 
{
    @Autowired
    private DevEquipmentMasterMapper devEquipmentMasterMapper;

    /**
     * 查询设备主档
     * 
     * @param id 设备主档主键
     * @return 设备主档
     */
    @Override
    public DevEquipmentMaster selectDevEquipmentMasterById(Long id)
    {
        return devEquipmentMasterMapper.selectDevEquipmentMasterById(id);
    }

    /**
     * 查询设备主档列表
     * 
     * @param devEquipmentMaster 设备主档
     * @return 设备主档
     */
    @Override
    public List<DevEquipmentMaster> selectDevEquipmentMasterList(DevEquipmentMaster devEquipmentMaster)
    {
        return devEquipmentMasterMapper.selectDevEquipmentMasterList(devEquipmentMaster);
    }

    /**
     * 新增设备主档
     * 
     * @param devEquipmentMaster 设备主档
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDevEquipmentMaster(DevEquipmentMaster devEquipmentMaster)
    {
        devEquipmentMaster.setCreateTime(DateUtils.getNowDate());
        int rows = devEquipmentMasterMapper.insertDevEquipmentMaster(devEquipmentMaster);
        insertDevEquipmentComponents(devEquipmentMaster);
        return rows;
    }

    /**
     * 修改设备主档
     * 
     * @param devEquipmentMaster 设备主档
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDevEquipmentMaster(DevEquipmentMaster devEquipmentMaster)
    {
        devEquipmentMaster.setUpdateTime(DateUtils.getNowDate());
        devEquipmentMasterMapper.deleteDevEquipmentComponentsByEquipmentId(devEquipmentMaster.getId());
        insertDevEquipmentComponents(devEquipmentMaster);
        return devEquipmentMasterMapper.updateDevEquipmentMaster(devEquipmentMaster);
    }

    /**
     * 批量删除设备主档
     * 
     * @param ids 需要删除的设备主档主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDevEquipmentMasterByIds(Long[] ids)
    {
        devEquipmentMasterMapper.deleteDevEquipmentComponentsByEquipmentIds(ids);
        return devEquipmentMasterMapper.deleteDevEquipmentMasterByIds(ids);
    }

    /**
     * 删除设备主档信息
     * 
     * @param id 设备主档主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDevEquipmentMasterById(Long id)
    {
        devEquipmentMasterMapper.deleteDevEquipmentComponentsByEquipmentId(id);
        return devEquipmentMasterMapper.deleteDevEquipmentMasterById(id);
    }

    /**
     * 新增设备部件信息
     * 
     * @param devEquipmentMaster 设备主档对象
     */
    public void insertDevEquipmentComponents(DevEquipmentMaster devEquipmentMaster)
    {
        List<DevEquipmentComponents> devEquipmentComponentsList = devEquipmentMaster.getDevEquipmentComponentsList();
        Long id = devEquipmentMaster.getId();
        if (StringUtils.isNotNull(devEquipmentComponentsList))
        {
            List<DevEquipmentComponents> list = new ArrayList<DevEquipmentComponents>();
            for (DevEquipmentComponents devEquipmentComponents : devEquipmentComponentsList)
            {
                devEquipmentComponents.setEquipmentId(id);
                list.add(devEquipmentComponents);
            }
            if (list.size() > 0)
            {
                devEquipmentMasterMapper.batchDevEquipmentComponents(list);
            }
        }
    }
}
