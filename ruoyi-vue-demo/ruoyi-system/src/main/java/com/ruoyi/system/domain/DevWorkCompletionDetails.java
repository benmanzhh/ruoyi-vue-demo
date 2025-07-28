package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维护作业完成情况详情对象 dev_work_completion_details
 * 
 * @author ruoyi
 * @date 2025-07-25
 */
public class DevWorkCompletionDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 完成情况ID */
    private Long id;

    /** 关联的计划ID */
    @Excel(name = "关联的计划ID")
    private Long planId;

    /** 完成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionDate;

    /** 故障现象描述 */
    @Excel(name = "故障现象描述")
    private String faultDescription;

    /** 采取的维保措施 */
    @Excel(name = "采取的维保措施")
    private String actionsTaken;

    /** 更换备件信息 */
    @Excel(name = "更换备件信息")
    private String partsReplaced;

    /** 维保报告链接 */
    @Excel(name = "维保报告链接")
    private String reportUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setCompletionDate(Date completionDate) 
    {
        this.completionDate = completionDate;
    }

    public Date getCompletionDate() 
    {
        return completionDate;
    }
    public void setFaultDescription(String faultDescription) 
    {
        this.faultDescription = faultDescription;
    }

    public String getFaultDescription() 
    {
        return faultDescription;
    }
    public void setActionsTaken(String actionsTaken) 
    {
        this.actionsTaken = actionsTaken;
    }

    public String getActionsTaken() 
    {
        return actionsTaken;
    }
    public void setPartsReplaced(String partsReplaced) 
    {
        this.partsReplaced = partsReplaced;
    }

    public String getPartsReplaced() 
    {
        return partsReplaced;
    }
    public void setReportUrl(String reportUrl) 
    {
        this.reportUrl = reportUrl;
    }

    public String getReportUrl() 
    {
        return reportUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("completionDate", getCompletionDate())
            .append("faultDescription", getFaultDescription())
            .append("actionsTaken", getActionsTaken())
            .append("partsReplaced", getPartsReplaced())
            .append("reportUrl", getReportUrl())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
