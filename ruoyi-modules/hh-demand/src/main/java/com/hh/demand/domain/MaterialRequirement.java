package com.hh.demand.domain;

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
 * @date 2023-11-22
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
    private Long companiesId;

    /** 预估总金额 */
    private BigDecimal totalAmount;

    /** 收货联系人 */
    private String contactPerson;

    /** 费用部门 */
    private String expenseDepartment;

    /** 主要用途及说明 */
    private String description;

    /** 地址 */
    private String address;

    /** 联系方式 */
    private String phone;

    /** 附件 */
    private String annex;

    /** 币种ID */
    private Long currencyId;

    /** 相关项目 */
    private String relatedProjects;

    /** 需求物料ID */
    private Long materialId;

    /** 需求物料数量 */
    private BigDecimal requiredMaterials;

    /** 需求到货时间 */
    private Date deliveryTime;

    /** 自动分配 */
    private Long automaticAssign;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("requirementId", getRequirementId())
            .append("requirementCode", getRequirementCode())
            .append("requirementCreator", getRequirementCreator())
            .append("requirementTitle", getRequirementTitle())
            .append("createDepartment", getCreateDepartment())
            .append("createTime", getCreateTime())
            .append("demander", getDemander())
            .append("demandDepartment", getDemandDepartment())
            .append("requirementType", getRequirementType())
            .append("companiesId", getCompaniesId())
            .append("totalAmount", getTotalAmount())
            .append("contactPerson", getContactPerson())
            .append("expenseDepartment", getExpenseDepartment())
            .append("description", getDescription())
            .append("address", getAddress())
            .append("phone", getPhone())
            .append("annex", getAnnex())
            .append("currencyId", getCurrencyId())
            .append("relatedProjects", getRelatedProjects())
            .append("materialId", getMaterialId())
            .append("requiredMaterials", getRequiredMaterials())
            .append("deliveryTime", getDeliveryTime())
            .append("automaticAssign", getAutomaticAssign())
            .toString();
    }
}
