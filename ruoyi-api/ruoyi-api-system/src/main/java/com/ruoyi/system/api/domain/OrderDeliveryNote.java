package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 订单送货管理对象 order_delivery_note
 * 
 * @author yt
 * @date 2023-12-08
 */
public class OrderDeliveryNote extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 送货单Id */
    private Long orderDeliveryNoteId;

    /** 送货单号 */
    @Excel(name = "送货单号")
    private String orderDeliveryCode;

    /** 关联订单 */
    private String orderConnectionId;

    /** 送货单状态 */
    @Excel(name = "送货单状态")
    private Long deliveryNoteState;

    /** 收货地址 */
    private String deliveryAddress;

    /** 收货人 */
    private String consignee;

    /** 收货公司名 */
    private String companyName;

    /** 收货人联系方式 */
    private String consigneePhone;

    /** 发货人 */
    private String shipper;

    /** 发货人联系方式 */
    @Excel(name = "发货人联系方式")
    private String shipperPhone;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private Long supplier;

    /** 承运人 */
    private String carrier;

    /** 承运人联系方式 */
    @Excel(name = "承运人联系方式")
    private String carrierPhone;

    /** 送货方式 */
    private String deliveryMethod;

    /** 快递/物流单号 */
    @Excel(name = "快递/物流单号")
    private String trackingNumber;

    /** 预计发货日期 */
    private Date deliveryDate;

    /** 预计到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrivalDate;

    /** 备注 */
    private String note;

    /** 附件 */
    private Long annex;

    /** 剩余送货数量 */
    @Excel(name = "剩余送货数量")
    private Long remainingDeliveryQuantity;

    /** 本次送货数量 */
    @Excel(name = "本次送货数量")
    private Long currentDeliveryQuantity;
//
//    /** 产品订单明细 */
//    private Long deliveryId;

    /** 产品明细信息 */
    private List<Delivery> deliveryList;


    public void setOrderDeliveryNoteId(Long orderDeliveryNoteId) 
    {
        this.orderDeliveryNoteId = orderDeliveryNoteId;
    }

    public Long getOrderDeliveryNoteId() 
    {
        return orderDeliveryNoteId;
    }
    public void setOrderDeliveryCode(String orderDeliveryCode) 
    {
        this.orderDeliveryCode = orderDeliveryCode;
    }

    public String getOrderDeliveryCode() 
    {
        return orderDeliveryCode;
    }
    public void setOrderConnectionId(String orderConnectionId) 
    {
        this.orderConnectionId = orderConnectionId;
    }

    public String getOrderConnectionId() 
    {
        return orderConnectionId;
    }
    public void setDeliveryNoteState(Long deliveryNoteState) 
    {
        this.deliveryNoteState = deliveryNoteState;
    }

    public Long getDeliveryNoteState() 
    {
        return deliveryNoteState;
    }
    public void setDeliveryAddress(String deliveryAddress) 
    {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() 
    {
        return deliveryAddress;
    }
    public void setConsignee(String consignee) 
    {
        this.consignee = consignee;
    }

    public String getConsignee() 
    {
        return consignee;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setConsigneePhone(String consigneePhone) 
    {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneePhone() 
    {
        return consigneePhone;
    }
    public void setShipper(String shipper) 
    {
        this.shipper = shipper;
    }

    public String getShipper() 
    {
        return shipper;
    }
    public void setShipperPhone(String shipperPhone) 
    {
        this.shipperPhone = shipperPhone;
    }

    public String getShipperPhone() 
    {
        return shipperPhone;
    }
    public void setSupplier(Long supplier) 
    {
        this.supplier = supplier;
    }

    public Long getSupplier() 
    {
        return supplier;
    }
    public void setCarrier(String carrier) 
    {
        this.carrier = carrier;
    }

    public String getCarrier() 
    {
        return carrier;
    }
    public void setCarrierPhone(String carrierPhone) 
    {
        this.carrierPhone = carrierPhone;
    }

    public String getCarrierPhone() 
    {
        return carrierPhone;
    }
    public void setDeliveryMethod(String deliveryMethod) 
    {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryMethod() 
    {
        return deliveryMethod;
    }
    public void setTrackingNumber(String trackingNumber) 
    {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() 
    {
        return trackingNumber;
    }
    public void setDeliveryDate(Date deliveryDate) 
    {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() 
    {
        return deliveryDate;
    }
    public void setArrivalDate(Date arrivalDate) 
    {
        this.arrivalDate = arrivalDate;
    }

    public Date getArrivalDate() 
    {
        return arrivalDate;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }
    public void setAnnex(Long annex) 
    {
        this.annex = annex;
    }

    public Long getAnnex() 
    {
        return annex;
    }

    public Long getRemainingDeliveryQuantity() {
        return remainingDeliveryQuantity;
    }

    public void setRemainingDeliveryQuantity(Long remainingDeliveryQuantity) {
        this.remainingDeliveryQuantity = remainingDeliveryQuantity;
    }

    public Long getCurrentDeliveryQuantity() {
        return currentDeliveryQuantity;
    }

    public void setCurrentDeliveryQuantity(Long currentDeliveryQuantity) {
        this.currentDeliveryQuantity = currentDeliveryQuantity;
    }

    public List<Delivery> getDeliveryList()
    {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList)
    {
        this.deliveryList = deliveryList;
    }

    @Override
    public String toString() {
        return "OrderDeliveryNote{" +
                "orderDeliveryNoteId=" + orderDeliveryNoteId +
                ", orderDeliveryCode='" + orderDeliveryCode + '\'' +
                ", orderConnectionId='" + orderConnectionId + '\'' +
                ", deliveryNoteState=" + deliveryNoteState +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", consignee='" + consignee + '\'' +
                ", companyName='" + companyName + '\'' +
                ", consigneePhone='" + consigneePhone + '\'' +
                ", shipper='" + shipper + '\'' +
                ", shipperPhone='" + shipperPhone + '\'' +
                ", supplier=" + supplier +
                ", carrier='" + carrier + '\'' +
                ", carrierPhone='" + carrierPhone + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", arrivalDate=" + arrivalDate +
                ", note='" + note + '\'' +
                ", annex=" + annex +
                ", remainingDeliveryQuantity=" + remainingDeliveryQuantity +
                ", currentDeliveryQuantity=" + currentDeliveryQuantity +
                ", deliveryList=" + deliveryList +
                '}';
    }
}
