package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 订单入库管理对象 inbound_note
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public class InboundNote extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String stockInNoteNo;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inboundDate;

    /** 入库人 */
    @Excel(name = "入库人")
    private String stockInPerson;

    /** 入库总数量 */
    @Excel(name = "入库总数量")
    private BigDecimal stockInQuantity;

    /** 入库总金额 */
    @Excel(name = "入库总金额")
    private BigDecimal stockInAmount;
    /**
     * 入库不含税金额
     */
    private BigDecimal stockInNoTaxAmount;

    /** 入库税额 */
    @Excel(name = "入库税额")
    private BigDecimal taxAmount;

    /** 入库备注 */
    @Excel(name = "入库备注")
    private String remarks;

    /** 附件 */
    @Excel(name = "附件")
    private String annex;

    /** 退货总数量 */
    @Excel(name = "退货总数量")
    private BigDecimal returnQuantity;

    /** 退货含税金额 */
    @Excel(name = "退货含税金额")
    private BigDecimal returnTaxIncludedAmount;

    /** 退货不含税金额 */
    @Excel(name = "退货不含税金额")
    private BigDecimal returnTaxExcludedAmount;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 入库单物料信息 */
    private List<InboundMaterial> inboundMaterialList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStockInNoteNo(String stockInNoteNo) 
    {
        this.stockInNoteNo = stockInNoteNo;
    }

    public String getStockInNoteNo() 
    {
        return stockInNoteNo;
    }
    public void setInboundDate(Date inboundDate) 
    {
        this.inboundDate = inboundDate;
    }

    public Date getInboundDate() 
    {
        return inboundDate;
    }
    public void setStockInPerson(String stockInPerson) 
    {
        this.stockInPerson = stockInPerson;
    }

    public String getStockInPerson() 
    {
        return stockInPerson;
    }
    public void setStockInQuantity(BigDecimal stockInQuantity) 
    {
        this.stockInQuantity = stockInQuantity;
    }

    public BigDecimal getStockInNoTaxAmount() {
        return stockInNoTaxAmount;
    }

    public void setStockInNoTaxAmount(BigDecimal stockInNoTaxAmount) {
        this.stockInNoTaxAmount = stockInNoTaxAmount;
    }

    public BigDecimal getStockInQuantity()
    {
        return stockInQuantity;
    }
    public void setStockInAmount(BigDecimal stockInAmount) 
    {
        this.stockInAmount = stockInAmount;
    }

    public BigDecimal getStockInAmount() 
    {
        return stockInAmount;
    }
    public void setTaxAmount(BigDecimal taxAmount) 
    {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxAmount() 
    {
        return taxAmount;
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
    public void setReturnQuantity(BigDecimal returnQuantity) 
    {
        this.returnQuantity = returnQuantity;
    }

    public BigDecimal getReturnQuantity() 
    {
        return returnQuantity;
    }
    public void setReturnTaxIncludedAmount(BigDecimal returnTaxIncludedAmount) 
    {
        this.returnTaxIncludedAmount = returnTaxIncludedAmount;
    }

    public BigDecimal getReturnTaxIncludedAmount() 
    {
        return returnTaxIncludedAmount;
    }
    public void setReturnTaxExcludedAmount(BigDecimal returnTaxExcludedAmount) 
    {
        this.returnTaxExcludedAmount = returnTaxExcludedAmount;
    }

    public BigDecimal getReturnTaxExcludedAmount() 
    {
        return returnTaxExcludedAmount;
    }
    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    public List<InboundMaterial> getInboundMaterialList()
    {
        return inboundMaterialList;
    }

    public void setInboundMaterialList(List<InboundMaterial> inboundMaterialList)
    {
        this.inboundMaterialList = inboundMaterialList;
    }

    @Override
    public String toString() {
        return "InboundNote{" +
                "id=" + id +
                ", stockInNoteNo='" + stockInNoteNo + '\'' +
                ", inboundDate=" + inboundDate +
                ", stockInPerson='" + stockInPerson + '\'' +
                ", stockInQuantity=" + stockInQuantity +
                ", stockInAmount=" + stockInAmount +
                ", stockInNoTaxAmount=" + stockInNoTaxAmount +
                ", taxAmount=" + taxAmount +
                ", remarks='" + remarks + '\'' +
                ", annex='" + annex + '\'' +
                ", returnQuantity=" + returnQuantity +
                ", returnTaxIncludedAmount=" + returnTaxIncludedAmount +
                ", returnTaxExcludedAmount=" + returnTaxExcludedAmount +
                ", createdTime=" + createdTime +
                ", updatedAt=" + updatedAt +
                ", inboundMaterialList=" + inboundMaterialList +
                '}';
    }
}
