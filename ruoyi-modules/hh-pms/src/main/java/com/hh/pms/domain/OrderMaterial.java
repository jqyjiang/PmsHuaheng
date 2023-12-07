package com.hh.pms.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 订单物料明细对象 order_material
 *
 * @author ruoyi
 * @date 2023-11-27
 */
public class OrderMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long orId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String orCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String orName;

    /** 品类编码 */
    @Excel(name = "品类编码")
    private String categoryCode;

    /** 物料品类 */
    @Excel(name = "物料品类")
    private String materialCategory;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpecification;

    /** 物料型号 */
    @Excel(name = "物料型号")
    private String materialModel;

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

    /** 历史最低价 */
    @Excel(name = "历史最低价")
    private BigDecimal lowerPrice;

    /** 最新价格 */
    @Excel(name = "最新价格")
    private BigDecimal newPrice;

    /** 不含税单价 */
    @Excel(name = "不含税单价")
    private Long noTaxPrice;

    /** 税率代码 */
    @Excel(name = "税率代码")
    private String taxCode;

    /** 税率 */
    @Excel(name = "税率")
    private BigDecimal tax;

    /** 含税单价 */
    @Excel(name = "含税单价")
    private Long taxPrice;

    /** 行含税金额 */
    @Excel(name = "行含税金额")
    private BigDecimal lineTaxAmount;

    /** 收货人 */
    @Excel(name = "收货人")
    private String consignee;

    /** 收货电话 */
    @Excel(name = "收货电话")
    private String receivingPhone;

    /** 收货地址 */
    @Excel(name = "收货地址")
    private String receivingAddress;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 所属订单号 */
    @Excel(name = "所属订单号")
    private String orderCode;

    @Excel(name = "订单明细执行状态")
    private Long omState;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String sbiName;

    public String getSbiName() {
        return sbiName;
    }

    public void setSbiName(String sbiName) {
        this.sbiName = sbiName;
    }

    public Long getOmState() {
        return omState;
    }

    public void setOmState(Long omState) {
        this.omState = omState;
    }

    public void setOrId(Long orId)
    {
        this.orId = orId;
    }

    public Long getOrId()
    {
        return orId;
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
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode()
    {
        return categoryCode;
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
    public void setLowerPrice(BigDecimal lowerPrice)
    {
        this.lowerPrice = lowerPrice;
    }

    public BigDecimal getLowerPrice()
    {
        return lowerPrice;
    }
    public void setNewPrice(BigDecimal newPrice)
    {
        this.newPrice = newPrice;
    }

    public BigDecimal getNewPrice()
    {
        return newPrice;
    }
    public void setNoTaxPrice(Long noTaxPrice)
    {
        this.noTaxPrice = noTaxPrice;
    }

    public Long getNoTaxPrice()
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
    public void setTaxPrice(Long taxPrice)
    {
        this.taxPrice = taxPrice;
    }

    public Long getTaxPrice()
    {
        return taxPrice;
    }
    public void setLineTaxAmount(BigDecimal lineTaxAmount)
    {
        this.lineTaxAmount = lineTaxAmount;
    }

    public BigDecimal getLineTaxAmount()
    {
        return lineTaxAmount;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setReceivingPhone(String receivingPhone)
    {
        this.receivingPhone = receivingPhone;
    }

    public String getReceivingPhone()
    {
        return receivingPhone;
    }
    public void setReceivingAddress(String receivingAddress)
    {
        this.receivingAddress = receivingAddress;
    }

    public String getReceivingAddress()
    {
        return receivingAddress;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public String toString() {
        return "OrderMaterial{" +
                "orId=" + orId +
                ", orCode='" + orCode + '\'' +
                ", orName='" + orName + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", materialCategory='" + materialCategory + '\'' +
                ", materialSpecification='" + materialSpecification + '\'' +
                ", materialModel='" + materialModel + '\'' +
                ", materialUnit='" + materialUnit + '\'' +
                ", requireNumber=" + requireNumber +
                ", requireTime=" + requireTime +
                ", lowerPrice=" + lowerPrice +
                ", newPrice=" + newPrice +
                ", noTaxPrice=" + noTaxPrice +
                ", taxCode='" + taxCode + '\'' +
                ", tax=" + tax +
                ", taxPrice=" + taxPrice +
                ", lineTaxAmount=" + lineTaxAmount +
                ", consignee='" + consignee + '\'' +
                ", receivingPhone='" + receivingPhone + '\'' +
                ", receivingAddress='" + receivingAddress + '\'' +
                ", remarks='" + remarks + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", omState=" + omState +
                ", sbiName='" + sbiName + '\'' +
                '}';
    }
}
