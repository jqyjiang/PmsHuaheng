package com.hh.pms.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 采购需求池对象 material
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
public class Material extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料序号 */
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 基本计算单位 */
    @Excel(name = "基本计算单位")
    private String calculationUnit;

    /** 采购员 */
    @Excel(name = "采购员")
    private String purchaser;

    /** 最后更新人 */
    @Excel(name = "最后更新人")
    private String lUpdated;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lUpdateTime;

    /** 来源系统 */
    @Excel(name = "来源系统")
    private String sourceSystem;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long enable;

    /** 主品类 */
    @Excel(name = "主品类")
    private String mCategory;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 默认税种/税率 */
    @Excel(name = "默认税种/税率")
    private BigDecimal categoriesTaxes;

    /** 物料图片 */
    @Excel(name = "物料图片")
    private String image;

    /** 毛重 */
    @Excel(name = "毛重")
    private BigDecimal gWeight;

    /** 净重 */
    @Excel(name = "净重")
    private BigDecimal nWeight;

    /** 重量单位 */
    @Excel(name = "重量单位")
    private String weight;

    /** 体积 */
    @Excel(name = "体积")
    private BigDecimal volume;

    /** 体积单位 */
    @Excel(name = "体积单位")
    private String vUnit;

    /** 物料ABC属性 */
    @Excel(name = "物料ABC属性")
    private String abcAttribute;

    /** 是否免检 */
    @Excel(name = "是否免检")
    private Long avoidInspect;

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
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
    public void setCalculationUnit(String calculationUnit) 
    {
        this.calculationUnit = calculationUnit;
    }

    public String getCalculationUnit() 
    {
        return calculationUnit;
    }
    public void setPurchaser(String purchaser) 
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
    }
    public void setlUpdated(String lUpdated) 
    {
        this.lUpdated = lUpdated;
    }

    public String getlUpdated() 
    {
        return lUpdated;
    }
    public void setlUpdateTime(Date lUpdateTime) 
    {
        this.lUpdateTime = lUpdateTime;
    }

    public Date getlUpdateTime() 
    {
        return lUpdateTime;
    }
    public void setSourceSystem(String sourceSystem) 
    {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceSystem() 
    {
        return sourceSystem;
    }
    public void setEnable(Long enable) 
    {
        this.enable = enable;
    }

    public Long getEnable() 
    {
        return enable;
    }
    public void setmCategory(String mCategory) 
    {
        this.mCategory = mCategory;
    }

    public String getmCategory() 
    {
        return mCategory;
    }
    public void setSpecifications(String specifications) 
    {
        this.specifications = specifications;
    }

    public String getSpecifications() 
    {
        return specifications;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setCategoriesTaxes(BigDecimal categoriesTaxes) 
    {
        this.categoriesTaxes = categoriesTaxes;
    }

    public BigDecimal getCategoriesTaxes() 
    {
        return categoriesTaxes;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setgWeight(BigDecimal gWeight) 
    {
        this.gWeight = gWeight;
    }

    public BigDecimal getgWeight() 
    {
        return gWeight;
    }
    public void setnWeight(BigDecimal nWeight) 
    {
        this.nWeight = nWeight;
    }

    public BigDecimal getnWeight() 
    {
        return nWeight;
    }
    public void setWeight(String weight) 
    {
        this.weight = weight;
    }

    public String getWeight() 
    {
        return weight;
    }
    public void setVolume(BigDecimal volume) 
    {
        this.volume = volume;
    }

    public BigDecimal getVolume() 
    {
        return volume;
    }
    public void setvUnit(String vUnit) 
    {
        this.vUnit = vUnit;
    }

    public String getvUnit() 
    {
        return vUnit;
    }
    public void setAbcAttribute(String abcAttribute) 
    {
        this.abcAttribute = abcAttribute;
    }

    public String getAbcAttribute() 
    {
        return abcAttribute;
    }
    public void setAvoidInspect(Long avoidInspect) 
    {
        this.avoidInspect = avoidInspect;
    }

    public Long getAvoidInspect() 
    {
        return avoidInspect;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("calculationUnit", getCalculationUnit())
            .append("purchaser", getPurchaser())
            .append("lUpdated", getlUpdated())
            .append("lUpdateTime", getlUpdateTime())
            .append("sourceSystem", getSourceSystem())
            .append("enable", getEnable())
            .append("mCategory", getmCategory())
            .append("specifications", getSpecifications())
            .append("model", getModel())
            .append("brand", getBrand())
            .append("categoriesTaxes", getCategoriesTaxes())
            .append("image", getImage())
            .append("gWeight", getgWeight())
            .append("nWeight", getnWeight())
            .append("weight", getWeight())
            .append("volume", getVolume())
            .append("vUnit", getvUnit())
            .append("abcAttribute", getAbcAttribute())
            .append("avoidInspect", getAvoidInspect())
            .toString();
    }
}
