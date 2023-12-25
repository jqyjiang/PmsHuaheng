package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 收货明细信息对象 receipt_details
 * 
 * @author ruoyi
 * @date 2023-12-20
 */
public class ReceiptDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键，自增ID */
    private Long id;

    /** 序号 */
    @Excel(name = "序号")
    private Long serialNumber;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderCode;

    /** 发货单号 */
    @Excel(name = "发货单号")
    private String deliveryNoteNo;

    /** 物料编号 */
    @Excel(name = "物料编号")
    private String orCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String orName;

    /** 物料分类 */
    @Excel(name = "物料分类")
    private String materialCategory;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpecification;

    /** 物料单位 */
    @Excel(name = "物料单位")
    private String materialUnit;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private BigDecimal requireNumber;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requireTime;

    /** 发货数量 */
    @Excel(name = "发货数量")
    private BigDecimal deliveryQuantity;

    /** 拒收数量 */
    @Excel(name = "拒收数量")
    private BigDecimal rejectedQuantity;

    /** 拒收原因 */
    @Excel(name = "拒收原因")
    private String rejectionReason;

    /** 收货数量 */
    @Excel(name = "收货数量")
    private Long receivedQuantity;

    /** 不含税单价（元） */
    @Excel(name = "不含税单价", readConverterExp = "元=")
    private BigDecimal noTaxPrice;

    /** 税率 */
    @Excel(name = "税率")
    private String taxCode;

    /** 税率值（%） */
    @Excel(name = "税率值", readConverterExp = "%=")
    private BigDecimal tax;

    /** 含税单价 */
    @Excel(name = "含税单价")
    private BigDecimal taxPrice;

    /** 收货人 */
    /** 收货人 */
    @Excel(name = "收货人")
    private String consignee;

    /** 收货电话 */
    @Excel(name = "收货电话")
    private String receivingPhone;

    /** 库房 */
    @Excel(name = "库房")
    private String warehouse;

    /** 供货能力免检 */
    @Excel(name = "供货能力免检")
    private Long exemptFromSupplyCapacityCheck;

    /** 物料免检 */
    @Excel(name = "物料免检")
    private Long exemptFromMaterialInspection;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;
    /**
     * 公司
     */
    private String company;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSerialNumber(Long serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public Long getSerialNumber() 
    {
        return serialNumber;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setDeliveryNoteNo(String deliveryNoteNo) 
    {
        this.deliveryNoteNo = deliveryNoteNo;
    }

    public String getDeliveryNoteNo() 
    {
        return deliveryNoteNo;
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
    public void setRequireNumber(BigDecimal requireNumber) 
    {
        this.requireNumber = requireNumber;
    }

    public BigDecimal getRequireNumber() 
    {
        return requireNumber;
    }
    public void setRequireTime(Date requireTime) 
    {
        this.requireTime = requireTime;
    }

    public Date getRequireTime() 
    {
        return requireTime;
    }
    public void setDeliveryQuantity(BigDecimal deliveryQuantity) 
    {
        this.deliveryQuantity = deliveryQuantity;
    }

    public BigDecimal getDeliveryQuantity() 
    {
        return deliveryQuantity;
    }
    public void setRejectedQuantity(BigDecimal rejectedQuantity) 
    {
        this.rejectedQuantity = rejectedQuantity;
    }

    public BigDecimal getRejectedQuantity() 
    {
        return rejectedQuantity;
    }
    public void setRejectionReason(String rejectionReason) 
    {
        this.rejectionReason = rejectionReason;
    }

    public String getRejectionReason() 
    {
        return rejectionReason;
    }
    public void setReceivedQuantity(Long receivedQuantity) 
    {
        this.receivedQuantity = receivedQuantity;
    }

    public Long getReceivedQuantity() 
    {
        return receivedQuantity;
    }
    public void setNoTaxPrice(BigDecimal noTaxPrice) 
    {
        this.noTaxPrice = noTaxPrice;
    }

    public BigDecimal getNoTaxPrice() 
    {
        return noTaxPrice;
    }
    public void setTaxCode(String taxCode) 
    {
        this.taxCode = taxCode;
    }

    public String getTaxCode() 
    {
        return taxCode;
    }
    public void setTax(BigDecimal tax) 
    {
        this.tax = tax;
    }

    public BigDecimal getTax() 
    {
        return tax;
    }
    public void setTaxPrice(BigDecimal taxPrice) 
    {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getTaxPrice() 
    {
        return taxPrice;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getReceivingPhone() {
        return receivingPhone;
    }

    public void setReceivingPhone(String receivingPhone) {
        this.receivingPhone = receivingPhone;
    }

    public void setWarehouse(String warehouse)
    {
        this.warehouse = warehouse;
    }

    public String getWarehouse() 
    {
        return warehouse;
    }
    public void setExemptFromSupplyCapacityCheck(Long exemptFromSupplyCapacityCheck) 
    {
        this.exemptFromSupplyCapacityCheck = exemptFromSupplyCapacityCheck;
    }

    public Long getExemptFromSupplyCapacityCheck() 
    {
        return exemptFromSupplyCapacityCheck;
    }
    public void setExemptFromMaterialInspection(Long exemptFromMaterialInspection) 
    {
        this.exemptFromMaterialInspection = exemptFromMaterialInspection;
    }

    public Long getExemptFromMaterialInspection() 
    {
        return exemptFromMaterialInspection;
    }


    @Override
    public String toString() {
        return "ReceiptDetails{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", orderCode='" + orderCode + '\'' +
                ", deliveryNoteNo='" + deliveryNoteNo + '\'' +
                ", orCode='" + orCode + '\'' +
                ", orName='" + orName + '\'' +
                ", materialCategory='" + materialCategory + '\'' +
                ", materialSpecification='" + materialSpecification + '\'' +
                ", materialUnit='" + materialUnit + '\'' +
                ", requireNumber=" + requireNumber +
                ", requireTime=" + requireTime +
                ", deliveryQuantity=" + deliveryQuantity +
                ", rejectedQuantity=" + rejectedQuantity +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", receivedQuantity=" + receivedQuantity +
                ", noTaxPrice=" + noTaxPrice +
                ", taxCode='" + taxCode + '\'' +
                ", tax=" + tax +
                ", taxPrice=" + taxPrice +
                ", consignee='" + consignee + '\'' +
                ", receivingPhone='" + receivingPhone + '\'' +
                ", warehouse='" + warehouse + '\'' +
                ", exemptFromSupplyCapacityCheck=" + exemptFromSupplyCapacityCheck +
                ", exemptFromMaterialInspection=" + exemptFromMaterialInspection +
                ", remarks='" + remarks + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
