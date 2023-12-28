package com.ruoyi.system.api.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 考评模板对象 scoring_template
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
public class ScoringTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评分模板id */
    private Long scoringTemplateId;

    /** 评分模板编码 */
    @Excel(name = "评分模板编码")
    private String scoringTemplateCode;

    /** 评分模板名称 */
    @Excel(name = "评分模板名称")
    private String scoringTemplateName;

    /** 模板状态 */
    @Excel(name = "模板状态")
    private Long templateState;

    /** 模板类型 */
    @Excel(name = "模板类型")
    private Long templateType;

    /** 评分明细信息 */
    private List<ScoringDetails> scoringDetailsList;

    public void setScoringTemplateId(Long scoringTemplateId) 
    {
        this.scoringTemplateId = scoringTemplateId;
    }

    public Long getScoringTemplateId() 
    {
        return scoringTemplateId;
    }
    public void setScoringTemplateCode(String scoringTemplateCode) 
    {
        this.scoringTemplateCode = scoringTemplateCode;
    }

    public String getScoringTemplateCode() 
    {
        return scoringTemplateCode;
    }
    public void setScoringTemplateName(String scoringTemplateName) 
    {
        this.scoringTemplateName = scoringTemplateName;
    }

    public String getScoringTemplateName() 
    {
        return scoringTemplateName;
    }
    public void setTemplateState(Long templateState) 
    {
        this.templateState = templateState;
    }

    public Long getTemplateState() 
    {
        return templateState;
    }
    public void setTemplateType(Long templateType) 
    {
        this.templateType = templateType;
    }

    public Long getTemplateType() 
    {
        return templateType;
    }

    public List<ScoringDetails> getScoringDetailsList()
    {
        return scoringDetailsList;
    }

    public void setScoringDetailsList(List<ScoringDetails> scoringDetailsList)
    {
        this.scoringDetailsList = scoringDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("scoringTemplateId", getScoringTemplateId())
            .append("scoringTemplateCode", getScoringTemplateCode())
            .append("scoringTemplateName", getScoringTemplateName())
            .append("templateState", getTemplateState())
            .append("templateType", getTemplateType())
            .append("scoringDetailsList", getScoringDetailsList())
            .toString();
    }
}
