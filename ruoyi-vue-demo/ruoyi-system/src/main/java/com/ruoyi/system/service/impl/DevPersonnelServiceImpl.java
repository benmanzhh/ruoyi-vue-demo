package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.DevVendors;
import com.ruoyi.system.mapper.DevPersonnelMapper;
import com.ruoyi.system.domain.DevPersonnel;
import com.ruoyi.system.service.IDevPersonnelService;

/**
 * 维护人员信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
@Service
public class DevPersonnelServiceImpl implements IDevPersonnelService 
{
    @Autowired
    private DevPersonnelMapper devPersonnelMapper;

    /**
     * 查询维护人员信息
     * 
     * @param id 维护人员信息主键
     * @return 维护人员信息
     */
    @Override
    public DevPersonnel selectDevPersonnelById(Long id)
    {
        return devPersonnelMapper.selectDevPersonnelById(id);
    }

    /**
     * 查询维护人员信息列表
     * 
     * @param devPersonnel 维护人员信息
     * @return 维护人员信息
     */
    @Override
    public List<DevPersonnel> selectDevPersonnelList(DevPersonnel devPersonnel)
    {
        return devPersonnelMapper.selectDevPersonnelList(devPersonnel);
    }

    /**
     * 新增维护人员信息
     * 
     * @param devPersonnel 维护人员信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDevPersonnel(DevPersonnel devPersonnel)
    {
        devPersonnel.setCreateTime(DateUtils.getNowDate());
        int rows = devPersonnelMapper.insertDevPersonnel(devPersonnel);
        insertDevVendors(devPersonnel);
        return rows;
    }

    /**
     * 修改维护人员信息
     * 
     * @param devPersonnel 维护人员信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDevPersonnel(DevPersonnel devPersonnel)
    {
        devPersonnel.setUpdateTime(DateUtils.getNowDate());
        devPersonnelMapper.deleteDevVendorsByVendorId(devPersonnel.getId());
        insertDevVendors(devPersonnel);
        return devPersonnelMapper.updateDevPersonnel(devPersonnel);
    }

    /**
     * 批量删除维护人员信息
     * 
     * @param ids 需要删除的维护人员信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDevPersonnelByIds(Long[] ids)
    {
        devPersonnelMapper.deleteDevVendorsByVendorIds(ids);
        return devPersonnelMapper.deleteDevPersonnelByIds(ids);
    }

    /**
     * 删除维护人员信息信息
     * 
     * @param id 维护人员信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDevPersonnelById(Long id)
    {
        devPersonnelMapper.deleteDevVendorsByVendorId(id);
        return devPersonnelMapper.deleteDevPersonnelById(id);
    }

    /**
     * 新增供应商信息信息
     * 
     * @param devPersonnel 维护人员信息对象
     */
    public void insertDevVendors(DevPersonnel devPersonnel)
    {
        List<DevVendors> devVendorsList = devPersonnel.getDevVendorsList();
        Long id = devPersonnel.getId();
        if (StringUtils.isNotNull(devVendorsList))
        {
            List<DevVendors> list = new ArrayList<DevVendors>();
            for (DevVendors devVendors : devVendorsList)
            {
                devVendors.setVendorId(id);
                list.add(devVendors);
            }
            if (list.size() > 0)
            {
                devPersonnelMapper.batchDevVendors(list);
            }
        }
    }
}
