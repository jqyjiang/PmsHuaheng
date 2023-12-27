package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * mingxi对象 order_execution_details
 *
 * @author ruoyi
 * @date 2023-12-14
 */
public class OrderExecutionDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderCode;

    /**
     * 订单处理状态
     */
    @Excel(name = "订单处理状态")
    private Long orderHandle;

    /**
     * 质检状态
     */
    @Excel(name = "质检状态")
    private Long orderQuanlity;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private String orderStatus;

    /**
     * 总需求量
     */
    @Excel(name = "总需求量")
    private BigDecimal totalDemand;

    /**
     * 交货数量
     */
    @Excel(name = "交货数量")
    private BigDecimal deliveryQuantity;

    /**
     * 已收货数量
     */
    @Excel(name = "已收货数量")
    private BigDecimal receivedQuantity;

    /**
     * 库存数量
     */
    @Excel(name = "库存数量")
    private BigDecimal inStockQuantity;

    /**
     * 税额总计
     */
    @Excel(name = "税额总计")
    private BigDecimal taxTotal;

    /**
     * 已收款金额
     */
    @Excel(name = "已收款金额")
    private BigDecimal receivedAmount;

    /**
     * 待交货数量
     */
    @Excel(name = "待交货数量")
    private BigDecimal awaitingDeliveryQuantity;

    /**
     * 待交货金额
     */
    @Excel(name = "待交货金额")
    private BigDecimal awaitingDeliveryAmount;

    /**
     * 订单行号
     */
    private String orderLineNo;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    private String materialName;

    /**
     * 物料单位
     */
    @Excel(name = "物料单位")
    private String materialUnit;

    /**
     * 需求数量
     */
    @Excel(name = "需求数量")
    private BigDecimal requiredQuantity;

    /**
     * 需求日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requiredDate;

    /**
     * 交货单号
     */
    private String deliveryNoteNo;

    /**
     * 交货单行号
     */
    private String deliveryNoteLineNo;

    /**
     * 在途数量
     */
    private BigDecimal deliveredQuantity;

    /**
     * 在途金额
     */
    @Excel(name = "已发货金额")
    private BigDecimal deliveredAmount;

    /**
     * 供应商
     */
    @Excel(name = "供应商")
    private String supplier;

    /**
     * 公司
     */
    private String company;

    /**
     * 主要联系人姓名
     */
    @Excel(name = "主要联系人姓名")
    private String sdPcn;

    /**
     * 主要联系人手机号
     */
    @Excel(name = "主要联系人手机号")
    private String sdPcp;
    /**
     * 发货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;

    /**
     * 收货单号
     */
    private String receiptNoteNo;

    /**
     * 收货单行号
     */
    private String receiptNoteLineNo;

    /**
     * 拒收数量
     */
    private BigDecimal rejectedQuantity;

    /**
     * 已收货数量（第二列）
     */
    @Excel(name = "已收货数量", readConverterExp = "第=二列")
    private BigDecimal receiveQuantity;

    /**
     * 收货总金额
     */
    private BigDecimal recepitAmountTax;
    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receivedDate;

    /**
     * 入库单号
     */
    private String stockInNoteNo;

    /**
     * 入库单行号
     */
    private String stockInNoteLineNo;

    /**
     * 退回数量
     */
    private BigDecimal returnedQuantity;

    /**
     * 入库数量
     */
    @Excel(name = "入库数量")
    private BigDecimal stockInQuantity;

    /**
     * 入库金额
     */
    @Excel(name = "入库金额")
    private BigDecimal stockInAmount;


    /**
     * 入库人员
     */
    private String stockInPerson;

    /**
     * 入库日期
     */
    private Date stockInDate;

    /**业务对账状态*/
    private Long reconciliationStatus;

    public Long getReconciliationStatus() {
        return reconciliationStatus;
    }

    public void setReconciliationStatus(Long reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

    /**
     * 物料基本信息
     */
    private List<OrderMaterial> orderMaterials;

    /**
     * 订单管理
     */
    private OrderManager orderManager;

    public BigDecimal getRecepitAmountTax() {
        return recepitAmountTax;
    }

    public void setRecepitAmountTax(BigDecimal recepitAmountTax) {
        this.recepitAmountTax = recepitAmountTax;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public List<OrderMaterial> getOrderMaterials() {
        return orderMaterials;
    }

    public void setOrderMaterials(List<OrderMaterial> orderMaterials) {
        this.orderMaterials = orderMaterials;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Long getOrderHandle() {
        return orderHandle;
    }

    public void setOrderHandle(Long orderHandle) {
        this.orderHandle = orderHandle;
    }

    public Long getOrderQuanlity() {
        return orderQuanlity;
    }

    public void setOrderQuanlity(Long orderQuanlity) {
        this.orderQuanlity = orderQuanlity;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setTotalDemand(BigDecimal totalDemand) {
        this.totalDemand = totalDemand;
    }

    public BigDecimal getTotalDemand() {
        return totalDemand;
    }

    public void setDeliveryQuantity(BigDecimal deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public BigDecimal getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public BigDecimal getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setInStockQuantity(BigDecimal inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public BigDecimal getInStockQuantity() {
        return inStockQuantity;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setAwaitingDeliveryQuantity(BigDecimal awaitingDeliveryQuantity) {
        this.awaitingDeliveryQuantity = awaitingDeliveryQuantity;
    }

    public String getSdPcn() {
        return sdPcn;
    }

    public void setSdPcn(String sdPcn) {
        this.sdPcn = sdPcn;
    }

    public String getSdPcp() {
        return sdPcp;
    }

    public void setSdPcp(String sdPcp) {
        this.sdPcp = sdPcp;
    }

    public BigDecimal getAwaitingDeliveryQuantity() {
        return awaitingDeliveryQuantity;
    }

    public BigDecimal getDeliveredAmount() {
        return deliveredAmount;
    }

    public void setDeliveredAmount(BigDecimal deliveredAmount) {
        this.deliveredAmount = deliveredAmount;
    }

    public void setAwaitingDeliveryAmount(BigDecimal awaitingDeliveryAmount) {
        this.awaitingDeliveryAmount = awaitingDeliveryAmount;
    }

    public BigDecimal getAwaitingDeliveryAmount() {
        return awaitingDeliveryAmount;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setDeliveryNoteNo(String deliveryNoteNo) {
        this.deliveryNoteNo = deliveryNoteNo;
    }

    public String getDeliveryNoteNo() {
        return deliveryNoteNo;
    }

    public void setDeliveryNoteLineNo(String deliveryNoteLineNo) {
        this.deliveryNoteLineNo = deliveryNoteLineNo;
    }

    public String getDeliveryNoteLineNo() {
        return deliveryNoteLineNo;
    }

    public void setDeliveredQuantity(BigDecimal deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public BigDecimal getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setReceiptNoteNo(String receiptNoteNo) {
        this.receiptNoteNo = receiptNoteNo;
    }

    public String getReceiptNoteNo() {
        return receiptNoteNo;
    }

    public void setReceiptNoteLineNo(String receiptNoteLineNo) {
        this.receiptNoteLineNo = receiptNoteLineNo;
    }

    public String getReceiptNoteLineNo() {
        return receiptNoteLineNo;
    }

    public void setRejectedQuantity(BigDecimal rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public BigDecimal getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setReceiveQuantity(BigDecimal receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public BigDecimal getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setStockInNoteNo(String stockInNoteNo) {
        this.stockInNoteNo = stockInNoteNo;
    }

    public String getStockInNoteNo() {
        return stockInNoteNo;
    }

    public void setStockInNoteLineNo(String stockInNoteLineNo) {
        this.stockInNoteLineNo = stockInNoteLineNo;
    }

    public String getStockInNoteLineNo() {
        return stockInNoteLineNo;
    }

    public void setReturnedQuantity(BigDecimal returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public BigDecimal getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setStockInQuantity(BigDecimal stockInQuantity) {
        this.stockInQuantity = stockInQuantity;
    }

    public BigDecimal getStockInAmount() {
        return stockInAmount;
    }

    public void setStockInAmount(BigDecimal stockInAmount) {
        this.stockInAmount = stockInAmount;
    }

    public BigDecimal getStockInQuantity() {
        return stockInQuantity;
    }

    public void setStockInPerson(String stockInPerson) {
        this.stockInPerson = stockInPerson;
    }

    public String getStockInPerson() {
        return stockInPerson;
    }

    public void setStockInDate(Date stockInDate) {
        this.stockInDate = stockInDate;
    }

    public Date getStockInDate() {
        return stockInDate;
    }

    @Override
    public String toString() {
        return "OrderExecutionDetails{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalDemand=" + totalDemand +
                ", deliveryQuantity=" + deliveryQuantity +
                ", receivedQuantity=" + receivedQuantity +
                ", inStockQuantity=" + inStockQuantity +
                ", taxTotal=" + taxTotal +
                ", receivedAmount=" + receivedAmount +
                ", awaitingDeliveryQuantity=" + awaitingDeliveryQuantity +
                ", awaitingDeliveryAmount=" + awaitingDeliveryAmount +
                ", orderLineNo='" + orderLineNo + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialUnit='" + materialUnit + '\'' +
                ", requiredQuantity=" + requiredQuantity +
                ", requiredDate=" + requiredDate +
                ", deliveryNoteNo='" + deliveryNoteNo + '\'' +
                ", deliveryNoteLineNo='" + deliveryNoteLineNo + '\'' +
                ", deliveredQuantity=" + deliveredQuantity +
                ", deliveredAmount=" + deliveredAmount +
                ", supplier='" + supplier + '\'' +
                ", company='" + company + '\'' +
                ", sdPcn='" + sdPcn + '\'' +
                ", sdPcp='" + sdPcp + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", receiptNoteNo='" + receiptNoteNo + '\'' +
                ", receiptNoteLineNo='" + receiptNoteLineNo + '\'' +
                ", rejectedQuantity=" + rejectedQuantity +
                ", receiveQuantity=" + receiveQuantity +
                ", recepitAmountTax=" + recepitAmountTax +
                ", receiver='" + receiver + '\'' +
                ", receivedDate=" + receivedDate +
                ", stockInNoteNo='" + stockInNoteNo + '\'' +
                ", stockInNoteLineNo='" + stockInNoteLineNo + '\'' +
                ", returnedQuantity=" + returnedQuantity +
                ", stockInQuantity=" + stockInQuantity +
                ", stockInAmount=" + stockInAmount +
                ", stockInPerson='" + stockInPerson + '\'' +
                ", stockInDate=" + stockInDate +
                ", reconciliationStatus=" + reconciliationStatus +
                ", orderMaterials=" + orderMaterials +
                ", orderManager=" + orderManager +
                '}';
    }


}
