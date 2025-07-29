package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维护人员信息对象 dev_personnel
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public class DevPersonnel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 人员主键ID */
    private Long id;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String personnelId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 人员属性 */
    @Excel(name = "人员属性")
    private String personnelType;

    /** 证书要求 */
    @Excel(name = "证书要求")
    private String certificateInfo;

    /** 证书拍照 */
    @Excel(name = "证书拍照")
    private String certificatePhotoUrl;

    /** 证书到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "证书到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date certificateExpiryDate;

    /** 技能考核打分情况 */
    @Excel(name = "技能考核打分情况")
    private BigDecimal skillScore;

    /** 维护人员权限信息 */
    @Excel(name = "维护人员权限信息")
    private String permissions;

    /** 所属供应商ID */
    @Excel(name = "所属供应商ID")
    private Long vendorId;

    private List<DevVendors> devVendorsList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPersonnelId(String personnelId) 
    {
        this.personnelId = personnelId;
    }

    public String getPersonnelId() 
    {
        return personnelId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPersonnelType(String personnelType) 
    {
        this.personnelType = personnelType;
    }

    public String getPersonnelType() 
    {
        return personnelType;
    }
    public void setCertificateInfo(String certificateInfo) 
    {
        this.certificateInfo = certificateInfo;
    }

    public String getCertificateInfo() 
    {
        return certificateInfo;
    }
    public void setCertificatePhotoUrl(String certificatePhotoUrl) 
    {
        this.certificatePhotoUrl = certificatePhotoUrl;
    }

    public String getCertificatePhotoUrl() 
    {
        return certificatePhotoUrl;
    }
    public void setCertificateExpiryDate(Date certificateExpiryDate) 
    {
        this.certificateExpiryDate = certificateExpiryDate;
    }

    public Date getCertificateExpiryDate() 
    {
        return certificateExpiryDate;
    }
    public void setSkillScore(BigDecimal skillScore) 
    {
        this.skillScore = skillScore;
    }

    public BigDecimal getSkillScore() 
    {
        return skillScore;
    }
    public void setPermissions(String permissions) 
    {
        this.permissions = permissions;
    }

    public String getPermissions() 
    {
        return permissions;
    }
    public void setVendorId(Long vendorId) 
    {
        this.vendorId = vendorId;
    }

    public Long getVendorId() 
    {
        return vendorId;
    }

    /**
     * Getter and Setter for devVendorsList (Added)
     */
    public List<DevVendors> getDevVendorsList()
    {
        return devVendorsList;
    }

    public void setDevVendorsList(List<DevVendors> devVendorsList)
    {
        this.devVendorsList = devVendorsList;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("personnelId", getPersonnelId())
            .append("name", getName())
            .append("personnelType", getPersonnelType())
            .append("certificateInfo", getCertificateInfo())
            .append("certificatePhotoUrl", getCertificatePhotoUrl())
            .append("certificateExpiryDate", getCertificateExpiryDate())
            .append("skillScore", getSkillScore())
            .append("permissions", getPermissions())
            .append("vendorId", getVendorId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
                .append("devVendorsList", getDevVendorsList())
                .toString();
    }
}
