package com.ruoyi.system.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 年度作业计划对象 dev_annual_maintenance_plan
 * * @author ruoyi
 * @date 2025-07-25
 */
public class DevAnnualMaintenancePlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 计划主键ID */
    private Long planId;

    /** 维护目标类型 */
    @Excel(name = "维护目标类型")
    private String targetType;

    /** 部件ID */
    @Excel(name = "部件ID")
    private Long componentId;

    /** 计划所属年份 */
    @Excel(name = "计划所属年份")
    private Long year;

    /** 计划维护日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划维护日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scheduledDate;

    /** 计划状态 */
    @Excel(name = "计划状态")
    private String status;

    /** 维护人员ID */
    @Excel(name = "维护人员ID")
    private Long assignedPersonnelId;

    /** 维护作业完成情况详情信息 */
    private List<DevWorkCompletionDetails> devWorkCompletionDetailsList;

    /** 设备归档历史信息 */
    private List<DevArchivedComponents> devArchivedComponentsList;

    public void setPlanId(Long planId)
    {
        this.planId = planId;
    }

    public Long getPlanId()
    {
        return planId;
    }
    public void setTargetType(String targetType)
    {
        this.targetType = targetType;
    }

    public String getTargetType()
    {
        return targetType;
    }
    public void setComponentId(Long componentId)
    {
        this.componentId = componentId;
    }

    public Long getComponentId()
    {
        return componentId;
    }
    public void setYear(Long year)
    {
        this.year = year;
    }

    public Long getYear()
    {
        return year;
    }
    public void setScheduledDate(Date scheduledDate)
    {
        this.scheduledDate = scheduledDate;
    }

    public Date getScheduledDate()
    {
        return scheduledDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setAssignedPersonnelId(Long assignedPersonnelId)
    {
        this.assignedPersonnelId = assignedPersonnelId;
    }

    public Long getAssignedPersonnelId()
    {
        return assignedPersonnelId;
    }

    public List<DevWorkCompletionDetails> getDevWorkCompletionDetailsList()
    {
        return devWorkCompletionDetailsList;
    }

    public void setDevWorkCompletionDetailsList(List<DevWorkCompletionDetails> devWorkCompletionDetailsList)
    {
        this.devWorkCompletionDetailsList = devWorkCompletionDetailsList;
    }

    public List<DevArchivedComponents> getDevArchivedComponentsList()
    {
        return devArchivedComponentsList;
    }

    public void setDevArchivedComponentsList(List<DevArchivedComponents> devArchivedComponentsList)
    {
        this.devArchivedComponentsList = devArchivedComponentsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("planId", getPlanId())
                .append("targetType", getTargetType())
                .append("componentId", getComponentId())
                .append("year", getYear())
                .append("scheduledDate", getScheduledDate())
                .append("status", getStatus())
                .append("assignedPersonnelId", getAssignedPersonnelId())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("devWorkCompletionDetailsList", getDevWorkCompletionDetailsList())
                .append("devArchivedComponentsList", getDevArchivedComponentsList())
                .toString();
    }
}