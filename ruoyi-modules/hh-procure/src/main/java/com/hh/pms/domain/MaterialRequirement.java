package com.hh.pms.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 采购需求申请对象 material_requirement
 * 
 * @author ruoyi
 * @date 2023-11-24
 */
public class MaterialRequirement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 需求申请ID */
    private Long requirementId;

    /** 需求编号 */
    @Excel(name = "需求编号")
    private String requirementCode;

    /** 需求创建人 */
    @Excel(name = "需求创建人")
    private String requirementCreator;

    /** 需求标题 */
    @Excel(name = "需求标题")
    private String requirementTitle;

    /** 创建部门 */
    @Excel(name = "创建部门")
    private String createDepartment;

    /** 需求人 */
    @Excel(name = "需求人")
    private String demander;

    /** 需求部门 */
    @Excel(name = "需求部门")
    private String demandDepartment;

    /** 需求类型 */
    @Excel(name = "需求类型")
    private Long requirementType;

    /** 公司ID */
    @Excel(name = "公司ID")
    private Long companiesId;

    /** 预估总金额 */
    @Excel(name = "预估总金额")
    private BigDecimal totalAmount;

    /** 收货联系人 */
    @Excel(name = "收货联系人")
    private String contactPerson;

    /** 费用部门 */
    @Excel(name = "费用部门")
    private String expenseDepartment;

    /** 主要用途及说明 */
    @Excel(name = "主要用途及说明")
    private String description;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phone;

    /** 附件 */
    @Excel(name = "附件")
    private String annex;

    /** 币种ID */
    @Excel(name = "币种ID")
    private Long currencyId;

    /** 相关项目 */
    @Excel(name = "相关项目")
    private String relatedProjects;

    /** 需求物料ID */
    @Excel(name = "需求物料ID")
    private Long materialId;

    /** 需求物料数量 */
    @Excel(name = "需求物料数量")
    private BigDecimal requiredMaterials;

    /** 需求到货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求到货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

    /** 自动分配 */
    @Excel(name = "自动分配")
    private Long automaticAssign;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 物料信息ID */
    @Excel(name = "物料信息ID")
    private Long mi_id;

    public void setRequirementId(Long requirementId) 
    {
        this.requirementId = requirementId;
    }

    public Long getRequirementId() 
    {
        return requirementId;
    }
    public void setRequirementCode(String requirementCode) 
    {
        this.requirementCode = requirementCode;
    }

    public String getRequirementCode() 
    {
        return requirementCode;
    }
    public void setRequirementCreator(String requirementCreator) 
    {
        this.requirementCreator = requirementCreator;
    }

    public String getRequirementCreator() 
    {
        return requirementCreator;
    }
    public void setRequirementTitle(String requirementTitle) 
    {
        this.requirementTitle = requirementTitle;
    }

    public String getRequirementTitle() 
    {
        return requirementTitle;
    }
    public void setCreateDepartment(String createDepartment) 
    {
        this.createDepartment = createDepartment;
    }

    public String getCreateDepartment() 
    {
        return createDepartment;
    }
    public void setDemander(String demander) 
    {
        this.demander = demander;
    }

    public String getDemander() 
    {
        return demander;
    }
    public void setDemandDepartment(String demandDepartment) 
    {
        this.demandDepartment = demandDepartment;
    }

    public String getDemandDepartment() 
    {
        return demandDepartment;
    }
    public void setRequirementType(Long requirementType) 
    {
        this.requirementType = requirementType;
    }

    public Long getRequirementType() 
    {
        return requirementType;
    }
    public void setCompaniesId(Long companiesId) 
    {
        this.companiesId = companiesId;
    }

    public Long getCompaniesId() 
    {
        return companiesId;
    }
    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }
    public void setContactPerson(String contactPerson) 
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() 
    {
        return contactPerson;
    }
    public void setExpenseDepartment(String expenseDepartment) 
    {
        this.expenseDepartment = expenseDepartment;
    }

    public String getExpenseDepartment() 
    {
        return expenseDepartment;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAnnex(String annex) 
    {
        this.annex = annex;
    }

    public String getAnnex() 
    {
        return annex;
    }
    public void setCurrencyId(Long currencyId) 
    {
        this.currencyId = currencyId;
    }

    public Long getCurrencyId() 
    {
        return currencyId;
    }
    public void setRelatedProjects(String relatedProjects) 
    {
        this.relatedProjects = relatedProjects;
    }

    public String getRelatedProjects() 
    {
        return relatedProjects;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setRequiredMaterials(BigDecimal requiredMaterials) 
    {
        this.requiredMaterials = requiredMaterials;
    }

    public BigDecimal getRequiredMaterials() 
    {
        return requiredMaterials;
    }
    public void setDeliveryTime(Date deliveryTime) 
    {
        this.deliveryTime = deliveryTime;
    }

    public Date getDeliveryTime() 
    {
        return deliveryTime;
    }
    public void setAutomaticAssign(Long automaticAssign) 
    {
        this.automaticAssign = automaticAssign;
    }

    public Long getAutomaticAssign() 
    {
        return automaticAssign;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getMi_id() {
        return mi_id;
    }

    public void setMi_id(Long mi_id) {
        this.mi_id = mi_id;
    }

    @Override
    public String toString() {
        return "MaterialRequirement{" +
                "requirementId=" + requirementId +
                ", requirementCode='" + requirementCode + '\'' +
                ", requirementCreator='" + requirementCreator + '\'' +
                ", requirementTitle='" + requirementTitle + '\'' +
                ", createDepartment='" + createDepartment + '\'' +
                ", demander='" + demander + '\'' +
                ", demandDepartment='" + demandDepartment + '\'' +
                ", requirementType=" + requirementType +
                ", companiesId=" + companiesId +
                ", totalAmount=" + totalAmount +
                ", contactPerson='" + contactPerson + '\'' +
                ", expenseDepartment='" + expenseDepartment + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", annex='" + annex + '\'' +
                ", currencyId=" + currencyId +
                ", relatedProjects='" + relatedProjects + '\'' +
                ", materialId=" + materialId +
                ", requiredMaterials=" + requiredMaterials +
                ", deliveryTime=" + deliveryTime +
                ", automaticAssign=" + automaticAssign +
                ", status=" + status +
                ", mi_id=" + mi_id +
                '}';
    }
}
