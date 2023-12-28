package com.ruoyi.system.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 考评指标对象 indicators
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
public class Indicators extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 指标id */
    private Long indicatorId;

    /** 指标编码 */
    @Excel(name = "指标编码")
    private String indicatorCode;

    /** 指标名称 */
    @Excel(name = "指标名称")
    private String indicatorName;

    /** 评分方式 */
    @Excel(name = "评分方式")
    private String scoringMethod;

    /** 指标类型 */
    @Excel(name = "指标类型")
    private String indicatorType;

    /** 分值从 */
    @Excel(name = "分值从")
    private String scoreFrom;

    /** 分支至 */
    @Excel(name = "分支至")
    private String scoreTo;

    /** 基准分值 */
    @Excel(name = "基准分值")
    private String benchmarkScore;

    /** 评分人信息 */
    @Excel(name = "评分人信息")
    private String rater;

    /** 评分标准 */
    @Excel(name = "评分标准")
    private String scoringCriteria;

    /** 配置 */
    @Excel(name = "配置")
    private String configuration;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    public void setIndicatorId(Long indicatorId) 
    {
        this.indicatorId = indicatorId;
    }

    public Long getIndicatorId() 
    {
        return indicatorId;
    }
    public void setIndicatorCode(String indicatorCode) 
    {
        this.indicatorCode = indicatorCode;
    }

    public String getIndicatorCode() 
    {
        return indicatorCode;
    }
    public void setIndicatorName(String indicatorName) 
    {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorName() 
    {
        return indicatorName;
    }
    public void setScoringMethod(String scoringMethod) 
    {
        this.scoringMethod = scoringMethod;
    }

    public String getScoringMethod() 
    {
        return scoringMethod;
    }
    public void setIndicatorType(String indicatorType) 
    {
        this.indicatorType = indicatorType;
    }

    public String getIndicatorType() 
    {
        return indicatorType;
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
    public void setBenchmarkScore(String benchmarkScore) 
    {
        this.benchmarkScore = benchmarkScore;
    }

    public String getBenchmarkScore() 
    {
        return benchmarkScore;
    }
    public void setRater(String rater) 
    {
        this.rater = rater;
    }

    public String getRater() 
    {
        return rater;
    }
    public void setScoringCriteria(String scoringCriteria) 
    {
        this.scoringCriteria = scoringCriteria;
    }

    public String getScoringCriteria() 
    {
        return scoringCriteria;
    }
    public void setConfiguration(String configuration) 
    {
        this.configuration = configuration;
    }

    public String getConfiguration() 
    {
        return configuration;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("indicatorId", getIndicatorId())
            .append("indicatorCode", getIndicatorCode())
            .append("indicatorName", getIndicatorName())
            .append("scoringMethod", getScoringMethod())
            .append("indicatorType", getIndicatorType())
            .append("scoreFrom", getScoreFrom())
            .append("scoreTo", getScoreTo())
            .append("benchmarkScore", getBenchmarkScore())
            .append("rater", getRater())
            .append("scoringCriteria", getScoringCriteria())
            .append("configuration", getConfiguration())
            .append("state", getState())
            .toString();
    }
}
