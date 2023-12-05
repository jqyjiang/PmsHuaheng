package com.hh.pms.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 我的需求任务对象 procurement_task
 * 
 * @author ruoyi
 * @date 2023-11-27
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
    @Excel(name = "物料ID")
    private Long materialId;

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
    @Excel(name = "是否自动合单")
    private Long mergeOrders;

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

    public void setPurchaser(String purchaser) 
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
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

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public String getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(String companiesId) {
        this.companiesId = companiesId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
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
    public void setMergeOrders(Long mergeOrders) 
    {
        this.mergeOrders = mergeOrders;
    }

    public Long getMergeOrders() 
    {
        return mergeOrders;
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
                ", materialId=" + materialId +
                ", currencyId=" + currencyId +
                ", acceptanceStrategy='" + acceptanceStrategy + '\'' +
                ", taskNumber=" + taskNumber +
                ", acceptedQuantity=" + acceptedQuantity +
                ", taskOccupied=" + taskOccupied +
                ", taskAccepted=" + taskAccepted +
                ", mergeOrders=" + mergeOrders +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
