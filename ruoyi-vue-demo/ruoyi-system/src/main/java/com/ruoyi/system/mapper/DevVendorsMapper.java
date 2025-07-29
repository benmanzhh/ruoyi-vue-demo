package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DevVendors;

/**
 * 供应商信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
public interface DevVendorsMapper 
{
    /**
     * 查询供应商信息
     * 
     * @param id 供应商信息主键
     * @return 供应商信息
     */
    public DevVendors selectDevVendorsById(Long id);

    /**
     * 查询供应商信息列表
     * 
     * @param devVendors 供应商信息
     * @return 供应商信息集合
     */
    public List<DevVendors> selectDevVendorsList(DevVendors devVendors);

    /**
     * 新增供应商信息
     * 
     * @param devVendors 供应商信息
     * @return 结果
     */
    public int insertDevVendors(DevVendors devVendors);

    /**
     * 修改供应商信息
     * 
     * @param devVendors 供应商信息
     * @return 结果
     */
    public int updateDevVendors(DevVendors devVendors);

    /**
     * 删除供应商信息
     * 
     * @param id 供应商信息主键
     * @return 结果
     */
    public int deleteDevVendorsById(Long id);

    /**
     * 批量删除供应商信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevVendorsByIds(Long[] ids);
}
