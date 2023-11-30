package com.hh.pms.domain;

import java.math.BigDecimal;
import java.util.List;
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
 * @date 2023-11-30
 */
public class MaterialInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer miId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料品类 */
    @Excel(name = "物料品类")
    private String materialCategory;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpecification;

    /** 物料型号 */
    @Excel(name = "物料型号")
    private String materialModel;

    /** 物料品牌 */
    @Excel(name = "物料品牌")
    private String materialBrand;

    /** 物料单位 */
    @Excel(name = "物料单位")
    private String materialUnit;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private Long mustNumber;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date mustDate;

    /** 币种 */
    @Excel(name = "币种")
    private Long currencyName;

    /** 参考价格 */
    @Excel(name = "参考价格")
    private BigDecimal referencePrice;

    /** 预算单价(不含税) */
    @Excel(name = "预算单价(不含税)")
    private BigDecimal unitPrice;

    /** 税率 */
    @Excel(name = "税率")
    private String tax;

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

    /** 采购员 */
    @Excel(name = "采购员")
    private String purchaser;

    /** 物料维护信息 */
    private List<Material> materialList;

    public void setMiId(Integer miId)
    {
        this.miId = miId;
    }

    public Integer getMiId()
    {
        return miId;
    }
    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode()
    {
        return materialCode;
    }
    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getMaterialName()
    {
        return materialName;
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
    public void setMaterialBrand(String materialBrand)
    {
        this.materialBrand = materialBrand;
    }

    public String getMaterialBrand()
    {
        return materialBrand;
    }
    public void setMaterialUnit(String materialUnit)
    {
        this.materialUnit = materialUnit;
    }

    public String getMaterialUnit()
    {
        return materialUnit;
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
    public void setCurrencyName(Long currencyName)
    {
        this.currencyName = currencyName;
    }

    public Long getCurrencyName()
    {
        return currencyName;
    }
    public void setReferencePrice(BigDecimal referencePrice)
    {
        this.referencePrice = referencePrice;
    }

    public BigDecimal getReferencePrice()
    {
        return referencePrice;
    }
    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }
    public void setTax(String tax)
    {
        this.tax = tax;
    }

    public String getTax()
    {
        return tax;
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
    public void setPurchaser(String purchaser)
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser()
    {
        return purchaser;
    }

    public List<Material> getMaterialList()
    {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList)
    {
        this.materialList = materialList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("miId", getMiId())
                .append("materialCode", getMaterialCode())
                .append("materialName", getMaterialName())
                .append("materialCategory", getMaterialCategory())
                .append("materialSpecification", getMaterialSpecification())
                .append("materialModel", getMaterialModel())
                .append("materialBrand", getMaterialBrand())
                .append("materialUnit", getMaterialUnit())
                .append("mustNumber", getMustNumber())
                .append("mustDate", getMustDate())
                .append("currencyName", getCurrencyName())
                .append("referencePrice", getReferencePrice())
                .append("unitPrice", getUnitPrice())
                .append("tax", getTax())
                .append("rateValue", getRateValue())
                .append("budgetAmount", getBudgetAmount())
                .append("remarks", getRemarks())
                .append("annex", getAnnex())
                .append("purchaser", getPurchaser())
                .append("materialList", getMaterialList())
                .toString();
    }
}
