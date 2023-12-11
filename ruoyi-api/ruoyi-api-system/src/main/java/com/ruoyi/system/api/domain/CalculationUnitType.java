package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 计量单位类型定义对象 calculation_unit_type
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
public class CalculationUnitType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单位类型ID */
    private Long unitTypeId;

    /** 单位类型代码 */
    @Excel(name = "单位类型代码")
    private String unitTypeCode;

    /** 单位类型名称 */
    @Excel(name = "单位类型名称")
    private String unitTypeName;

    /** 是否启用 0：停用 1：启用 */
    @Excel(name = "是否启用 0：停用 1：启用")
    private Long enable;

    public void setUnitTypeId(Long unitTypeId) 
    {
        this.unitTypeId = unitTypeId;
    }

    public Long getUnitTypeId() 
    {
        return unitTypeId;
    }
    public void setUnitTypeCode(String unitTypeCode) 
    {
        this.unitTypeCode = unitTypeCode;
    }

    public String getUnitTypeCode() 
    {
        return unitTypeCode;
    }
    public void setUnitTypeName(String unitTypeName) 
    {
        this.unitTypeName = unitTypeName;
    }

    public String getUnitTypeName() 
    {
        return unitTypeName;
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
            .append("unitTypeId", getUnitTypeId())
            .append("unitTypeCode", getUnitTypeCode())
            .append("unitTypeName", getUnitTypeName())
            .append("enable", getEnable())
            .toString();
    }
}
