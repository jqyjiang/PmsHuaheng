package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 到货质检对象 quality_task_list
 * 
 * @author yangtao
 * @date 2023-12-21
 */
public class QualityTaskList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 质检任务单Id */
    private Long qualityTaskListId;

    /** 采购订单编号 */
    @Excel(name = "采购订单编号")
    private String orderCode;

    /** 收货单号 */
    @Excel(name = "收货单号")
    private String receiptNoteNo;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String orName;

    /** 物料编号 */
    @Excel(name = "物料编号")
    private String orCode;

    /** 公司 */
    @Excel(name = "公司")
    private Long company;

    /** 收货数量 */
    @Excel(name = "收货数量")
    private BigDecimal receiveQuantity;

    /** 收货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveDate;

    /** 收货人 */
    @Excel(name = "收货人")
    private String consignee;

    /** 状态 */
    @Excel(name = "状态")
    private Long qualityStatus;

    public void setQualityTaskListId(Long qualityTaskListId) 
    {
        this.qualityTaskListId = qualityTaskListId;
    }

    public Long getQualityTaskListId() 
    {
        return qualityTaskListId;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setReceiptNoteNo(String receiptNoteNo) 
    {
        this.receiptNoteNo = receiptNoteNo;
    }

    public String getReceiptNoteNo() 
    {
        return receiptNoteNo;
    }
    public void setOrName(String orName) 
    {
        this.orName = orName;
    }

    public String getOrName() 
    {
        return orName;
    }
    public void setOrCode(String orCode) 
    {
        this.orCode = orCode;
    }

    public String getOrCode() 
    {
        return orCode;
    }
    public void setCompany(Long company) 
    {
        this.company = company;
    }

    public Long getCompany() 
    {
        return company;
    }
    public void setReceiveQuantity(BigDecimal receiveQuantity) 
    {
        this.receiveQuantity = receiveQuantity;
    }

    public BigDecimal getReceiveQuantity() 
    {
        return receiveQuantity;
    }
    public void setReceiveDate(Date receiveDate) 
    {
        this.receiveDate = receiveDate;
    }

    public Date getReceiveDate() 
    {
        return receiveDate;
    }
    public void setConsignee(String consignee) 
    {
        this.consignee = consignee;
    }

    public String getConsignee() 
    {
        return consignee;
    }
    public void setQualityStatus(Long qualityStatus) 
    {
        this.qualityStatus = qualityStatus;
    }

    public Long getQualityStatus() 
    {
        return qualityStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("qualityTaskListId", getQualityTaskListId())
            .append("orderCode", getOrderCode())
            .append("receiptNoteNo", getReceiptNoteNo())
            .append("orName", getOrName())
            .append("orCode", getOrCode())
            .append("company", getCompany())
            .append("receiveQuantity", getReceiveQuantity())
            .append("receiveDate", getReceiveDate())
            .append("consignee", getConsignee())
            .append("qualityStatus", getQualityStatus())
            .toString();
    }
}
