package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DevPersonnel;
import com.ruoyi.system.domain.DevVendors;

/**
 * 维护人员信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
public interface DevPersonnelMapper 
{
    /**
     * 查询维护人员信息
     * 
     * @param id 维护人员信息主键
     * @return 维护人员信息
     */
    public DevPersonnel selectDevPersonnelById(Long id);

    /**
     * 查询维护人员信息列表
     * 
     * @param devPersonnel 维护人员信息
     * @return 维护人员信息集合
     */
    public List<DevPersonnel> selectDevPersonnelList(DevPersonnel devPersonnel);

    /**
     * 新增维护人员信息
     * 
     * @param devPersonnel 维护人员信息
     * @return 结果
     */
    public int insertDevPersonnel(DevPersonnel devPersonnel);

    /**
     * 修改维护人员信息
     * 
     * @param devPersonnel 维护人员信息
     * @return 结果
     */
    public int updateDevPersonnel(DevPersonnel devPersonnel);

    /**
     * 删除维护人员信息
     * 
     * @param id 维护人员信息主键
     * @return 结果
     */
    public int deleteDevPersonnelById(Long id);

    /**
     * 批量删除维护人员信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevPersonnelByIds(Long[] ids);

    /**
     * 批量删除供应商信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevVendorsByVendorIds(Long[] ids);
    
    /**
     * 批量新增供应商信息
     * 
     * @param devVendorsList 供应商信息列表
     * @return 结果
     */
    public int batchDevVendors(List<DevVendors> devVendorsList);
    

    /**
     * 通过维护人员信息主键删除供应商信息信息
     * 
     * @param id 维护人员信息ID
     * @return 结果
     */
    public int deleteDevVendorsByVendorId(Long id);
}
