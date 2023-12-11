package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 行业类型对象 industry_type
 * 
 * @author ruoyi
 * @date 2023-11-29
 */
public class IndustryType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 行业类型ID */
    private Long industryTypeId;

    /** 行业类型编码 */
    @Excel(name = "行业类型编码")
    private String industryTypeCode;

    /** 行业类型名称 */
    @Excel(name = "行业类型名称")
    private String industryTypeName;

    /** 上级行业 */
    @Excel(name = "上级行业")
    private String superiorIndustry;

    /** 启用 */
    private Long enable;

    public void setIndustryTypeId(Long industryTypeId) 
    {
        this.industryTypeId = industryTypeId;
    }

    public Long getIndustryTypeId() 
    {
        return industryTypeId;
    }
    public void setIndustryTypeCode(String industryTypeCode) 
    {
        this.industryTypeCode = industryTypeCode;
    }

    public String getIndustryTypeCode() 
    {
        return industryTypeCode;
    }
    public void setIndustryTypeName(String industryTypeName) 
    {
        this.industryTypeName = industryTypeName;
    }

    public String getIndustryTypeName() 
    {
        return industryTypeName;
    }
    public void setSuperiorIndustry(String superiorIndustry) 
    {
        this.superiorIndustry = superiorIndustry;
    }

    public String getSuperiorIndustry() 
    {
        return superiorIndustry;
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
            .append("industryTypeId", getIndustryTypeId())
            .append("industryTypeCode", getIndustryTypeCode())
            .append("industryTypeName", getIndustryTypeName())
            .append("superiorIndustry", getSuperiorIndustry())
            .append("enable", getEnable())
            .toString();
    }
}
