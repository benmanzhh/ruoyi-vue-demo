package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备归档历史表对象 dev_archived_components
 * * @author ruoyi
 * @date 2025-07-25
 */
public class DevArchivedComponents extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 历史记录主键ID */
    private Long id;

    /** 关联的计划ID (新增) */
    private Long planId;

    /** 关联的部件ID */
    private Long componentId;

    /** 所属设备ID */
    @Excel(name = "所属设备ID")
    private String masterEquipmentId;

    /** 部件名称 */
    @Excel(name = "部件名称")
    private String componentName;

    /** 原部件ID(字符) */
    @Excel(name = "原部件ID(字符)")
    private String componentIdStr;

    /** 投入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "投入时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date installDate;

    /** 最大年限 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最大年限", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date maxLifespanDate;

    /** 质保时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "质保时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date warrantyEndDate;

    /** 维修规范 */
    @Excel(name = "维修规范")
    private String maintenanceRules;

    /** 归档时的维保商 */
    @Excel(name = "归档时的维保商")
    private String vendorName;

    /** 归档日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "归档日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date archiveDate;

    /** 归档原因 */
    @Excel(name = "归档原因")
    private String reasonForArchival;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    /**
     * 新增 planId 的 getter 和 setter
     */
    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public void setComponentId(Long componentId)
    {
        this.componentId = componentId;
    }

    public Long getComponentId()
    {
        return componentId;
    }
    public void setMasterEquipmentId(String masterEquipmentId)
    {
        this.masterEquipmentId = masterEquipmentId;
    }

    public String getMasterEquipmentId()
    {
        return masterEquipmentId;
    }
    public void setComponentName(String componentName)
    {
        this.componentName = componentName;
    }

    public String getComponentName()
    {
        return componentName;
    }
    public void setComponentIdStr(String componentIdStr)
    {
        this.componentIdStr = componentIdStr;
    }

    public String getComponentIdStr()
    {
        return componentIdStr;
    }
    public void setInstallDate(Date installDate)
    {
        this.installDate = installDate;
    }

    public Date getInstallDate()
    {
        return installDate;
    }
    public void setMaxLifespanDate(Date maxLifespanDate)
    {
        this.maxLifespanDate = maxLifespanDate;
    }

    public Date getMaxLifespanDate()
    {
        return maxLifespanDate;
    }
    public void setWarrantyEndDate(Date warrantyEndDate)
    {
        this.warrantyEndDate = warrantyEndDate;
    }

    public Date getWarrantyEndDate()
    {
        return warrantyEndDate;
    }
    public void setMaintenanceRules(String maintenanceRules)
    {
        this.maintenanceRules = maintenanceRules;
    }

    public String getMaintenanceRules()
    {
        return maintenanceRules;
    }
    public void setVendorName(String vendorName)
    {
        this.vendorName = vendorName;
    }

    public String getVendorName()
    {
        return vendorName;
    }
    public void setArchiveDate(Date archiveDate)
    {
        this.archiveDate = archiveDate;
    }

    public Date getArchiveDate()
    {
        return archiveDate;
    }
    public void setReasonForArchival(String reasonForArchival)
    {
        this.reasonForArchival = reasonForArchival;
    }

    public String getReasonForArchival()
    {
        return reasonForArchival;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("planId", getPlanId()) // 新增
                .append("componentId", getComponentId())
                .append("masterEquipmentId", getMasterEquipmentId())
                .append("componentName", getComponentName())
                .append("componentIdStr", getComponentIdStr())
                .append("installDate", getInstallDate())
                .append("maxLifespanDate", getMaxLifespanDate())
                .append("warrantyEndDate", getWarrantyEndDate())
                .append("maintenanceRules", getMaintenanceRules())
                .append("vendorName", getVendorName())
                .append("archiveDate", getArchiveDate())
                .append("reasonForArchival", getReasonForArchival())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
