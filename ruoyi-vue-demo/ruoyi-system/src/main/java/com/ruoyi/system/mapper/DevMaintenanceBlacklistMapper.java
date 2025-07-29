package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DevMaintenanceBlacklist;

/**
 * 维护作业黑名单Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public interface DevMaintenanceBlacklistMapper 
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
     * 删除维护作业黑名单
     * 
     * @param id 维护作业黑名单主键
     * @return 结果
     */
    public int deleteDevMaintenanceBlacklistById(Long id);

    /**
     * 批量删除维护作业黑名单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevMaintenanceBlacklistByIds(Long[] ids);
}
