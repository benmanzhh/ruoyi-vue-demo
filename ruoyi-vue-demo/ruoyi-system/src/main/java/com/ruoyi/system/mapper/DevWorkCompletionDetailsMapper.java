package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DevWorkCompletionDetails;

/**
 * 维护作业完成情况详情Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
public interface DevWorkCompletionDetailsMapper 
{
    /**
     * 查询维护作业完成情况详情
     * 
     * @param id 维护作业完成情况详情主键
     * @return 维护作业完成情况详情
     */
    public DevWorkCompletionDetails selectDevWorkCompletionDetailsById(Long id);

    /**
     * 查询维护作业完成情况详情列表
     * 
     * @param devWorkCompletionDetails 维护作业完成情况详情
     * @return 维护作业完成情况详情集合
     */
    public List<DevWorkCompletionDetails> selectDevWorkCompletionDetailsList(DevWorkCompletionDetails devWorkCompletionDetails);

    /**
     * 新增维护作业完成情况详情
     * 
     * @param devWorkCompletionDetails 维护作业完成情况详情
     * @return 结果
     */
    public int insertDevWorkCompletionDetails(DevWorkCompletionDetails devWorkCompletionDetails);

    /**
     * 修改维护作业完成情况详情
     * 
     * @param devWorkCompletionDetails 维护作业完成情况详情
     * @return 结果
     */
    public int updateDevWorkCompletionDetails(DevWorkCompletionDetails devWorkCompletionDetails);

    /**
     * 删除维护作业完成情况详情
     * 
     * @param id 维护作业完成情况详情主键
     * @return 结果
     */
    public int deleteDevWorkCompletionDetailsById(Long id);

    /**
     * 批量删除维护作业完成情况详情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevWorkCompletionDetailsByIds(Long[] ids);
}
