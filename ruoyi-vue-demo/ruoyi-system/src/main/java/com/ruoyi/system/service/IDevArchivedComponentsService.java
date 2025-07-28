package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DevArchivedComponents;

/**
 * 设备归档历史Service接口
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
public interface IDevArchivedComponentsService 
{
    /**
     * 查询设备归档历史
     * 
     * @param id 设备归档历史主键
     * @return 设备归档历史
     */
    public DevArchivedComponents selectDevArchivedComponentsById(Long id);

    /**
     * 查询设备归档历史列表
     * 
     * @param devArchivedComponents 设备归档历史
     * @return 设备归档历史集合
     */
    public List<DevArchivedComponents> selectDevArchivedComponentsList(DevArchivedComponents devArchivedComponents);

    /**
     * 新增设备归档历史
     * 
     * @param devArchivedComponents 设备归档历史
     * @return 结果
     */
    public int insertDevArchivedComponents(DevArchivedComponents devArchivedComponents);

    /**
     * 修改设备归档历史
     * 
     * @param devArchivedComponents 设备归档历史
     * @return 结果
     */
    public int updateDevArchivedComponents(DevArchivedComponents devArchivedComponents);

    /**
     * 批量删除设备归档历史
     * 
     * @param ids 需要删除的设备归档历史主键集合
     * @return 结果
     */
    public int deleteDevArchivedComponentsByIds(Long[] ids);

    /**
     * 删除设备归档历史信息
     * 
     * @param id 设备归档历史主键
     * @return 结果
     */
    public int deleteDevArchivedComponentsById(Long id);
}
