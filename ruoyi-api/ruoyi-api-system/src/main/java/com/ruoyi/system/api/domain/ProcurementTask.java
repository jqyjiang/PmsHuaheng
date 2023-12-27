package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的需求任务对象 procurement_task
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
public class ProcurementTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long taskId;

    /** 任务单号 */
    @Excel(name = "任务单号")
    private String taskCode;

    /** 采购策略 */
    @Excel(name = "采购策略")
    private String procurementStrategy;

    /** 任务总金额 */
    @Excel(name = "任务总金额")
    private BigDecimal taskTotal;

    /** 需求申请ID */
    @Excel(name = "需求申请ID")
    private Long requirementId;

    /** 公司ID */
    @Excel(name = "公司ID")
    private String companiesId;

    /** 采购员 */
    @Excel(name = "采购员")
    private String purchaser;

    /** 物料ID */
//    @Excel(name = "物料ID")
//    private Long materialId;

    /** 币种ID */
    @Excel(name = "币种ID")
    private Long currencyId;

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

    /** 是否自动合单 */
//    @Excel(name = "是否自动合单")
//    private Long mergeOrders;

    /** 状态 */
    @Excel(name = "状态")
    private Long taskStatus;

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setTaskCode(String taskCode) 
    {
        this.taskCode = taskCode;
    }

    public String getTaskCode() 
    {
        return taskCode;
    }
    public void setProcurementStrategy(String procurementStrategy) 
    {
        this.procurementStrategy = procurementStrategy;
    }

    public String getProcurementStrategy() 
    {
        return procurementStrategy;
    }
    public void setTaskTotal(BigDecimal taskTotal) 
    {
        this.taskTotal = taskTotal;
    }

    public BigDecimal getTaskTotal() 
    {
        return taskTotal;
    }

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public void setPurchaser(String purchaser)
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
    }

    public String getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(String companiesId) {
        this.companiesId = companiesId;
    }

//    public Long getMaterialId() {
//        return materialId;
//    }
//
//    public void setMaterialId(Long materialId) {
//        this.materialId = materialId;
//    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public void setAcceptanceStrategy(String acceptanceStrategy)
    {
        this.acceptanceStrategy = acceptanceStrategy;
    }

    public String getAcceptanceStrategy() 
    {
        return acceptanceStrategy;
    }
    public void setTaskNumber(BigDecimal taskNumber) 
    {
        this.taskNumber = taskNumber;
    }

    public BigDecimal getTaskNumber() 
    {
        return taskNumber;
    }
    public void setAcceptedQuantity(BigDecimal acceptedQuantity) 
    {
        this.acceptedQuantity = acceptedQuantity;
    }

    public BigDecimal getAcceptedQuantity() 
    {
        return acceptedQuantity;
    }
    public void setTaskOccupied(BigDecimal taskOccupied) 
    {
        this.taskOccupied = taskOccupied;
    }

    public BigDecimal getTaskOccupied() 
    {
        return taskOccupied;
    }
    public void setTaskAccepted(BigDecimal taskAccepted) 
    {
        this.taskAccepted = taskAccepted;
    }

    public BigDecimal getTaskAccepted() 
    {
        return taskAccepted;
    }

    public void setTaskStatus(Long taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public Long getTaskStatus() 
    {
        return taskStatus;
    }

    @Override
    public String toString() {
        return "ProcurementTask{" +
                "taskId=" + taskId +
                ", taskCode='" + taskCode + '\'' +
                ", procurementStrategy='" + procurementStrategy + '\'' +
                ", taskTotal=" + taskTotal +
                ", requirementId=" + requirementId +
                ", companiesId='" + companiesId + '\'' +
                ", purchaser='" + purchaser + '\'' +
                ", currencyId=" + currencyId +
                ", acceptanceStrategy='" + acceptanceStrategy + '\'' +
                ", taskNumber=" + taskNumber +
                ", acceptedQuantity=" + acceptedQuantity +
                ", taskOccupied=" + taskOccupied +
                ", taskAccepted=" + taskAccepted +
                ", taskStatus=" + taskStatus +
                '}';
    }
    // 物料信息
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


    /** 需求编号 */
    @Excel(name = "需求编号")
    private String requirementCode;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public Integer getMiId() {
        return miId;
    }

    public void setMiId(Integer miId) {
        this.miId = miId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getMaterialSpecification() {
        return materialSpecification;
    }

    public void setMaterialSpecification(String materialSpecification) {
        this.materialSpecification = materialSpecification;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    public String getMaterialBrand() {
        return materialBrand;
    }

    public void setMaterialBrand(String materialBrand) {
        this.materialBrand = materialBrand;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public Long getMustNumber() {
        return mustNumber;
    }

    public void setMustNumber(Long mustNumber) {
        this.mustNumber = mustNumber;
    }

    public Date getMustDate() {
        return mustDate;
    }

    public void setMustDate(Date mustDate) {
        this.mustDate = mustDate;
    }

    public Long getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(Long currencyName) {
        this.currencyName = currencyName;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public String getRequirementCode() {
        return requirementCode;
    }

    public void setRequirementCode(String requirementCode) {
        this.requirementCode = requirementCode;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }




}
