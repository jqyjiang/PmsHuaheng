package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 入库单物料对象 inbound_material
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public class InboundMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderCode;

    /** 行号 */
    @Excel(name = "行号")
    private Long lineNumber;

    /** 物料编号 */
    @Excel(name = "物料编号")
    private String orCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String orName;

    /** 物料类别 */
    @Excel(name = "物料类别")
    private String materialCategory;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpecification;

    /** 物料单位 */
    @Excel(name = "物料单位")
    private String materialUnit;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private BigDecimal requiredQuantity;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;

    /** 收货数量 */
    @Excel(name = "收货数量")
    private BigDecimal receivedQuantity;

    /** 质检处理措施 */
    @Excel(name = "质检处理措施")
    private Long handlingMeasuresName;

    /** 质检合格数量 */
    @Excel(name = "质检合格数量")
    private BigDecimal inspectQualifiedNumber;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private BigDecimal stockInQuantity;

    /** 退换货数量 */
    @Excel(name = "退换货数量")
    private BigDecimal returnExchangeQuantity;

    /** 退换货原因 */
    @Excel(name = "退换货原因")
    private String returnExchangeReason;

    /** 收货人 */
    @Excel(name = "收货人")
    private String consignee;

    /** 收货人电话号码 */
    @Excel(name = "收货人电话号码")
    private String receivingPhone;

    /** 库房 */
    @Excel(name = "库房")
    private String inventory;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setLineNumber(Long lineNumber) 
    {
        this.lineNumber = lineNumber;
    }

    public Long getLineNumber() 
    {
        return lineNumber;
    }
    public void setOrCode(String orCode) 
    {
        this.orCode = orCode;
    }

    public String getOrCode() 
    {
        return orCode;
    }
    public void setOrName(String orName) 
    {
        this.orName = orName;
    }

    public String getOrName() 
    {
        return orName;
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
    public void setMaterialUnit(String materialUnit) 
    {
        this.materialUnit = materialUnit;
    }

    public String getMaterialUnit() 
    {
        return materialUnit;
    }
    public void setRequiredQuantity(BigDecimal requiredQuantity) 
    {
        this.requiredQuantity = requiredQuantity;
    }

    public BigDecimal getRequiredQuantity() 
    {
        return requiredQuantity;
    }
    public void setDeliveryDate(Date deliveryDate) 
    {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() 
    {
        return deliveryDate;
    }
    public void setReceivedQuantity(BigDecimal receivedQuantity) 
    {
        this.receivedQuantity = receivedQuantity;
    }

    public BigDecimal getReceivedQuantity() 
    {
        return receivedQuantity;
    }

    public Long getHandlingMeasuresName() {
        return handlingMeasuresName;
    }

    public void setHandlingMeasuresName(Long handlingMeasuresName) {
        this.handlingMeasuresName = handlingMeasuresName;
    }

    public void setInspectQualifiedNumber(BigDecimal inspectQualifiedNumber)
    {
        this.inspectQualifiedNumber = inspectQualifiedNumber;
    }

    public BigDecimal getInspectQualifiedNumber() 
    {
        return inspectQualifiedNumber;
    }
    public void setStockInQuantity(BigDecimal stockInQuantity) 
    {
        this.stockInQuantity = stockInQuantity;
    }

    public BigDecimal getStockInQuantity() 
    {
        return stockInQuantity;
    }
    public void setReturnExchangeQuantity(BigDecimal returnExchangeQuantity) 
    {
        this.returnExchangeQuantity = returnExchangeQuantity;
    }

    public BigDecimal getReturnExchangeQuantity() 
    {
        return returnExchangeQuantity;
    }
    public void setReturnExchangeReason(String returnExchangeReason) 
    {
        this.returnExchangeReason = returnExchangeReason;
    }

    public String getReturnExchangeReason() 
    {
        return returnExchangeReason;
    }
    public void setConsignee(String consignee) 
    {
        this.consignee = consignee;
    }

    public String getConsignee() 
    {
        return consignee;
    }
    public void setReceivingPhone(String receivingPhone) 
    {
        this.receivingPhone = receivingPhone;
    }

    public String getReceivingPhone() 
    {
        return receivingPhone;
    }
    public void setInventory(String inventory) 
    {
        this.inventory = inventory;
    }

    public String getInventory() 
    {
        return inventory;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderCode", getOrderCode())
            .append("lineNumber", getLineNumber())
            .append("orCode", getOrCode())
            .append("orName", getOrName())
            .append("materialCategory", getMaterialCategory())
            .append("materialSpecification", getMaterialSpecification())
            .append("materialUnit", getMaterialUnit())
            .append("requiredQuantity", getRequiredQuantity())
            .append("deliveryDate", getDeliveryDate())
            .append("receivedQuantity", getReceivedQuantity())
            .append("handlingMeasuresName", getHandlingMeasuresName())
            .append("inspectQualifiedNumber", getInspectQualifiedNumber())
            .append("stockInQuantity", getStockInQuantity())
            .append("returnExchangeQuantity", getReturnExchangeQuantity())
            .append("returnExchangeReason", getReturnExchangeReason())
            .append("consignee", getConsignee())
            .append("receivingPhone", getReceivingPhone())
            .append("inventory", getInventory())
            .append("remarks", getRemarks())
            .toString();
    }
}
