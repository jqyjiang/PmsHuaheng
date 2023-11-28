package com.hh.pms.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 采购需求池对象 material_information
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
public class MaterialInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer miId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private Long materialId;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private Long mustNumber;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date mustDate;

    /** 币种 */
    @Excel(name = "币种")
    private Long currencyId;

    /** 参考日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "参考日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date referenceDate;

    /** 预算单价(不含税) */
    @Excel(name = "预算单价(不含税)")
    private BigDecimal unitPrice;

    /** 税率值 */
    @Excel(name = "税率值")
    private String rateValue;

    /** 行预算金额 */
    @Excel(name = "行预算金额")
    private String budgetAmount;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 附件 */
    @Excel(name = "附件")
    private String annex;

    public void setMiId(Integer miId) 
    {
        this.miId = miId;
    }

    public Integer getMiId() 
    {
        return miId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setMustNumber(Long mustNumber) 
    {
        this.mustNumber = mustNumber;
    }

    public Long getMustNumber() 
    {
        return mustNumber;
    }
    public void setMustDate(Date mustDate) 
    {
        this.mustDate = mustDate;
    }

    public Date getMustDate() 
    {
        return mustDate;
    }
    public void setCurrencyId(Long currencyId) 
    {
        this.currencyId = currencyId;
    }

    public Long getCurrencyId() 
    {
        return currencyId;
    }
    public void setReferenceDate(Date referenceDate) 
    {
        this.referenceDate = referenceDate;
    }

    public Date getReferenceDate() 
    {
        return referenceDate;
    }
    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }
    public void setRateValue(String rateValue) 
    {
        this.rateValue = rateValue;
    }

    public String getRateValue() 
    {
        return rateValue;
    }
    public void setBudgetAmount(String budgetAmount) 
    {
        this.budgetAmount = budgetAmount;
    }

    public String getBudgetAmount() 
    {
        return budgetAmount;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setAnnex(String annex) 
    {
        this.annex = annex;
    }

    public String getAnnex() 
    {
        return annex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("miId", getMiId())
            .append("materialId", getMaterialId())
            .append("mustNumber", getMustNumber())
            .append("mustDate", getMustDate())
            .append("currencyId", getCurrencyId())
            .append("referenceDate", getReferenceDate())
            .append("unitPrice", getUnitPrice())
            .append("rateValue", getRateValue())
            .append("budgetAmount", getBudgetAmount())
            .append("remarks", getRemarks())
            .append("annex", getAnnex())
            .toString();
    }
}
