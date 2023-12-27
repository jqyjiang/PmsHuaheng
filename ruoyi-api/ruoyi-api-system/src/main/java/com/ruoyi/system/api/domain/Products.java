package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 *  产品信息对象 products
 *
 * @author ruoyi
 * @date 2023-12-11
 */
public class Products extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品id */
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String specifications;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 税率 */
    @Excel(name = "税率")
    private BigDecimal taxRate;
    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal number;
    /** 小计 */
    @Excel(name = "小计")
    private BigDecimal subtotal;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderCode;


    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductCode()
    {
        return productCode;
    }
    public void setSpecifications(String specifications)
    {
        this.specifications = specifications;
    }

    public String getSpecifications()
    {
        return specifications;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setTaxRate(BigDecimal taxRate)
    {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate()
    {
        return taxRate;
    }

    public BigDecimal getNumber()
    {
        return number;
    }
    public BigDecimal getSubtotal()
    {
        return subtotal;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("productId", getProductId())
                .append("productName", getProductName())
                .append("productCode", getProductCode())
                .append("specifications", getSpecifications())
                .append("unit", getUnit())
                .append("price", getPrice())
                .append("taxRate", getTaxRate())
                .append("number", getNumber())
                .append("subtotal", getSubtotal())
                .append("orderCode", getOrderCode())
                .toString();
    }
}
