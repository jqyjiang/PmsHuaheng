package com.ruoyi.system.api.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 送样对象 sample_sending
 *
 * @author ruoyi
 * @date 2023-12-21
 */
public class SampleSending extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 送样单号 */
    private Long sampleId;

    /** 送样编号 */
    @Excel(name = "送样编号")
    private String sampleCode;

    /** 送样单标题 */
    @Excel(name = "送样单标题")
    private String sampleName;

    /** 公司 */
    @Excel(name = "公司")
    private String company;

    /** 采购组织 */
    @Excel(name = "采购组织")
    private String purchasing;

    /** 采购员 */
    @Excel(name = "采购员")
    private String purchasingBy;

    /** 送样类型 */
    @Excel(name = "送样类型")
    private Long sampleType;

    /** 状态 */
    @Excel(name = "状态")
    private Long state;

    /** 采方附件 */
    @Excel(name = "采方附件")
    private String acquirerAttachments;

    /** 送样单说明 */
    @Excel(name = "送样单说明")
    private String sampleDeliveryNote;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseTime;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String sbiName;

    /** 当前操作人 */
    @Excel(name = "当前操作人")
    private String currentOperator;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contacts;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String telephone;

    /** 供应商物料信息 */
    private List<SupplierMaterialList> supplierMaterialListList;

    public void setSampleId(Long sampleId)
    {
        this.sampleId = sampleId;
    }

    public Long getSampleId()
    {
        return sampleId;
    }
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }

    public String getSampleName()
    {
        return sampleName;
    }
    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCompany()
    {
        return company;
    }
    public void setPurchasing(String purchasing)
    {
        this.purchasing = purchasing;
    }

    public String getPurchasing()
    {
        return purchasing;
    }
    public void setPurchasingBy(String purchasingBy)
    {
        this.purchasingBy = purchasingBy;
    }

    public String getPurchasingBy()
    {
        return purchasingBy;
    }
    public void setSampleType(Long sampleType)
    {
        this.sampleType = sampleType;
    }

    public Long getSampleType()
    {
        return sampleType;
    }
    public void setState(Long state)
    {
        this.state = state;
    }

    public Long getState()
    {
        return state;
    }
    public void setAcquirerAttachments(String acquirerAttachments)
    {
        this.acquirerAttachments = acquirerAttachments;
    }

    public String getAcquirerAttachments()
    {
        return acquirerAttachments;
    }
    public void setSampleDeliveryNote(String sampleDeliveryNote)
    {
        this.sampleDeliveryNote = sampleDeliveryNote;
    }

    public String getSampleDeliveryNote()
    {
        return sampleDeliveryNote;
    }
    public void setReleaseTime(Date releaseTime)
    {
        this.releaseTime = releaseTime;
    }

    public Date getReleaseTime()
    {
        return releaseTime;
    }
    public void setSbiName(String sbiName)
    {
        this.sbiName = sbiName;
    }

    public String getSbiName()
    {
        return sbiName;
    }
    public void setCurrentOperator(String currentOperator)
    {
        this.currentOperator = currentOperator;
    }

    public String getCurrentOperator()
    {
        return currentOperator;
    }
    public void setContacts(String contacts)
    {
        this.contacts = contacts;
    }

    public String getContacts()
    {
        return contacts;
    }
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public List<SupplierMaterialList> getSupplierMaterialListList()
    {
        return supplierMaterialListList;
    }

    public void setSupplierMaterialListList(List<SupplierMaterialList> supplierMaterialListList)
    {
        this.supplierMaterialListList = supplierMaterialListList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("sampleId", getSampleId())
                .append("sampleCode", getSampleCode())
                .append("sampleName", getSampleName())
                .append("company", getCompany())
                .append("purchasing", getPurchasing())
                .append("purchasingBy", getPurchasingBy())
                .append("sampleType", getSampleType())
                .append("state", getState())
                .append("acquirerAttachments", getAcquirerAttachments())
                .append("sampleDeliveryNote", getSampleDeliveryNote())
                .append("releaseTime", getReleaseTime())
                .append("sbiName", getSbiName())
                .append("currentOperator", getCurrentOperator())
                .append("contacts", getContacts())
                .append("telephone", getTelephone())
                .append("supplierMaterialListList", getSupplierMaterialListList())
                .toString();
    }
}
