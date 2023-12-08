package com.hh.pms.mast.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 物料维护对象 material
 *
 * @author ruoyi
 * @date 2023-11-29
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
    private Integer calculationUnit;

    /**单位*/
    @Excel(name = "单位名")
    private String unitName;

    //计量单位
    @Excel(name = "计算单位")
    private String metering_unit;

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
    private Long mCategory;

    //主品类名称
    @Excel(name = "品类名称")
    private String categoryName;

    /** 规格 */
    private String specifications;

    /** 型号 */
    private String model;

    /** 品牌 */
    private String brand;

    /** 默认税种/税率 */
    private String categoriesTaxes;

    /** 物料图片 */
    private String image;

    /** 毛重 */
    private BigDecimal gWeight;

    /** 净重 */
    private BigDecimal nWeight;

    /** 重量单位 */
    private Long weight;
    /**重量单位显示*/
    private String weightName;

    /** 体积 */
    private BigDecimal volume;

    /** 体积单位 */
    private Integer vUnit;
    /**体积单位显示*/
    private String vUnitName;

    /** 物料ABC属性 */
    private String abcAttribute;

    /** 是否免检 */
    private Long avoidInspect;

    public String getvUnitName() {
        return vUnitName;
    }

    public void setvUnitName(String vUnitName) {
        this.vUnitName = vUnitName;
    }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getMetering_unit() {
        return metering_unit;
    }

    public String getCategoriesTaxes() {
        return categoriesTaxes;
    }

    public void setCategoriesTaxes(String categoriesTaxes) {
        this.categoriesTaxes = categoriesTaxes;
    }

    public void setMetering_unit(String metering_unit) {
        this.metering_unit = metering_unit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

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
    public void setCalculationUnit(Integer calculationUnit)
    {
        this.calculationUnit = calculationUnit;
    }

    public Integer getCalculationUnit()
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
    public void setmCategory(Long mCategory)
    {
        this.mCategory = mCategory;
    }

    public Long getmCategory()
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
    public void setWeight(Long weight)
    {
        this.weight = weight;
    }

    public Long getWeight()
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
    public void setvUnit(Integer vUnit)
    {
        this.vUnit = vUnit;
    }

    public Integer getvUnit()
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
            .append("unitName",getUnitName())
            .append("meteringUnit",getMetering_unit())
            .append("purchaser", getPurchaser())
            .append("lUpdated", getlUpdated())
            .append("lUpdateTime", getlUpdateTime())
            .append("sourceSystem", getSourceSystem())
            .append("enable", getEnable())
            .append("mCategory", getmCategory())
            .append("categoryName",getCategoryName())
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
            .append("vUnitName",getvUnitName())
            .append("abcAttribute", getAbcAttribute())
            .append("avoidInspect", getAvoidInspect())
            .toString();
    }
}
