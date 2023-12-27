package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 质检单对象 quality_order
 *
 * @author ruoyi
 * @date 2023-12-25
 */
public class QualityOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 质检单Id */
    private Long qualityId;

    /** 采购订单编号 */
    @Excel(name = "采购订单编号")
    private String orderCode;

    /** 质检编号 */
    @Excel(name = "质检编号")
    private String qualityCode;

    /** 收货编号 */
    @Excel(name = "收货编号")
    private String receiptNoteNo;

    /** 发货单号 */
    @Excel(name = "发货单号")
    private String deliveryNoteNo;

    /** 创建人 */
    @Excel(name = "创建人")
    private String founder;

    /** 到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receivedDate;

    /** 检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date qualityDate;

    /** 检验单类型 */
    @Excel(name = "检验单类型")
    private Long inspectOrderType;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplier;

    /** 物料编号 */
    @Excel(name = "物料编号")
    private String orCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String orName;

    /** 物料单位 */
    @Excel(name = "物料单位")
    private String materialUnit;

    /** 物料品类 */
    @Excel(name = "物料品类")
    private String materialCategory;

    /** 收货数量 */
    @Excel(name = "收货数量")
    private BigDecimal receivedQuantity;

    /** 送检数量 */
    @Excel(name = "送检数量")
    private BigDecimal inspectionQuantity;

    /** 质检类型 */
    @Excel(name = "质检类型")
    private Long qualityType;

    /** 检验类型 */
    @Excel(name = "检验类型")
    private Long inspectType;

    /** 检验开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检验开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectStartDate;

    /** 检验结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检验结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectEndDate;

    /** 质检损耗数量 */
    @Excel(name = "质检损耗数量")
    private BigDecimal qualifiedNumber;

    /** 检验合格数量 */
    @Excel(name = "检验合格数量")
    private BigDecimal inspectQualifiedNumber;

    /** 检验不合格品数量 */
    @Excel(name = "检验不合格品数量")
    private BigDecimal inspectNonConformingNumber;

    /** 不合格品比例 */
    @Excel(name = "不合格品比例")
    private String nonConforming;

    /** 检测结果 */
    @Excel(name = "检测结果")
    private Long decectionResultName;

    /** 处理措施 */
    @Excel(name = "处理措施")
    private Long handlingMeasuresName;

    /** 说明 */
    @Excel(name = "说明")
    private String remarks;

    /** 检验员 */
    @Excel(name = "检验员")
    private String inspecter;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 库存组织 */
    @Excel(name = "库存组织")
    private String inventory;

    /** 公司 */
    @Excel(name = "公司")
    private String company;

    public void setQualityId(Long qualityId)
    {
        this.qualityId = qualityId;
    }

    public Long getQualityId()
    {
        return qualityId;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode()
    {
        return orderCode;
    }
    public void setQualityCode(String qualityCode)
    {
        this.qualityCode = qualityCode;
    }

    public String getQualityCode()
    {
        return qualityCode;
    }
    public void setReceiptNoteNo(String receiptNoteNo)
    {
        this.receiptNoteNo = receiptNoteNo;
    }

    public String getReceiptNoteNo()
    {
        return receiptNoteNo;
    }
    public void setDeliveryNoteNo(String deliveryNoteNo)
    {
        this.deliveryNoteNo = deliveryNoteNo;
    }

    public String getDeliveryNoteNo()
    {
        return deliveryNoteNo;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setReceivedDate(Date receivedDate)
    {
        this.receivedDate = receivedDate;
    }

    public Date getReceivedDate()
    {
        return receivedDate;
    }
    public void setQualityDate(Date qualityDate)
    {
        this.qualityDate = qualityDate;
    }

    public Date getQualityDate()
    {
        return qualityDate;
    }
    public void setInspectOrderType(Long inspectOrderType)
    {
        this.inspectOrderType = inspectOrderType;
    }

    public Long getInspectOrderType()
    {
        return inspectOrderType;
    }
    public void setSupplier(String supplier)
    {
        this.supplier = supplier;
    }

    public String getSupplier()
    {
        return supplier;
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
    public void setMaterialUnit(String materialUnit)
    {
        this.materialUnit = materialUnit;
    }

    public String getMaterialUnit()
    {
        return materialUnit;
    }
    public void setMaterialCategory(String materialCategory)
    {
        this.materialCategory = materialCategory;
    }

    public String getMaterialCategory()
    {
        return materialCategory;
    }
    public void setReceivedQuantity(BigDecimal receivedQuantity)
    {
        this.receivedQuantity = receivedQuantity;
    }

    public BigDecimal getReceivedQuantity()
    {
        return receivedQuantity;
    }
    public void setInspectionQuantity(BigDecimal inspectionQuantity)
    {
        this.inspectionQuantity = inspectionQuantity;
    }

    public BigDecimal getInspectionQuantity()
    {
        return inspectionQuantity;
    }
    public void setQualityType(Long qualityType)
    {
        this.qualityType = qualityType;
    }

    public Long getQualityType()
    {
        return qualityType;
    }
    public void setInspectType(Long inspectType)
    {
        this.inspectType = inspectType;
    }

    public Long getInspectType()
    {
        return inspectType;
    }
    public void setInspectStartDate(Date inspectStartDate)
    {
        this.inspectStartDate = inspectStartDate;
    }

    public Date getInspectStartDate()
    {
        return inspectStartDate;
    }
    public void setInspectEndDate(Date inspectEndDate)
    {
        this.inspectEndDate = inspectEndDate;
    }

    public Date getInspectEndDate()
    {
        return inspectEndDate;
    }
    public void setQualifiedNumber(BigDecimal qualifiedNumber)
    {
        this.qualifiedNumber = qualifiedNumber;
    }

    public BigDecimal getQualifiedNumber()
    {
        return qualifiedNumber;
    }
    public void setInspectQualifiedNumber(BigDecimal inspectQualifiedNumber)
    {
        this.inspectQualifiedNumber = inspectQualifiedNumber;
    }

    public BigDecimal getInspectQualifiedNumber()
    {
        return inspectQualifiedNumber;
    }
    public void setInspectNonConformingNumber(BigDecimal inspectNonConformingNumber)
    {
        this.inspectNonConformingNumber = inspectNonConformingNumber;
    }

    public BigDecimal getInspectNonConformingNumber()
    {
        return inspectNonConformingNumber;
    }
    public void setNonConforming(String nonConforming)
    {
        this.nonConforming = nonConforming;
    }

    public String getNonConforming()
    {
        return nonConforming;
    }
    public void setDecectionResultName(Long decectionResultName)
    {
        this.decectionResultName = decectionResultName;
    }

    public Long getDecectionResultName()
    {
        return decectionResultName;
    }
    public void setHandlingMeasuresName(Long handlingMeasuresName)
    {
        this.handlingMeasuresName = handlingMeasuresName;
    }

    public Long getHandlingMeasuresName()
    {
        return handlingMeasuresName;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setInspecter(String inspecter)
    {
        this.inspecter = inspecter;
    }

    public String getInspecter()
    {
        return inspecter;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setInventory(String inventory)
    {
        this.inventory = inventory;
    }

    public String getInventory()
    {
        return inventory;
    }
    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCompany()
    {
        return company;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("qualityId", getQualityId())
                .append("orderCode", getOrderCode())
                .append("qualityCode", getQualityCode())
                .append("receiptNoteNo", getReceiptNoteNo())
                .append("deliveryNoteNo", getDeliveryNoteNo())
                .append("founder", getFounder())
                .append("createTime", getCreateTime())
                .append("receivedDate", getReceivedDate())
                .append("qualityDate", getQualityDate())
                .append("inspectOrderType", getInspectOrderType())
                .append("supplier", getSupplier())
                .append("orCode", getOrCode())
                .append("orName", getOrName())
                .append("materialUnit", getMaterialUnit())
                .append("materialCategory", getMaterialCategory())
                .append("receivedQuantity", getReceivedQuantity())
                .append("inspectionQuantity", getInspectionQuantity())
                .append("qualityType", getQualityType())
                .append("inspectType", getInspectType())
                .append("inspectStartDate", getInspectStartDate())
                .append("inspectEndDate", getInspectEndDate())
                .append("qualifiedNumber", getQualifiedNumber())
                .append("inspectQualifiedNumber", getInspectQualifiedNumber())
                .append("inspectNonConformingNumber", getInspectNonConformingNumber())
                .append("nonConforming", getNonConforming())
                .append("decectionResultName", getDecectionResultName())
                .append("handlingMeasuresName", getHandlingMeasuresName())
                .append("remarks", getRemarks())
                .append("inspecter", getInspecter())
                .append("status", getStatus())
                .append("inventory", getInventory())
                .append("company", getCompany())
                .toString();
    }
}