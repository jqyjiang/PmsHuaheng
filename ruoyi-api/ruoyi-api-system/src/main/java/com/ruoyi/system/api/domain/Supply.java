package com.ruoyi.system.api.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 供货管理对象 supply
 *
 * @author ruoyi
 * @date 2023-12-06
 */
public class Supply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 供货id */
    private Long supplyId;

    /** 供应商id */
    @Excel(name = "供应商id")
    private Long sdId;

    /** 供应商详细编码 */
    @Excel(name = "供应商详细编码")
    private String sdCode;

    @Excel(name = "供应商名称")
    private String sbiName;

    /** 生命周期id */
    @Excel(name = "生命周期id")
    private Long lifecycleId;

    /** 生命周期 */
    @Excel(name = "生命周期")
    private String cycle;
    /** 公司 */
    @Excel(name = "公司")
    private Long companiesId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String founder;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creationTime;

    /** 最后更新人 */
    @Excel(name = "最后更新人")
    private String lastUpdatedBy;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String notes;

    /** 创建人部门 */
    @Excel(name = "创建人部门")
    private String createdByEpartment;

    @Excel(name = "企业名称")
    private String companiesName;

    /** 供应商物料信息 */
    private List<SupplierMaterialList> supplierMaterialList;

    public Long getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(Long companiesId) {
        this.companiesId = companiesId;
    }

    public String getCompaniesName() {
        return companiesName;
    }

    public void setCompaniesName(String companiesName) {
        this.companiesName = companiesName;
    }

    /** 企业名称 */

    public void setSupplyId(Long supplyId)
    {
        this.supplyId = supplyId;
    }

    public Long getSupplyId()
    {
        return supplyId;
    }
    public void setSdId(Long sdId)
    {
        this.sdId = sdId;
    }

    public Long getSdId()
    {
        return sdId;
    }
    public void setFounder(String founder)
    {
        this.founder = founder;
    }

    public String getFounder()
    {
        return founder;
    }
    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    public Date getCreationTime()
    {
        return creationTime;
    }
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setCreatedByEpartment(String createdByEpartment)
    {
        this.createdByEpartment = createdByEpartment;
    }

    public String getCreatedByEpartment()
    {
        return createdByEpartment;
    }

    public String getSdCode() {
        return sdCode;
    }

    public void setSdCode(String sdCode) {
        this.sdCode = sdCode;
    }

    public String getSbiName() {
        return sbiName;
    }

    public void setSbiName(String sbiName) {
        this.sbiName = sbiName;
    }

    public Long getLifecycleId() {
        return lifecycleId;
    }

    public void setLifecycleId(Long lifecycleId) {
        this.lifecycleId = lifecycleId;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public List<SupplierMaterialList> getSupplierMaterialList() {
        return supplierMaterialList;
    }

    public void setSupplierMaterialList(List<SupplierMaterialList> supplierMaterialList) {
        this.supplierMaterialList = supplierMaterialList;
    }

    @Override
    public String toString() {
        return "Supply{" +
                "supplyId=" + supplyId +
                ", sdId=" + sdId +
                ", sdCode='" + sdCode + '\'' +
                ", sbiName='" + sbiName + '\'' +
                ", lifecycleId=" + lifecycleId +
                ", cycle='" + cycle + '\'' +
                ", companiesId=" + companiesId +
                ", founder='" + founder + '\'' +
                ", creationTime=" + creationTime +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", notes='" + notes + '\'' +
                ", createdByEpartment='" + createdByEpartment + '\'' +
                ", companiesName='" + companiesName + '\'' +
                ", supplierMaterialList=" + supplierMaterialList +
                '}';
    }
}
