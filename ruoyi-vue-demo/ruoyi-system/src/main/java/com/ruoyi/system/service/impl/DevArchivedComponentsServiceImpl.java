package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DevArchivedComponentsMapper;
import com.ruoyi.system.domain.DevArchivedComponents;
import com.ruoyi.system.service.IDevArchivedComponentsService;

/**
 * 设备归档历史Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
@Service
public class DevArchivedComponentsServiceImpl implements IDevArchivedComponentsService 
{
    @Autowired
    private DevArchivedComponentsMapper devArchivedComponentsMapper;

    /**
     * 查询设备归档历史
     * 
     * @param id 设备归档历史主键
     * @return 设备归档历史
     */
    @Override
    public DevArchivedComponents selectDevArchivedComponentsById(Long id)
    {
        return devArchivedComponentsMapper.selectDevArchivedComponentsById(id);
    }

    /**
     * 查询设备归档历史列表
     * 
     * @param devArchivedComponents 设备归档历史
     * @return 设备归档历史
     */
    @Override
    public List<DevArchivedComponents> selectDevArchivedComponentsList(DevArchivedComponents devArchivedComponents)
    {
        return devArchivedComponentsMapper.selectDevArchivedComponentsList(devArchivedComponents);
    }

    /**
     * 新增设备归档历史
     * 
     * @param devArchivedComponents 设备归档历史
     * @return 结果
     */
    @Override
    public int insertDevArchivedComponents(DevArchivedComponents devArchivedComponents)
    {
        devArchivedComponents.setCreateTime(DateUtils.getNowDate());
        return devArchivedComponentsMapper.insertDevArchivedComponents(devArchivedComponents);
    }

    /**
     * 修改设备归档历史
     * 
     * @param devArchivedComponents 设备归档历史
     * @return 结果
     */
    @Override
    public int updateDevArchivedComponents(DevArchivedComponents devArchivedComponents)
    {
        return devArchivedComponentsMapper.updateDevArchivedComponents(devArchivedComponents);
    }

    /**
     * 批量删除设备归档历史
     * 
     * @param ids 需要删除的设备归档历史主键
     * @return 结果
     */
    @Override
    public int deleteDevArchivedComponentsByIds(Long[] ids)
    {
        return devArchivedComponentsMapper.deleteDevArchivedComponentsByIds(ids);
    }

    /**
     * 删除设备归档历史信息
     * 
     * @param id 设备归档历史主键
     * @return 结果
     */
    @Override
    public int deleteDevArchivedComponentsById(Long id)
    {
        return devArchivedComponentsMapper.deleteDevArchivedComponentsById(id);
    }
}
