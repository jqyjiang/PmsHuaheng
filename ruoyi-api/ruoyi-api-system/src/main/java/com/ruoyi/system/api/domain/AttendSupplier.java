package com.ruoyi.system.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 参评供应商对象 attend_supplier
 *
 * @author ruoyi
 * @date 2023-12-22
 */
public class AttendSupplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 参评id */
    private Long attendId;

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

    /** 考评id */
    @Excel(name = "考评id")
    private Long evaluationId;

    /** 汇总得分 */
    @Excel(name = "汇总得分")
    private double summaryScore;

    /** 得分 */
    @Excel(name = "得分")
    private double score;

    /** 等级 */
    @Excel(name = "等级")
    private String grade;

    /** 考评排名 */
    @Excel(name = "考评排名")
    private int evaluationRanking;

    /** 建议策略 */
    @Excel(name = "建议策略")
    private String strategy;

    public void setAttendId(Long attendId)
    {
        this.attendId = attendId;
    }

    public Long getAttendId()
    {
        return attendId;
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
    public void setEvaluationId(Long evaluationId)
    {
        this.evaluationId = evaluationId;
    }

    public Long getEvaluationId()
    {
        return evaluationId;
    }
    public void setSummaryScore(double summaryScore)
    {
        this.summaryScore = summaryScore;
    }

    public double getSummaryScore()
    {
        return summaryScore;
    }
    public void setScore(double score)
    {
        this.score = score;
    }

    public double getScore()
    {
        return score;
    }
    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getGrade()
    {
        return grade;
    }
    public void setEvaluationRanking(int evaluationRanking)
    {
        this.evaluationRanking = evaluationRanking;
    }

    public int getEvaluationRanking()
    {
        return evaluationRanking;
    }
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }

    public String getStrategy()
    {
        return strategy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("attendId", getAttendId())
                .append("supplierCode", getSupplierCode())
                .append("supplierName", getSupplierName())
                .append("categoryCode", getCategoryCode())
                .append("categoryName", getCategoryName())
                .append("evaluationId", getEvaluationId())
                .append("summaryScore", getSummaryScore())
                .append("score", getScore())
                .append("grade", getGrade())
                .append("evaluationRanking", getEvaluationRanking())
                .append("strategy", getStrategy())
                .toString();
    }
}