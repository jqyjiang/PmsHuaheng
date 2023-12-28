package com.ruoyi.system.api.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 调查表对象 questionnaire
 * 
 * @author ruoyi
 * @date 2023-12-18
 */
public class Questionnaire extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调查id */
    private Long questionnaireId;

    /** 调查编码 */
    @Excel(name = "调查编码")
    private String questionnaireCode;

    /** 调查标题 */
    @Excel(name = "调查标题")
    private String questionnaireTitle;

    /** 调查状态 */
    @Excel(name = "调查状态")
    private Long questionnaireState;

    /** 调查类型 */
    @Excel(name = "调查类型")
    private Long questionnaireType;

    /** 公司 */
    @Excel(name = "公司")
    private String company;

    /** 调查分类 */
    @Excel(name = "调查分类")
    private Long surveyClassification;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 供应商联系人 */
    @Excel(name = "供应商联系人")
    private String supplierContact;

    /** 供应商联系方式 */
    @Excel(name = "供应商联系方式")
    private String supplierSupplierContactInformation;

    /** 供应商联系邮箱 */
    @Excel(name = "供应商联系邮箱")
    private String supplierContactEmail;

    /** 调查回复时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调查回复时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date surveyResponseTime;

    /** 采方调查说明 */
    @Excel(name = "采方调查说明")
    private String surveyDescription;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;


    /** 企业基本信息信息 */
    private Information informationList;

    public void setQuestionnaireId(Long questionnaireId) 
    {
        this.questionnaireId = questionnaireId;
    }

    public Long getQuestionnaireId() 
    {
        return questionnaireId;
    }
    public void setQuestionnaireCode(String questionnaireCode) 
    {
        this.questionnaireCode = questionnaireCode;
    }

    public String getQuestionnaireCode() 
    {
        return questionnaireCode;
    }
    public void setQuestionnaireTitle(String questionnaireTitle) 
    {
        this.questionnaireTitle = questionnaireTitle;
    }

    public String getQuestionnaireTitle() 
    {
        return questionnaireTitle;
    }
    public void setQuestionnaireState(Long questionnaireState) 
    {
        this.questionnaireState = questionnaireState;
    }

    public Long getQuestionnaireState() 
    {
        return questionnaireState;
    }
    public void setQuestionnaireType(Long questionnaireType) 
    {
        this.questionnaireType = questionnaireType;
    }

    public Long getQuestionnaireType() 
    {
        return questionnaireType;
    }
    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }
    public void setSurveyClassification(Long surveyClassification) 
    {
        this.surveyClassification = surveyClassification;
    }

    public Long getSurveyClassification() 
    {
        return surveyClassification;
    }
    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }
    public void setSupplierContact(String supplierContact) 
    {
        this.supplierContact = supplierContact;
    }

    public String getSupplierContact() 
    {
        return supplierContact;
    }
    public void setSupplierSupplierContactInformation(String supplierSupplierContactInformation) 
    {
        this.supplierSupplierContactInformation = supplierSupplierContactInformation;
    }

    public String getSupplierSupplierContactInformation() 
    {
        return supplierSupplierContactInformation;
    }
    public void setSupplierContactEmail(String supplierContactEmail) 
    {
        this.supplierContactEmail = supplierContactEmail;
    }

    public String getSupplierContactEmail() 
    {
        return supplierContactEmail;
    }
    public void setSurveyResponseTime(Date surveyResponseTime) 
    {
        this.surveyResponseTime = surveyResponseTime;
    }

    public Date getSurveyResponseTime() 
    {
        return surveyResponseTime;
    }
    public void setSurveyDescription(String surveyDescription) 
    {
        this.surveyDescription = surveyDescription;
    }

    public String getSurveyDescription() 
    {
        return surveyDescription;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public Information getInformationList() {
        return informationList;
    }

    public void setInformationList(Information informationList) {
        this.informationList = informationList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("questionnaireId", getQuestionnaireId())
            .append("questionnaireCode", getQuestionnaireCode())
            .append("questionnaireTitle", getQuestionnaireTitle())
            .append("questionnaireState", getQuestionnaireState())
            .append("questionnaireType", getQuestionnaireType())
            .append("company", getCompany())
            .append("surveyClassification", getSurveyClassification())
            .append("supplierName", getSupplierName())
            .append("supplierContact", getSupplierContact())
            .append("supplierSupplierContactInformation", getSupplierSupplierContactInformation())
            .append("supplierContactEmail", getSupplierContactEmail())
            .append("surveyResponseTime", getSurveyResponseTime())
            .append("surveyDescription", getSurveyDescription())
            .append("status", getStatus())
            .append("informationList", getInformationList())
            .toString();
    }
}
