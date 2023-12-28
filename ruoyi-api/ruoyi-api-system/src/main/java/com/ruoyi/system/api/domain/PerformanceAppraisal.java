package com.ruoyi.system.api.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 绩效考评对象 performance_appraisal
 * 
 * @author ruoyi
 * @date 2023-12-22
 */
public class PerformanceAppraisal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 考评id */
    private Long evaluationId;

    /** 考评编码 */
    @Excel(name = "考评编码")
    private String evaluationCode;

    /** 考评名称 */
    @Excel(name = "考评名称")
    private String evaluationName;

    /** 考评状态 */
    @Excel(name = "考评状态")
    private Long evaluationState;

    /** 考评模板id */
    @Excel(name = "考评模板id")
    private Long evaluationTemplateId;

    /** 考评模板编码 */
    @Excel(name = "考评模板编码")
    private String evaluationTemplateCode;

    /** 考评模板名称 */
    @Excel(name = "考评模板名称")
    private String evaluationTemplateName;

    /** 考评方式 */
    @Excel(name = "考评方式")
    private String evaluationMethod;

    /** 考评负责人 */
    @Excel(name = "考评负责人")
    private String evaluationHead;

    /** 分部 */
    @Excel(name = "分部")
    private String branch;

    /** 部门 */
    @Excel(name = "部门")
    private String contactsDepartment;

    /** 考评周期 */
    @Excel(name = "考评周期")
    private String evaluationCycle;

    /** 考评日期从 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考评日期从", width = 30, dateFormat = "yyyy-MM-dd")
    private Date evaluationDateFrom;

    /** 考评日期至 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考评日期至", width = 30, dateFormat = "yyyy-MM-dd")
    private Date evaluationDateTo;

    /** 考评说明 */
    @Excel(name = "考评说明")
    private String evaluationDescription;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creationTime;

    /** 考评附件 */
    @Excel(name = "考评附件")
    private String assessmentAttachment;

    /** 参评供应商信息 */
    private List<AttendSupplier> attendSupplierList;

    /** 评分明细信息 */
    private List<ScoringDetails> scoringDetailsList;

    public void setEvaluationId(Long evaluationId) 
    {
        this.evaluationId = evaluationId;
    }

    public Long getEvaluationId() 
    {
        return evaluationId;
    }
    public void setEvaluationCode(String evaluationCode)
    {
        this.evaluationCode = evaluationCode;
    }

    public String getEvaluationCode()
    {
        return evaluationCode;
    }
    public void setEvaluationName(String evaluationName) 
    {
        this.evaluationName = evaluationName;
    }

    public String getEvaluationName() 
    {
        return evaluationName;
    }
    public void setEvaluationState(Long evaluationState) 
    {
        this.evaluationState = evaluationState;
    }

    public Long getEvaluationState() 
    {
        return evaluationState;
    }
    public void setEvaluationTemplateCode(String evaluationTemplateCode)
    {
        this.evaluationTemplateCode = evaluationTemplateCode;
    }

    public String getEvaluationTemplateCode()
    {
        return evaluationTemplateCode;
    }
    public void setEvaluationTemplateName(String evaluationTemplateName) 
    {
        this.evaluationTemplateName = evaluationTemplateName;
    }

    public String getEvaluationTemplateName() 
    {
        return evaluationTemplateName;
    }
    public void setEvaluationMethod(String evaluationMethod) 
    {
        this.evaluationMethod = evaluationMethod;
    }

    public String getEvaluationMethod() 
    {
        return evaluationMethod;
    }
    public void setEvaluationHead(String evaluationHead) 
    {
        this.evaluationHead = evaluationHead;
    }

    public String getEvaluationHead() 
    {
        return evaluationHead;
    }
    public void setBranch(String branch) 
    {
        this.branch = branch;
    }

    public String getBranch() 
    {
        return branch;
    }
    public void setContactsDepartment(String contactsDepartment) 
    {
        this.contactsDepartment = contactsDepartment;
    }

    public String getContactsDepartment() 
    {
        return contactsDepartment;
    }
    public void setEvaluationCycle(String evaluationCycle) 
    {
        this.evaluationCycle = evaluationCycle;
    }

    public String getEvaluationCycle() 
    {
        return evaluationCycle;
    }
    public void setEvaluationDateFrom(Date evaluationDateFrom) 
    {
        this.evaluationDateFrom = evaluationDateFrom;
    }

    public Date getEvaluationDateFrom() 
    {
        return evaluationDateFrom;
    }
    public void setEvaluationDateTo(Date evaluationDateTo) 
    {
        this.evaluationDateTo = evaluationDateTo;
    }

    public Date getEvaluationDateTo() 
    {
        return evaluationDateTo;
    }
    public void setEvaluationDescription(String evaluationDescription) 
    {
        this.evaluationDescription = evaluationDescription;
    }

    public String getEvaluationDescription() 
    {
        return evaluationDescription;
    }
    public void setCreationTime(Date creationTime) 
    {
        this.creationTime = creationTime;
    }

    public Date getCreationTime() 
    {
        return creationTime;
    }
    public void setAssessmentAttachment(String assessmentAttachment) 
    {
        this.assessmentAttachment = assessmentAttachment;
    }

    public String getAssessmentAttachment() 
    {
        return assessmentAttachment;
    }

    public List<AttendSupplier> getAttendSupplierList()
    {
        return attendSupplierList;
    }

    public void setAttendSupplierList(List<AttendSupplier> attendSupplierList)
    {
        this.attendSupplierList = attendSupplierList;
    }

    public List<ScoringDetails> getScoringDetailsList()
    {
        return scoringDetailsList;
    }

    public void setScoringDetailsList(List<ScoringDetails> scoringDetailsList)
    {
        this.scoringDetailsList = scoringDetailsList;
    }

    public Long getEvaluationTemplateId() {
        return evaluationTemplateId;
    }

    public void setEvaluationTemplateId(Long evaluationTemplateId) {
        this.evaluationTemplateId = evaluationTemplateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("evaluationId", getEvaluationId())
            .append("evaluationCode", getEvaluationCode())
            .append("evaluationName", getEvaluationName())
            .append("evaluationState", getEvaluationState())
            .append("evaluationTemplateCode", getEvaluationTemplateCode())
            .append("evaluationTemplateName", getEvaluationTemplateName())
            .append("evaluationMethod", getEvaluationMethod())
            .append("evaluationHead", getEvaluationHead())
            .append("branch", getBranch())
            .append("contactsDepartment", getContactsDepartment())
            .append("evaluationCycle", getEvaluationCycle())
            .append("evaluationDateFrom", getEvaluationDateFrom())
            .append("evaluationDateTo", getEvaluationDateTo())
            .append("evaluationDescription", getEvaluationDescription())
            .append("creationTime", getCreationTime())
            .append("assessmentAttachment", getAssessmentAttachment())
            .append("attendSupplierList", getAttendSupplierList())
            .append("scoringDetailsList", getScoringDetailsList())
            .append("evaluationTemplateId", getEvaluationTemplateId())
            .toString();
    }
}
