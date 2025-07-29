package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备部件对象 dev_equipment_components
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public class DevEquipmentComponents extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部件主键ID */
    private Long id;

    /** 所属设备ID */
    @Excel(name = "所属设备ID")
    private Long equipmentId;

    /** 部件名称 */
    @Excel(name = "部件名称")
    private String componentName;

    /** 部件ID */
    @Excel(name = "部件ID")
    private String componentId;

    /** 部件状态 */
    @Excel(name = "部件状态")
    private String status;

    /** 投入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installDate;

    /** 使用年限 */
    @Excel(name = "使用年限")
    private Long lifespanYears;

    /** 最大年限 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最大年限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date maxLifespanDate;

    /** 质保年限 */
    @Excel(name = "质保年限")
    private Long warrantyYears;

    /** 质保时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "质保时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warrantyEndDate;

    /** 维修规范 */
    @Excel(name = "维修规范")
    private String maintenanceRules;

    /** 维修规范文件上传 */
    @Excel(name = "维修规范文件上传")
    private String rulesFileUrl;

    /** 下一次计划维护时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下一次计划维护时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextMaintenanceDate;

    /** 维护人员(员工id) */
    @Excel(name = "维护人员(员工id)")
    private Long personnelId;

    /** 维护人员信息列表 (Added to fix ReflectionException) */
    private List<DevPersonnel> devPersonnelList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setComponentName(String componentName) 
    {
        this.componentName = componentName;
    }

    public String getComponentName() 
    {
        return componentName;
    }
    public void setComponentId(String componentId) 
    {
        this.componentId = componentId;
    }

    public String getComponentId() 
    {
        return componentId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setInstallDate(Date installDate) 
    {
        this.installDate = installDate;
    }

    public Date getInstallDate() 
    {
        return installDate;
    }
    public void setLifespanYears(Long lifespanYears) 
    {
        this.lifespanYears = lifespanYears;
    }

    public Long getLifespanYears() 
    {
        return lifespanYears;
    }
    public void setMaxLifespanDate(Date maxLifespanDate) 
    {
        this.maxLifespanDate = maxLifespanDate;
    }

    public Date getMaxLifespanDate() 
    {
        return maxLifespanDate;
    }
    public void setWarrantyYears(Long warrantyYears) 
    {
        this.warrantyYears = warrantyYears;
    }

    public Long getWarrantyYears() 
    {
        return warrantyYears;
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
    public void setRulesFileUrl(String rulesFileUrl) 
    {
        this.rulesFileUrl = rulesFileUrl;
    }

    public String getRulesFileUrl() 
    {
        return rulesFileUrl;
    }
    public void setNextMaintenanceDate(Date nextMaintenanceDate) 
    {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public Date getNextMaintenanceDate() 
    {
        return nextMaintenanceDate;
    }
    public void setPersonnelId(Long personnelId) 
    {
        this.personnelId = personnelId;
    }

    public Long getPersonnelId() 
    {
        return personnelId;
    }

    /**
     * Getter and Setter for devPersonnelList (Added)
     */
    public List<DevPersonnel> getDevPersonnelList()
    {
        return devPersonnelList;
    }

    public void setDevPersonnelList(List<DevPersonnel> devPersonnelList)
    {
        this.devPersonnelList = devPersonnelList;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipmentId", getEquipmentId())
            .append("componentName", getComponentName())
            .append("componentId", getComponentId())
            .append("status", getStatus())
            .append("installDate", getInstallDate())
            .append("lifespanYears", getLifespanYears())
            .append("maxLifespanDate", getMaxLifespanDate())
            .append("warrantyYears", getWarrantyYears())
            .append("warrantyEndDate", getWarrantyEndDate())
            .append("maintenanceRules", getMaintenanceRules())
            .append("rulesFileUrl", getRulesFileUrl())
            .append("nextMaintenanceDate", getNextMaintenanceDate())
            .append("personnelId", getPersonnelId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
                .append("devPersonnelList", getDevPersonnelList())
                .toString();
    }
}
