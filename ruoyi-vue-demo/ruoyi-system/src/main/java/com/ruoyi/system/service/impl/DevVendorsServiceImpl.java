package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DevVendorsMapper;
import com.ruoyi.system.domain.DevVendors;
import com.ruoyi.system.service.IDevVendorsService;

/**
 * 供应商信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
@Service
public class DevVendorsServiceImpl implements IDevVendorsService 
{
    @Autowired
    private DevVendorsMapper devVendorsMapper;

    /**
     * 查询供应商信息
     * 
     * @param id 供应商信息主键
     * @return 供应商信息
     */
    @Override
    public DevVendors selectDevVendorsById(Long id)
    {
        return devVendorsMapper.selectDevVendorsById(id);
    }

    /**
     * 查询供应商信息列表
     * 
     * @param devVendors 供应商信息
     * @return 供应商信息
     */
    @Override
    public List<DevVendors> selectDevVendorsList(DevVendors devVendors)
    {
        return devVendorsMapper.selectDevVendorsList(devVendors);
    }

    /**
     * 新增供应商信息
     * 
     * @param devVendors 供应商信息
     * @return 结果
     */
    @Override
    public int insertDevVendors(DevVendors devVendors)
    {
        devVendors.setCreateTime(DateUtils.getNowDate());
        return devVendorsMapper.insertDevVendors(devVendors);
    }

    /**
     * 修改供应商信息
     * 
     * @param devVendors 供应商信息
     * @return 结果
     */
    @Override
    public int updateDevVendors(DevVendors devVendors)
    {
        devVendors.setUpdateTime(DateUtils.getNowDate());
        return devVendorsMapper.updateDevVendors(devVendors);
    }

    /**
     * 批量删除供应商信息
     * 
     * @param ids 需要删除的供应商信息主键
     * @return 结果
     */
    @Override
    public int deleteDevVendorsByIds(Long[] ids)
    {
        return devVendorsMapper.deleteDevVendorsByIds(ids);
    }

    /**
     * 删除供应商信息信息
     * 
     * @param id 供应商信息主键
     * @return 结果
     */
    @Override
    public int deleteDevVendorsById(Long id)
    {
        return devVendorsMapper.deleteDevVendorsById(id);
    }
}
