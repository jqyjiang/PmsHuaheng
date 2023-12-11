package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 产品明细对象 delivery
 * 
 * @author yt
 * @date 2023-12-08
 */
public class Delivery extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 送货单id */
    private Long deliveryId;

    /** 产品信息 */
    @Excel(name = "产品信息")
    private String productInfo;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpecifications;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private Long requiredQuantity;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 剩余送货数量 */
    @Excel(name = "剩余送货数量")
    private Long remainingDeliveryQuantity;

    /** 本次送货数量 */
    @Excel(name = "本次送货数量")
    private Long currentDeliveryQuantity;

    /** 注释 */
    @Excel(name = "注释")
    private String remarks;

    /** 来源单号 */
    @Excel(name = "来源单号")
    private String sourceOrderNumber;

    public void setDeliveryId(Long deliveryId) 
    {
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryId() 
    {
        return deliveryId;
    }
    public void setProductInfo(String productInfo) 
    {
        this.productInfo = productInfo;
    }

    public String getProductInfo() 
    {
        return productInfo;
    }
    public void setProductSpecifications(String productSpecifications) 
    {
        this.productSpecifications = productSpecifications;
    }

    public String getProductSpecifications() 
    {
        return productSpecifications;
    }
    public void setRequiredQuantity(Long requiredQuantity) 
    {
        this.requiredQuantity = requiredQuantity;
    }

    public Long getRequiredQuantity() 
    {
        return requiredQuantity;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setRemainingDeliveryQuantity(Long remainingDeliveryQuantity) 
    {
        this.remainingDeliveryQuantity = remainingDeliveryQuantity;
    }

    public Long getRemainingDeliveryQuantity() 
    {
        return remainingDeliveryQuantity;
    }
    public void setCurrentDeliveryQuantity(Long currentDeliveryQuantity) 
    {
        this.currentDeliveryQuantity = currentDeliveryQuantity;
    }

    public Long getCurrentDeliveryQuantity() 
    {
        return currentDeliveryQuantity;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setSourceOrderNumber(String sourceOrderNumber) 
    {
        this.sourceOrderNumber = sourceOrderNumber;
    }

    public String getSourceOrderNumber() 
    {
        return sourceOrderNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deliveryId", getDeliveryId())
            .append("productInfo", getProductInfo())
            .append("productSpecifications", getProductSpecifications())
            .append("requiredQuantity", getRequiredQuantity())
            .append("unit", getUnit())
            .append("remainingDeliveryQuantity", getRemainingDeliveryQuantity())
            .append("currentDeliveryQuantity", getCurrentDeliveryQuantity())
            .append("remarks", getRemarks())
            .append("sourceOrderNumber", getSourceOrderNumber())
            .toString();
    }
}
