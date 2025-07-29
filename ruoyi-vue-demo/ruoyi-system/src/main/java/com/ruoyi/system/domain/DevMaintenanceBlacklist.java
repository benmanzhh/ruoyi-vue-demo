package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维护作业黑名单对象 dev_maintenance_blacklist
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public class DevMaintenanceBlacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 黑名单主键ID */
    private Long id;

    /** 关联目标类型 */
    @Excel(name = "关联目标类型")
    private String targetType;

    /** 关联设备ID */
    @Excel(name = "关联设备ID")
    private Long equipmentMasterId;

    /** 禁止维护开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "禁止维护开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 禁止维护结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "禁止维护结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 设置原因 */
    @Excel(name = "设置原因")
    private String reason;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTargetType(String targetType) 
    {
        this.targetType = targetType;
    }

    public String getTargetType() 
    {
        return targetType;
    }

    public void setEquipmentMasterId(Long equipmentMasterId) 
    {
        this.equipmentMasterId = equipmentMasterId;
    }

    public Long getEquipmentMasterId() 
    {
        return equipmentMasterId;
    }

    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }

    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }

    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("targetType", getTargetType())
            .append("equipmentMasterId", getEquipmentMasterId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("reason", getReason())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
