package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商信息对象 dev_vendors
 * 
 * @author ruoyi
 * @date 2025-07-28
 */
public class DevVendors extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 供应商主键ID */
    private Long id;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long vendorId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String vendorName;

    /** 供应商联系方式 */
    @Excel(name = "供应商联系方式")
    private String contactInfo;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setVendorId(Long vendorId)
    {
        this.vendorId = vendorId;
    }

    public Long getVendorId()
    {
        return vendorId;
    }
    public void setVendorName(String vendorName) 
    {
        this.vendorName = vendorName;
    }

    public String getVendorName() 
    {
        return vendorName;
    }
    public void setContactInfo(String contactInfo) 
    {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() 
    {
        return contactInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vendorId", getVendorId())
            .append("vendorName", getVendorName())
            .append("contactInfo", getContactInfo())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
