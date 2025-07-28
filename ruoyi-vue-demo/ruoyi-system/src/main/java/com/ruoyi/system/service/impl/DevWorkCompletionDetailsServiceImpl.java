package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DevWorkCompletionDetailsMapper;
import com.ruoyi.system.domain.DevWorkCompletionDetails;
import com.ruoyi.system.service.IDevWorkCompletionDetailsService;

/**
 * 维护作业完成情况详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
@Service
public class DevWorkCompletionDetailsServiceImpl implements IDevWorkCompletionDetailsService 
{
    @Autowired
    private DevWorkCompletionDetailsMapper devWorkCompletionDetailsMapper;

    /**
     * 查询维护作业完成情况详情
     * 
     * @param id 维护作业完成情况详情主键
     * @return 维护作业完成情况详情
     */
    @Override
    public DevWorkCompletionDetails selectDevWorkCompletionDetailsById(Long id)
    {
        return devWorkCompletionDetailsMapper.selectDevWorkCompletionDetailsById(id);
    }

    /**
     * 查询维护作业完成情况详情列表
     * 
     * @param devWorkCompletionDetails 维护作业完成情况详情
     * @return 维护作业完成情况详情
     */
    @Override
    public List<DevWorkCompletionDetails> selectDevWorkCompletionDetailsList(DevWorkCompletionDetails devWorkCompletionDetails)
    {
        return devWorkCompletionDetailsMapper.selectDevWorkCompletionDetailsList(devWorkCompletionDetails);
    }

    /**
     * 新增维护作业完成情况详情
     * 
     * @param devWorkCompletionDetails 维护作业完成情况详情
     * @return 结果
     */
    @Override
    public int insertDevWorkCompletionDetails(DevWorkCompletionDetails devWorkCompletionDetails)
    {
        devWorkCompletionDetails.setCreateTime(DateUtils.getNowDate());
        return devWorkCompletionDetailsMapper.insertDevWorkCompletionDetails(devWorkCompletionDetails);
    }

    /**
     * 修改维护作业完成情况详情
     * 
     * @param devWorkCompletionDetails 维护作业完成情况详情
     * @return 结果
     */
    @Override
    public int updateDevWorkCompletionDetails(DevWorkCompletionDetails devWorkCompletionDetails)
    {
        devWorkCompletionDetails.setUpdateTime(DateUtils.getNowDate());
        return devWorkCompletionDetailsMapper.updateDevWorkCompletionDetails(devWorkCompletionDetails);
    }

    /**
     * 批量删除维护作业完成情况详情
     * 
     * @param ids 需要删除的维护作业完成情况详情主键
     * @return 结果
     */
    @Override
    public int deleteDevWorkCompletionDetailsByIds(Long[] ids)
    {
        return devWorkCompletionDetailsMapper.deleteDevWorkCompletionDetailsByIds(ids);
    }

    /**
     * 删除维护作业完成情况详情信息
     * 
     * @param id 维护作业完成情况详情主键
     * @return 结果
     */
    @Override
    public int deleteDevWorkCompletionDetailsById(Long id)
    {
        return devWorkCompletionDetailsMapper.deleteDevWorkCompletionDetailsById(id);
    }
}
