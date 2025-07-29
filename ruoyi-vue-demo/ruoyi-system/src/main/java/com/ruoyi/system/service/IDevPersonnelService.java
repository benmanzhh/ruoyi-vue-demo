package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DevPersonnel;

/**
 * 维护人员信息Service接口
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
public interface IDevPersonnelService 
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
     * 批量删除维护人员信息
     * 
     * @param ids 需要删除的维护人员信息主键集合
     * @return 结果
     */
    public int deleteDevPersonnelByIds(Long[] ids);

    /**
     * 删除维护人员信息信息
     * 
     * @param id 维护人员信息主键
     * @return 结果
     */
    public int deleteDevPersonnelById(Long id);
}
