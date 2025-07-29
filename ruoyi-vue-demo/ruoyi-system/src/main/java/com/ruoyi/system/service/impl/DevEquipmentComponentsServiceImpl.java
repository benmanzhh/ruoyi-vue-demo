package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.DevPersonnel;
import com.ruoyi.system.mapper.DevEquipmentComponentsMapper;
import com.ruoyi.system.domain.DevEquipmentComponents;
import com.ruoyi.system.service.IDevEquipmentComponentsService;

/**
 * 设备部件Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@Service
public class DevEquipmentComponentsServiceImpl implements IDevEquipmentComponentsService 
{
    @Autowired
    private DevEquipmentComponentsMapper devEquipmentComponentsMapper;

    /**
     * 查询设备部件
     * 
     * @param id 设备部件主键
     * @return 设备部件
     */
    @Override
    public DevEquipmentComponents selectDevEquipmentComponentsById(Long id)
    {
        return devEquipmentComponentsMapper.selectDevEquipmentComponentsById(id);
    }

    /**
     * 查询设备部件列表
     * 
     * @param devEquipmentComponents 设备部件
     * @return 设备部件
     */
    @Override
    public List<DevEquipmentComponents> selectDevEquipmentComponentsList(DevEquipmentComponents devEquipmentComponents)
    {
        return devEquipmentComponentsMapper.selectDevEquipmentComponentsList(devEquipmentComponents);
    }

    /**
     * 新增设备部件
     * 
     * @param devEquipmentComponents 设备部件
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDevEquipmentComponents(DevEquipmentComponents devEquipmentComponents)
    {
        devEquipmentComponents.setCreateTime(DateUtils.getNowDate());
        int rows = devEquipmentComponentsMapper.insertDevEquipmentComponents(devEquipmentComponents);
        insertDevPersonnel(devEquipmentComponents);
        return rows;
    }

    /**
     * 修改设备部件
     * 
     * @param devEquipmentComponents 设备部件
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDevEquipmentComponents(DevEquipmentComponents devEquipmentComponents)
    {
        devEquipmentComponents.setUpdateTime(DateUtils.getNowDate());
        devEquipmentComponentsMapper.deleteDevPersonnelByPersonnelId(devEquipmentComponents.getId());
        insertDevPersonnel(devEquipmentComponents);
        return devEquipmentComponentsMapper.updateDevEquipmentComponents(devEquipmentComponents);
    }

    /**
     * 批量删除设备部件
     * 
     * @param ids 需要删除的设备部件主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDevEquipmentComponentsByIds(Long[] ids)
    {
        devEquipmentComponentsMapper.deleteDevPersonnelByPersonnelIds(ids);
        return devEquipmentComponentsMapper.deleteDevEquipmentComponentsByIds(ids);
    }

    /**
     * 删除设备部件信息
     * 
     * @param id 设备部件主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDevEquipmentComponentsById(Long id)
    {
        devEquipmentComponentsMapper.deleteDevPersonnelByPersonnelId(id);
        return devEquipmentComponentsMapper.deleteDevEquipmentComponentsById(id);
    }

    /**
     * 新增维护人员信息信息
     * 
     * @param devEquipmentComponents 设备部件对象
     */
    public void insertDevPersonnel(DevEquipmentComponents devEquipmentComponents)
    {
        List<DevPersonnel> devPersonnelList = devEquipmentComponents.getDevPersonnelList();
        Long id = devEquipmentComponents.getId();
        if (StringUtils.isNotNull(devPersonnelList))
        {
            List<DevPersonnel> list = new ArrayList<DevPersonnel>();
            for (DevPersonnel devPersonnel : devPersonnelList)
            {
                devPersonnel.setPersonnelId(String.valueOf(id));
                list.add(devPersonnel);
            }
            if (list.size() > 0)
            {
                devEquipmentComponentsMapper.batchDevPersonnel(list);
            }
        }
    }
}
