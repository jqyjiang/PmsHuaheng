package com.hh.pms.mast.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 计算单位定义对象 unit_of_account
 *
 * @author ruoyi
 * @date 2023-11-28
 */
public class UnitOfAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单位ID */
    private Long unitId;

    /** 单位类型名称 */
    @Excel(name = "单位类型名称")
    private Long unitTypeName;

    /** 计算单位编码 */
    @Excel(name = "计算单位编码")
    private String calculationUnitCode;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String meteringUnit;

    /** 来源系统 */
    @Excel(name = "来源系统")
    private String sourceSystem;

    /** 单位转换率 */
    @Excel(name = "单位转换率")
    private BigDecimal unitConversionRate;

    /** 精度 */
    @Excel(name = "精度")
    private BigDecimal accuracy;

    /** 是否启用 0：启用 1：停用 */
    @Excel(name = "是否启用 0：启用 1：停用")
    private Long enable;

    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId()
    {
        return unitId;
    }
    public void setUnitTypeName(Long unitTypeName)
    {
        this.unitTypeName = unitTypeName;
    }

    public Long getUnitTypeName()
    {
        return unitTypeName;
    }
    public void setCalculationUnitCode(String calculationUnitCode)
    {
        this.calculationUnitCode = calculationUnitCode;
    }

    public String getCalculationUnitCode()
    {
        return calculationUnitCode;
    }
    public void setMeteringUnit(String meteringUnit)
    {
        this.meteringUnit = meteringUnit;
    }

    public String getMeteringUnit()
    {
        return meteringUnit;
    }
    public void setSourceSystem(String sourceSystem)
    {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceSystem()
    {
        return sourceSystem;
    }
    public void setUnitConversionRate(BigDecimal unitConversionRate)
    {
        this.unitConversionRate = unitConversionRate;
    }

    public BigDecimal getUnitConversionRate()
    {
        return unitConversionRate;
    }
    public void setAccuracy(BigDecimal accuracy)
    {
        this.accuracy = accuracy;
    }

    public BigDecimal getAccuracy()
    {
        return accuracy;
    }
    public void setEnable(Long enable)
    {
        this.enable = enable;
    }

    public Long getEnable()
    {
        return enable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("unitId", getUnitId())
            .append("unitTypeName", getUnitTypeName())
            .append("calculationUnitCode", getCalculationUnitCode())
            .append("meteringUnit", getMeteringUnit())
            .append("sourceSystem", getSourceSystem())
            .append("unitConversionRate", getUnitConversionRate())
            .append("accuracy", getAccuracy())
            .append("enable", getEnable())
            .toString();
    }
}
