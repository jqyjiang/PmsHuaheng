package com.hh.pms.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 采购订单管理对象 order_manager
 * 
 * @author ruoyi
 * @date 2023-11-21
 */
public class OrderManager extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购订单id */
    private Long orderId;

    /** 采购订单编号 */
    @Excel(name = "采购订单编号")
    private String orderCode;

    /** 公司 */
    @Excel(name = "公司")
    private Long company;

    /** 订单类型 */
    @Excel(name = "订单类型")
    private Long orderType;

    /** 订单来源 */
    @Excel(name = "订单来源")
    private String orderSource;

    /** 采购组织 */
    @Excel(name = "采购组织")
    private Long purOrganization;

    /** 附件 */
    @Excel(name = "附件")
    private Long annex;

    /** 是否自提 */
    @Excel(name = "是否自提")
    private Long isSelfPickup;

    /** 自提车牌号 */
    @Excel(name = "自提车牌号")
    private String licensePlateNumber;

    /** 自提司机联系方式 */
    @Excel(name = "自提司机联系方式")
    private String concatInfomation;

    /** 关联合同编号 */
    @Excel(name = "关联合同编号")
    private String contractCode;

    /** 关联合同名称 */
    @Excel(name = "关联合同名称")
    private String contractName;

    /** 订单说明 */
    @Excel(name = "订单说明")
    private String orderDescription;

    /** 需求总数量 */
    @Excel(name = "需求总数量")
    private BigDecimal totalDemand;

    /** 采购员 */
    @Excel(name = "采购员")
    private Long purchaser;

    /** 状态 */
    @Excel(name = "状态")
    private String orderState;

    /** 币种 */
    @Excel(name = "币种")
    private Long currencyId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private Long supplier;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contacts;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 供应商发票方式 */
    @Excel(name = "供应商发票方式")
    private Long invoiceMethod;

    @Excel(name = "采购订单创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setCompany(Long company) 
    {
        this.company = company;
    }

    public Long getCompany() 
    {
        return company;
    }
    public void setOrderType(Long orderType) 
    {
        this.orderType = orderType;
    }

    public Long getOrderType() 
    {
        return orderType;
    }
    public void setOrderSource(String orderSource) 
    {
        this.orderSource = orderSource;
    }

    public String getOrderSource() 
    {
        return orderSource;
    }
    public void setPurOrganization(Long purOrganization) 
    {
        this.purOrganization = purOrganization;
    }

    public Long getPurOrganization() 
    {
        return purOrganization;
    }
    public void setAnnex(Long annex) 
    {
        this.annex = annex;
    }

    public Long getAnnex() 
    {
        return annex;
    }
    public void setIsSelfPickup(Long isSelfPickup) 
    {
        this.isSelfPickup = isSelfPickup;
    }

    public Long getIsSelfPickup() 
    {
        return isSelfPickup;
    }
    public void setLicensePlateNumber(String licensePlateNumber) 
    {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getLicensePlateNumber() 
    {
        return licensePlateNumber;
    }
    public void setConcatInfomation(String concatInfomation) 
    {
        this.concatInfomation = concatInfomation;
    }

    public String getConcatInfomation() 
    {
        return concatInfomation;
    }
    public void setContractCode(String contractCode) 
    {
        this.contractCode = contractCode;
    }

    public String getContractCode() 
    {
        return contractCode;
    }
    public void setContractName(String contractName) 
    {
        this.contractName = contractName;
    }

    public String getContractName() 
    {
        return contractName;
    }
    public void setOrderDescription(String orderDescription) 
    {
        this.orderDescription = orderDescription;
    }

    public String getOrderDescription() 
    {
        return orderDescription;
    }
    public void setTotalDemand(BigDecimal totalDemand) 
    {
        this.totalDemand = totalDemand;
    }

    public BigDecimal getTotalDemand() 
    {
        return totalDemand;
    }
    public void setPurchaser(Long purchaser) 
    {
        this.purchaser = purchaser;
    }

    public Long getPurchaser() 
    {
        return purchaser;
    }
    public void setOrderState(String orderState) 
    {
        this.orderState = orderState;
    }

    public String getOrderState() 
    {
        return orderState;
    }
    public void setCurrencyId(Long currencyId) 
    {
        this.currencyId = currencyId;
    }

    public Long getCurrencyId() 
    {
        return currencyId;
    }
    public void setSupplier(Long supplier) 
    {
        this.supplier = supplier;
    }

    public Long getSupplier() 
    {
        return supplier;
    }
    public void setContacts(String contacts) 
    {
        this.contacts = contacts;
    }

    public String getContacts() 
    {
        return contacts;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setInvoiceMethod(Long invoiceMethod) 
    {
        this.invoiceMethod = invoiceMethod;
    }

    public Long getInvoiceMethod() 
    {
        return invoiceMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderCode", getOrderCode())
            .append("company", getCompany())
            .append("orderType", getOrderType())
            .append("orderSource", getOrderSource())
            .append("purOrganization", getPurOrganization())
            .append("annex", getAnnex())
            .append("isSelfPickup", getIsSelfPickup())
            .append("licensePlateNumber", getLicensePlateNumber())
            .append("concatInfomation", getConcatInfomation())
            .append("contractCode", getContractCode())
            .append("contractName", getContractName())
            .append("orderDescription", getOrderDescription())
            .append("totalDemand", getTotalDemand())
            .append("purchaser", getPurchaser())
            .append("orderState", getOrderState())
            .append("currencyId", getCurrencyId())
            .append("supplier", getSupplier())
            .append("contacts", getContacts())
            .append("phone", getPhone())
            .append("invoiceMethod", getInvoiceMethod())
            .toString();
    }
}
