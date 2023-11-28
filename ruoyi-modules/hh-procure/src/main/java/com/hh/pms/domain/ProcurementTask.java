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
    private Long requirementId1;

    /** 公司ID */
    @Excel(name = "公司ID")
    private String companiesId1;

    /** 采购员 */
    @Excel(name = "采购员")
    private String purchaser;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId2;

    /** 币种ID */
    @Excel(name = "币种ID")
    private Long currencyId2;

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
    public void setRequirementId1(Long requirementId1) 
    {
        this.requirementId1 = requirementId1;
    }

    public Long getRequirementId1() 
    {
        return requirementId1;
    }
    public void setCompaniesId1(String companiesId1) 
    {
        this.companiesId1 = companiesId1;
    }

    public String getCompaniesId1() 
    {
        return companiesId1;
    }
    public void setPurchaser(String purchaser) 
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
    }
    public void setMaterialId2(Long materialId2) 
    {
        this.materialId2 = materialId2;
    }

    public Long getMaterialId2() 
    {
        return materialId2;
    }
    public void setCurrencyId2(Long currencyId2) 
    {
        this.currencyId2 = currencyId2;
    }

    public Long getCurrencyId2() 
    {
        return currencyId2;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskCode", getTaskCode())
            .append("procurementStrategy", getProcurementStrategy())
            .append("taskTotal", getTaskTotal())
            .append("requirementId1", getRequirementId1())
            .append("companiesId1", getCompaniesId1())
            .append("purchaser", getPurchaser())
            .append("materialId2", getMaterialId2())
            .append("currencyId2", getCurrencyId2())
            .append("acceptanceStrategy", getAcceptanceStrategy())
            .append("taskNumber", getTaskNumber())
            .append("acceptedQuantity", getAcceptedQuantity())
            .append("taskOccupied", getTaskOccupied())
            .append("taskAccepted", getTaskAccepted())
            .append("mergeOrders", getMergeOrders())
            .append("taskStatus", getTaskStatus())
            .toString();
    }
}
