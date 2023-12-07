package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 需求类型对象 requirement_table
 * 
 * @author ruoyi
 * @date 2023-12-01
 */
public class RequirementTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 需求类型ID */
    private Long requirementId;

    /** 需求类型名称 */
    @Excel(name = "需求类型名称")
    private String requirementType;

    public void setRequirementId(Long requirementId) 
    {
        this.requirementId = requirementId;
    }

    public Long getRequirementId() 
    {
        return requirementId;
    }
    public void setRequirementType(String requirementType) 
    {
        this.requirementType = requirementType;
    }

    public String getRequirementType() 
    {
        return requirementType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("requirementId", getRequirementId())
            .append("requirementType", getRequirementType())
            .toString();
    }
}
