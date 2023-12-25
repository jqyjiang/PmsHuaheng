package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购需求池对象 material_information
 *
 * @author ruoyi
 * @date 2023-12-06
 */
public class MaterialInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer miId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料品类 */
    @Excel(name = "物料品类")
    private String materialCategory;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpecification;

    /** 物料型号 */
    @Excel(name = "物料型号")
    private String materialModel;

    /** 物料品牌 */
    @Excel(name = "物料品牌")
    private String materialBrand;

    /** 物料单位 */
    @Excel(name = "物料单位")
    private String materialUnit;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private Long mustNumber;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date mustDate;

    /** 币种 */
    @Excel(name = "币种")
    private Long currencyName;

    /** 参考价格 */
    @Excel(name = "参考价格")
    private BigDecimal referencePrice;

    /** 预算单价(不含税) */
    @Excel(name = "预算单价(不含税)")
    private BigDecimal unitPrice;

    /** 税率 */
    @Excel(name = "税率")
    private String tax;

    /** 税率值 */
    @Excel(name = "税率值")
    private String rateValue;

    /** 行预算金额 */
    @Excel(name = "行预算金额")
    private String budgetAmount;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 附件 */
    @Excel(name = "附件")
    private String annex;

    /** 采购员 */
    @Excel(name = "采购员")
    private String purchaser;

    /** 需求编号 */
    @Excel(name = "需求编号")
    private String requirementCode;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 物料维护信息 */
    private List<MaterialPool> materialPoolList;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public void setMiId(Integer miId)
    {
        this.miId = miId;
    }

    public Integer getMiId()
    {
        return miId;
    }
    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode()
    {
        return materialCode;
    }
    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getMaterialName()
    {
        return materialName;
    }
    public void setMaterialCategory(String materialCategory)
    {
        this.materialCategory = materialCategory;
    }

    public String getMaterialCategory()
    {
        return materialCategory;
    }
    public void setMaterialSpecification(String materialSpecification)
    {
        this.materialSpecification = materialSpecification;
    }

    public String getMaterialSpecification()
    {
        return materialSpecification;
    }
    public void setMaterialModel(String materialModel)
    {
        this.materialModel = materialModel;
    }

    public String getMaterialModel()
    {
        return materialModel;
    }
    public void setMaterialBrand(String materialBrand)
    {
        this.materialBrand = materialBrand;
    }

    public String getMaterialBrand()
    {
        return materialBrand;
    }
    public void setMaterialUnit(String materialUnit)
    {
        this.materialUnit = materialUnit;
    }

    public String getMaterialUnit()
    {
        return materialUnit;
    }
    public void setMustNumber(Long mustNumber)
    {
        this.mustNumber = mustNumber;
    }

    public Long getMustNumber()
    {
        return mustNumber;
    }
    public void setMustDate(Date mustDate)
    {
        this.mustDate = mustDate;
    }

    public Date getMustDate()
    {
        return mustDate;
    }
    public void setCurrencyName(Long currencyName)
    {
        this.currencyName = currencyName;
    }

    public Long getCurrencyName()
    {
        return currencyName;
    }
    public void setReferencePrice(BigDecimal referencePrice)
    {
        this.referencePrice = referencePrice;
    }

    public BigDecimal getReferencePrice()
    {
        return referencePrice;
    }
    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }
    public void setTax(String tax)
    {
        this.tax = tax;
    }

    public String getTax()
    {
        return tax;
    }
    public void setRateValue(String rateValue)
    {
        this.rateValue = rateValue;
    }

    public String getRateValue()
    {
        return rateValue;
    }
    public void setBudgetAmount(String budgetAmount)
    {
        this.budgetAmount = budgetAmount;
    }

    public String getBudgetAmount()
    {
        return budgetAmount;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setAnnex(String annex)
    {
        this.annex = annex;
    }

    public String getAnnex()
    {
        return annex;
    }
    public void setPurchaser(String purchaser)
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser()
    {
        return purchaser;
    }
    public void setRequirementCode(String requirementCode)
    {
        this.requirementCode = requirementCode;
    }

    public String getRequirementCode()
    {
        return requirementCode;
    }

    public List<MaterialPool> getMaterialPoolList() {
        return materialPoolList;
    }

    public void setMaterialPoolList(List<MaterialPool> materialPoolList) {
        this.materialPoolList = materialPoolList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("miId", getMiId())
                .append("materialCode", getMaterialCode())
                .append("materialName", getMaterialName())
                .append("materialCategory", getMaterialCategory())
                .append("materialSpecification", getMaterialSpecification())
                .append("materialModel", getMaterialModel())
                .append("materialBrand", getMaterialBrand())
                .append("materialUnit", getMaterialUnit())
                .append("mustNumber", getMustNumber())
                .append("mustDate", getMustDate())
                .append("currencyName", getCurrencyName())
                .append("referencePrice", getReferencePrice())
                .append("unitPrice", getUnitPrice())
                .append("tax", getTax())
                .append("rateValue", getRateValue())
                .append("budgetAmount", getBudgetAmount())
                .append("remarks", getRemarks())
                .append("annex", getAnnex())
                .append("purchaser", getPurchaser())
                .append("requirementCode", getRequirementCode())
                .append("materialPoolList", getMaterialPoolList())
                .append("status",getStatus())
                .toString();
    }

    /**  需求申请字段  */
    /** 需求申请ID */
    private Long requirementId;


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


    private List<Material> materialList;

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    // 任务
    /** 任务ID */
    private Long taskId;


    /** 采购策略 */
    @Excel(name = "采购策略")
    private String procurementStrategy;

    /** 任务总金额 */
    @Excel(name = "任务总金额")
    private BigDecimal taskTotal;


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
