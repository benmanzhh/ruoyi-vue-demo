package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DevMaintenanceBlacklistMapper;
import com.ruoyi.system.domain.DevMaintenanceBlacklist;
import com.ruoyi.system.service.IDevMaintenanceBlacklistService;

/**
 * 维护作业黑名单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@Service
public class DevMaintenanceBlacklistServiceImpl implements IDevMaintenanceBlacklistService 
{
    @Autowired
    private DevMaintenanceBlacklistMapper devMaintenanceBlacklistMapper;

    /**
     * 查询维护作业黑名单
     * 
     * @param id 维护作业黑名单主键
     * @return 维护作业黑名单
     */
    @Override
    public DevMaintenanceBlacklist selectDevMaintenanceBlacklistById(Long id)
    {
        return devMaintenanceBlacklistMapper.selectDevMaintenanceBlacklistById(id);
    }

    /**
     * 查询维护作业黑名单列表
     * 
     * @param devMaintenanceBlacklist 维护作业黑名单
     * @return 维护作业黑名单
     */
    @Override
    public List<DevMaintenanceBlacklist> selectDevMaintenanceBlacklistList(DevMaintenanceBlacklist devMaintenanceBlacklist)
    {
        return devMaintenanceBlacklistMapper.selectDevMaintenanceBlacklistList(devMaintenanceBlacklist);
    }

    /**
     * 新增维护作业黑名单
     * 
     * @param devMaintenanceBlacklist 维护作业黑名单
     * @return 结果
     */
    @Override
    public int insertDevMaintenanceBlacklist(DevMaintenanceBlacklist devMaintenanceBlacklist)
    {
        devMaintenanceBlacklist.setCreateTime(DateUtils.getNowDate());
        return devMaintenanceBlacklistMapper.insertDevMaintenanceBlacklist(devMaintenanceBlacklist);
    }

    /**
     * 修改维护作业黑名单
     * 
     * @param devMaintenanceBlacklist 维护作业黑名单
     * @return 结果
     */
    @Override
    public int updateDevMaintenanceBlacklist(DevMaintenanceBlacklist devMaintenanceBlacklist)
    {
        devMaintenanceBlacklist.setUpdateTime(DateUtils.getNowDate());
        return devMaintenanceBlacklistMapper.updateDevMaintenanceBlacklist(devMaintenanceBlacklist);
    }

    /**
     * 批量删除维护作业黑名单
     * 
     * @param ids 需要删除的维护作业黑名单主键
     * @return 结果
     */
    @Override
    public int deleteDevMaintenanceBlacklistByIds(Long[] ids)
    {
        return devMaintenanceBlacklistMapper.deleteDevMaintenanceBlacklistByIds(ids);
    }

    /**
     * 删除维护作业黑名单信息
     * 
     * @param id 维护作业黑名单主键
     * @return 结果
     */
    @Override
    public int deleteDevMaintenanceBlacklistById(Long id)
    {
        return devMaintenanceBlacklistMapper.deleteDevMaintenanceBlacklistById(id);
    }
}
