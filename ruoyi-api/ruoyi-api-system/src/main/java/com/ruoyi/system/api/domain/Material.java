package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

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
    @Excel(name = "品类编码")
    private String categoryCode;
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

    /**需求月度 统计分析*/
    @Excel(name = "需求月度")
    private String demandMonth;
    /**需求月度数 统计分析*/
    @Excel(name = "需求月度数")
    private String demandNum;
    /**需求状态 统计分析*/
    @Excel(name = "需求状态")
    private String demandStatus;
    /**需求状态数 统计分析*/
    @Excel(name = "需求状态数")
    private String count;
    /**订单来源 统计分析*/
    @Excel(name = "订单来源")
    private String orderSource;
    /**订单来源 统计分析*/
    @Excel(name = "订单来源数")
    private String oCount;
    /**月度订单 统计分析*/
    @Excel(name = "月度订单")
    private String Month;
    /**月度订单数 统计分析*/
    @Excel(name = "月度订单数")
    private String mCount;
    /**月度订单金额统计*/
    @Excel(name = "月度订单金额")
    private String aMoney;
    /**待收货订单*/
    @Excel(name = "待收货订单")
    private Long treatNum;
    /**待收货金额*/
    @Excel(name = "待收货金额")
    private Long treatMoney;
    /**已完成订单*/
    @Excel(name = "已完成订单")
    private Long stopNum;
    /**已完成金额*/
    @Excel(name = "已完成金额")
    private Long completeMoney;
    /**需求申请数*/
    @Excel(name = "需求申请数")
    private Long applyNum;
    /**对账申请总数*/
    @Excel(name = "对账申请总数")
    private Long reconciliationNum;
    /**对账申请被拒绝数*/
    @Excel(name = "对账申请被拒绝数")
    private Long reconciliationRefuseNum;
    /**订单总数*/
    @Excel(name = "订单总数")
    private Long orderTotal;
    /**订单总收货金额*/
    @Excel(name = "订单总收货金额")
    private Long orderTotalAmount;
    /**合同总数*/
    @Excel(name = "合同总数")
    private Long mentNum;
    /**合同未激活数*/
    @Excel(name = "合同未激活数")
    private Long mentNumNo;
    /**质检总数*/
    @Excel(name = "质检总数")
    private Long inspectTotal;
    /**质检未执行*/
    @Excel(name = "质检未执行")
    private Long inspectTotalNo;

    public Long getInspectTotal() {
        return inspectTotal;
    }

    public void setInspectTotal(Long inspectTotal) {
        this.inspectTotal = inspectTotal;
    }

    public Long getInspectTotalNo() {
        return inspectTotalNo;
    }

    public void setInspectTotalNo(Long inspectTotalNo) {
        this.inspectTotalNo = inspectTotalNo;
    }

    public Long getMentNum() {
        return mentNum;
    }

    public void setMentNum(Long mentNum) {
        this.mentNum = mentNum;
    }

    public Long getMentNumNo() {
        return mentNumNo;
    }

    public void setMentNumNo(Long mentNumNo) {
        this.mentNumNo = mentNumNo;
    }

    public Long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Long getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(Long orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public Long getReconciliationNum() {
        return reconciliationNum;
    }

    public void setReconciliationNum(Long reconciliationNum) {
        this.reconciliationNum = reconciliationNum;
    }

    public Long getReconciliationRefuseNum() {
        return reconciliationRefuseNum;
    }

    public void setReconciliationRefuseNum(Long reconciliationRefuseNum) {
        this.reconciliationRefuseNum = reconciliationRefuseNum;
    }

    public Long getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Long applyNum) {
        this.applyNum = applyNum;
    }

    public Long getTreatNum() {
        return treatNum;
    }

    public void setTreatNum(Long treatNum) {
        this.treatNum = treatNum;
    }

    public Long getTreatMoney() {
        return treatMoney;
    }

    public void setTreatMoney(Long treatMoney) {
        this.treatMoney = treatMoney;
    }

    public Long getStopNum() {
        return stopNum;
    }

    public void setStopNum(Long stopNum) {
        this.stopNum = stopNum;
    }

    public Long getCompleteMoney() {
        return completeMoney;
    }

    public void setCompleteMoney(Long completeMoney) {
        this.completeMoney = completeMoney;
    }

    public String getaMoney() {
        return aMoney;
    }
    public void setaMoney(String aMoney) {
        this.aMoney = aMoney;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getmCount() {
        return mCount;
    }

    public void setmCount(String mCount) {
        this.mCount = mCount;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getoCount() {
        return oCount;
    }

    public void setoCount(String oCount) {
        this.oCount = oCount;
    }

    public String getDemandStatus() {
        return demandStatus;
    }

    public void setDemandStatus(String demandStatus) {
        this.demandStatus = demandStatus;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDemandMonth() {
        return demandMonth;
    }

    public void setDemandMonth(String demandMonth) {
        this.demandMonth = demandMonth;
    }

    public String getDemandNum() {
        return demandNum;
    }

    public void setDemandNum(String demandNum) {
        this.demandNum = demandNum;
    }

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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("calculationUnit", getCalculationUnit())
            .append("unitName",getUnitName())
            .append("metering_unit",getMetering_unit())
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
            .append("demandMonth",getDemandMonth())
            .append("demandNum",getDemandNum())
            .append("demandStatus",getDemandStatus())
            .append("count",getCount())
            .append("orderSource",getOrderSource())
            .append("oCount",getoCount())
            .append("Month",getMonth())
            .append("mCount",getmCount())
            .append("aMoney",getaMoney())
            .append("treatNum",getTreatNum())
            .append("treatMoney",getTreatMoney())
            .append("stopNum",getStopNum())
            .append("completeMoney",getCompleteMoney())
            .append("applyNum",getApplyNum())
            .append("reconciliationNum",getReconciliationNum())
            .append("reconciliationRefuseNum",getReconciliationRefuseNum())
            .append("orderTotal",getOrderTotal())
            .append("orderTotalAmount",getOrderTotalAmount())
            .append("mentNum",getMentNum())
            .append("mentNumNo",getMentNumNo())
            .append("inspectTotal",getInspectTotal())
            .append("inspectTotalNo",getInspectTotalNo())
            .toString();
    }
}
