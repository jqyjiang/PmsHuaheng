package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;

/**
 * 采购需求申请对象 material_requirement
 *
 * @author ruoyi
 * @date 2023-12-05
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

    /** 任务单号 */
    @Excel(name = "任务单号")
    private String taskCode;



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

    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
    }

    public String getTaskCode()
    {
        return taskCode;
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
                .append("taskCode", getTaskCode())
                .toString();
    }


    /**  需求物料  */
    private List<MaterialInformation> materialInformations;

    public List<MaterialInformation> getMaterialInformations() {
        return materialInformations;
    }

    public void setMaterialInformations(List<MaterialInformation> materialInformations) {
        this.materialInformations = materialInformations;
    }

    /**  采购任务  */


    /** 任务ID */
    private Long taskId;


    /** 采购策略 */
    @Excel(name = "采购策略")
    private String procurementStrategy;

    /** 任务总金额 */
    @Excel(name = "任务总金额")
    private BigDecimal taskTotal;


    /** 采购员 */
    @Excel(name = "采购员")
    private String purchaser;


    /** 受理策略 */
    @Excel(name = "受理策略")
    private String acceptanceStrategy;

    /** 任务总数量 */
    @Excel(name = "任务总数量")
    private BigDecimal taskNumber;

    /** 已受理数量 */
    @Excel(name = "已受理数量")
    private BigDecimal acceptedQuantity;

    /** 占用任务数量 */
    @Excel(name = "占用任务数量")
    private BigDecimal taskOccupied;

    /** 待受理数量 */
    @Excel(name = "待受理数量")
    private BigDecimal taskAccepted;


    /** 状态 */
    @Excel(name = "状态")
    private Long taskStatus;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getProcurementStrategy() {
        return procurementStrategy;
    }

    public void setProcurementStrategy(String procurementStrategy) {
        this.procurementStrategy = procurementStrategy;
    }

    public BigDecimal getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(BigDecimal taskTotal) {
        this.taskTotal = taskTotal;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getAcceptanceStrategy() {
        return acceptanceStrategy;
    }

    public void setAcceptanceStrategy(String acceptanceStrategy) {
        this.acceptanceStrategy = acceptanceStrategy;
    }

    public BigDecimal getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(BigDecimal taskNumber) {
        this.taskNumber = taskNumber;
    }

    public BigDecimal getAcceptedQuantity() {
        return acceptedQuantity;
    }

    public void setAcceptedQuantity(BigDecimal acceptedQuantity) {
        this.acceptedQuantity = acceptedQuantity;
    }

    public BigDecimal getTaskOccupied() {
        return taskOccupied;
    }

    public void setTaskOccupied(BigDecimal taskOccupied) {
        this.taskOccupied = taskOccupied;
    }

    public BigDecimal getTaskAccepted() {
        return taskAccepted;
    }

    public void setTaskAccepted(BigDecimal taskAccepted) {
        this.taskAccepted = taskAccepted;
    }

    public Long getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Long taskStatus) {
        this.taskStatus = taskStatus;
    }
}
