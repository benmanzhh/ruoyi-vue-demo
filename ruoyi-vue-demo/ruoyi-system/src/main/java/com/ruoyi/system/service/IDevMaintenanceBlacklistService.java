package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DevMaintenanceBlacklist;

/**
 * 维护作业黑名单Service接口
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public interface IDevMaintenanceBlacklistService 
{
    /**
     * 查询维护作业黑名单
     * 
     * @param id 维护作业黑名单主键
     * @return 维护作业黑名单
     */
    public DevMaintenanceBlacklist selectDevMaintenanceBlacklistById(Long id);

    /**
     * 查询维护作业黑名单列表
     * 
     * @param devMaintenanceBlacklist 维护作业黑名单
     * @return 维护作业黑名单集合
     */
    public List<DevMaintenanceBlacklist> selectDevMaintenanceBlacklistList(DevMaintenanceBlacklist devMaintenanceBlacklist);

    /**
     * 新增维护作业黑名单
     * 
     * @param devMaintenanceBlacklist 维护作业黑名单
     * @return 结果
     */
    public int insertDevMaintenanceBlacklist(DevMaintenanceBlacklist devMaintenanceBlacklist);

    /**
     * 修改维护作业黑名单
     * 
     * @param devMaintenanceBlacklist 维护作业黑名单
     * @return 结果
     */
    public int updateDevMaintenanceBlacklist(DevMaintenanceBlacklist devMaintenanceBlacklist);

    /**
     * 批量删除维护作业黑名单
     * 
     * @param ids 需要删除的维护作业黑名单主键集合
     * @return 结果
     */
    public int deleteDevMaintenanceBlacklistByIds(Long[] ids);

    /**
     * 删除维护作业黑名单信息
     * 
     * @param id 维护作业黑名单主键
     * @return 结果
     */
    public int deleteDevMaintenanceBlacklistById(Long id);
}
