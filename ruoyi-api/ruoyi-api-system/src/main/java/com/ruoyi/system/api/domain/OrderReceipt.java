package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 订单收货管理对象 order_receipt
 *
 * @author ruoyi
 * @date 2023-12-20
 */
public class OrderReceipt extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 收货单Id
     */
    private Long receiptId;

    /**
     * 收货单号
     */
    private String receiptNoteNo;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货总数量
     */
    private BigDecimal receiveQuantity;

    /**
     * 收货含税金额
     */
    private BigDecimal recepitAmountTax;

    /**
     * 收货备注
     */
    private String receiptRemarks;

    /**
     * 附件
     */
    private Long annex;

    /**
     * 质检人
     */
    private String inspector;

    /**
     * 供应商名称
     */
    private String supplier;

    /**
     * 联系人
     */
    private String receiptConcat;

    /**
     * 联系电话
     */
    private String receiptPhone;

    /**
     * 收货状态
     */
    private Long receiptState;
    private String consignee;
    private List<ReceiptDetails> receiptDetails;

    public List<ReceiptDetails> getReceiptDetails() {
        return receiptDetails;
    }

    public void setReceiptDetails(List<ReceiptDetails> receiptDetails) {
        this.receiptDetails = receiptDetails;
    }

    /**
     * 收货明细信息信息
     */
    private List<ReceiptDetails> receiptDetailsList;

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public String getReceiptNoteNo() {
        return receiptNoteNo;
    }

    public void setReceiptNoteNo(String receiptNoteNo) {
        this.receiptNoteNo = receiptNoteNo;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getReceiveQuantity() {
        return receiveQuantity;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setReceiveQuantity(BigDecimal receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public void setRecepitAmountTax(BigDecimal recepitAmountTax) {
        this.recepitAmountTax = recepitAmountTax;
    }

    public BigDecimal getRecepitAmountTax() {
        return recepitAmountTax;
    }

    public void setReceiptRemarks(String receiptRemarks) {
        this.receiptRemarks = receiptRemarks;
    }

    public String getReceiptRemarks() {
        return receiptRemarks;
    }

    public void setAnnex(Long annex) {
        this.annex = annex;
    }

    public Long getAnnex() {
        return annex;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspector() {
        return inspector;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setReceiptConcat(String receiptConcat) {
        this.receiptConcat = receiptConcat;
    }

    public String getReceiptConcat() {
        return receiptConcat;
    }

    public void setReceiptPhone(String receiptPhone) {
        this.receiptPhone = receiptPhone;
    }

    public String getReceiptPhone() {
        return receiptPhone;
    }

    public void setReceiptState(Long receiptState) {
        this.receiptState = receiptState;
    }

    public Long getReceiptState() {
        return receiptState;
    }

    public List<ReceiptDetails> getReceiptDetailsList() {
        return receiptDetailsList;
    }

    public void setReceiptDetailsList(List<ReceiptDetails> receiptDetailsList) {
        this.receiptDetailsList = receiptDetailsList;
    }

    @Override
    public String toString() {
        return "OrderReceipt{" +
                "receiptId=" + receiptId +
                ", receiptNoteNo='" + receiptNoteNo + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiveQuantity=" + receiveQuantity +
                ", recepitAmountTax=" + recepitAmountTax +
                ", receiptRemarks='" + receiptRemarks + '\'' +
                ", annex=" + annex +
                ", inspector='" + inspector + '\'' +
                ", supplier='" + supplier + '\'' +
                ", receiptConcat='" + receiptConcat + '\'' +
                ", receiptPhone='" + receiptPhone + '\'' +
                ", receiptState=" + receiptState +
                ", receiptDetails=" + receiptDetails +
                ", receiptDetailsList=" + receiptDetailsList +
                '}';
    }
}
