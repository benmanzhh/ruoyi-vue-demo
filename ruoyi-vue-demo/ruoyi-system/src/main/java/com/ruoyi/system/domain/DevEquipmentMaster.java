package com.ruoyi.system.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备主档对象 dev_equipment_master
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public class DevEquipmentMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 设备大类 */
    @Excel(name = "设备大类")
    private String category;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备ID */
    @Excel(name = "设备ID")
    private String equipmentId;

    /** 楼号 */
    @Excel(name = "楼号")
    private String buildingNo;

    /** 机房位置 */
    @Excel(name = "机房位置")
    private String location;

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

    /** 实际维护时间 (新增) */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际维护时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualMaintenanceDate;

    /** 维护人员ID */
    @Excel(name = "维护人员ID")
    private Long maintainerId;

    /** 设备部件信息 */
    private List<DevEquipmentComponents> devEquipmentComponentsList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setEquipmentId(String equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId() 
    {
        return equipmentId;
    }

    public void setBuildingNo(String buildingNo) 
    {
        this.buildingNo = buildingNo;
    }

    public String getBuildingNo() 
    {
        return buildingNo;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
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

    // (新增)
    public void setActualMaintenanceDate(Date actualMaintenanceDate)
    {
        this.actualMaintenanceDate = actualMaintenanceDate;
    }

    public Date getActualMaintenanceDate()
    {
        return actualMaintenanceDate;
    }


    public void setMaintainerId(Long maintainerId) 
    {
        this.maintainerId = maintainerId;
    }

    public Long getMaintainerId() 
    {
        return maintainerId;
    }

    public List<DevEquipmentComponents> getDevEquipmentComponentsList()
    {
        return devEquipmentComponentsList;
    }

    public void setDevEquipmentComponentsList(List<DevEquipmentComponents> devEquipmentComponentsList)
    {
        this.devEquipmentComponentsList = devEquipmentComponentsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("category", getCategory())
            .append("equipmentName", getEquipmentName())
            .append("equipmentId", getEquipmentId())
            .append("buildingNo", getBuildingNo())
            .append("location", getLocation())
            .append("installDate", getInstallDate())
            .append("lifespanYears", getLifespanYears())
            .append("maxLifespanDate", getMaxLifespanDate())
            .append("warrantyYears", getWarrantyYears())
            .append("warrantyEndDate", getWarrantyEndDate())
            .append("maintenanceRules", getMaintenanceRules())
            .append("rulesFileUrl", getRulesFileUrl())
            .append("nextMaintenanceDate", getNextMaintenanceDate())
                .append("actualMaintenanceDate", getActualMaintenanceDate()) // (新增)
                .append("maintainerId", getMaintainerId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("devEquipmentComponentsList", getDevEquipmentComponentsList())
            .toString();
    }
}
