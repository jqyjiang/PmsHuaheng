package com.ruoyi.system.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 评分明细对象 scoring_details
 *
 * @author ruoyi
 * @date 2023-12-25
 */
public class ScoringDetails  extends BaseEntity implements Cloneable
{
    private static final long serialVersionUID = 1L;

    /** 评分明细id */
    private Long scoringDetailsId;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 品类编码 */
    @Excel(name = "品类编码")
    private String categoryCode;

    /** 品类名称 */
    @Excel(name = "品类名称")
    private String categoryName;

    /** 指标描述 */
    @Excel(name = "指标描述")
    private String indicatorDescription;

    /** 指标编码 */
    @Excel(name = "指标编码")
    private String indicatorCode;

    /** 评分标准 */
    @Excel(name = "评分标准")
    private String scoringCriteria;

    /** 权重(%) */
    @Excel(name = "权重(%)")
    private Double weight;

    /** 评分方式 */
    @Excel(name = "评分方式")
    private String scoringMethod;

    /** 评分人 */
    @Excel(name = "评分人")
    private String rater;

    /** 指标类型 */
    @Excel(name = "指标类型")
    private String indicatorType;

    /** 评分状态 */
    @Excel(name = "评分状态")
    private String ratingStatus;

    /** 得分 */
    @Excel(name = "得分")
    private double score;

    /** 选择项 */
    @Excel(name = "选择项")
    private String selectivity;

    /** 分值从 */
    @Excel(name = "分值从")
    private String scoreFrom;

    /** 分值至 */
    @Excel(name = "分值至")
    private String scoreTo;

    /** 反馈备注 */
    @Excel(name = "反馈备注")
    private String feedback;

    /** 考评id */
    @Excel(name = "考评id")
    private Long evaluationId;

    /** 模板id */
    @Excel(name = "模板id")
    private Long scoringTemplateId;

    public void setScoringDetailsId(Long scoringDetailsId)
    {
        this.scoringDetailsId = scoringDetailsId;
    }

    public Long getScoringDetailsId()
    {
        return scoringDetailsId;
    }
    public void setSupplierCode(String supplierCode)
    {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode()
    {
        return supplierCode;
    }
    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName()
    {
        return supplierName;
    }
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode()
    {
        return categoryCode;
    }
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }
    public void setIndicatorDescription(String indicatorDescription)
    {
        this.indicatorDescription = indicatorDescription;
    }

    public String getIndicatorDescription()
    {
        return indicatorDescription;
    }
    public void setIndicatorCode(String indicatorCode)
    {
        this.indicatorCode = indicatorCode;
    }

    public String getIndicatorCode()
    {
        return indicatorCode;
    }
    public void setScoringCriteria(String scoringCriteria)
    {
        this.scoringCriteria = scoringCriteria;
    }

    public String getScoringCriteria()
    {
        return scoringCriteria;
    }
    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    public Double getWeight()
    {
        return weight;
    }
    public void setScoringMethod(String scoringMethod)
    {
        this.scoringMethod = scoringMethod;
    }

    public String getScoringMethod()
    {
        return scoringMethod;
    }
    public void setRater(String rater)
    {
        this.rater = rater;
    }

    public String getRater()
    {
        return rater;
    }
    public void setIndicatorType(String indicatorType)
    {
        this.indicatorType = indicatorType;
    }

    public String getIndicatorType()
    {
        return indicatorType;
    }
    public void setRatingStatus(String ratingStatus)
    {
        this.ratingStatus = ratingStatus;
    }

    public String getRatingStatus()
    {
        return ratingStatus;
    }
    public void setScore(double score)
    {
        this.score = score;
    }

    public double getScore()
    {
        return score;
    }
    public void setSelectivity(String selectivity)
    {
        this.selectivity = selectivity;
    }

    public String getSelectivity()
    {
        return selectivity;
    }
    public void setScoreFrom(String scoreFrom)
    {
        this.scoreFrom = scoreFrom;
    }

    public String getScoreFrom()
    {
        return scoreFrom;
    }
    public void setScoreTo(String scoreTo)
    {
        this.scoreTo = scoreTo;
    }

    public String getScoreTo()
    {
        return scoreTo;
    }
    public void setFeedback(String feedback)
    {
        this.feedback = feedback;
    }

    public String getFeedback()
    {
        return feedback;
    }
    public void setEvaluationId(Long evaluationId)
    {
        this.evaluationId = evaluationId;
    }

    public Long getEvaluationId()
    {
        return evaluationId;
    }
    public void setScoringTemplateId(Long scoringTemplateId)
    {
        this.scoringTemplateId = scoringTemplateId;
    }

    public Long getScoringTemplateId()
    {
        return scoringTemplateId;
    }

    @Override
    public ScoringDetails clone() throws CloneNotSupportedException {
        return (ScoringDetails)super.clone();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("scoringDetailsId", getScoringDetailsId())
                .append("supplierCode", getSupplierCode())
                .append("supplierName", getSupplierName())
                .append("categoryCode", getCategoryCode())
                .append("categoryName", getCategoryName())
                .append("indicatorDescription", getIndicatorDescription())
                .append("indicatorCode", getIndicatorCode())
                .append("scoringCriteria", getScoringCriteria())
                .append("weight", getWeight())
                .append("scoringMethod", getScoringMethod())
                .append("rater", getRater())
                .append("indicatorType", getIndicatorType())
                .append("ratingStatus", getRatingStatus())
                .append("score", getScore())
                .append("selectivity", getSelectivity())
                .append("scoreFrom", getScoreFrom())
                .append("scoreTo", getScoreTo())
                .append("feedback", getFeedback())
                .append("evaluationId", getEvaluationId())
                .append("scoringTemplateId", getScoringTemplateId())
                .toString();
    }
}

