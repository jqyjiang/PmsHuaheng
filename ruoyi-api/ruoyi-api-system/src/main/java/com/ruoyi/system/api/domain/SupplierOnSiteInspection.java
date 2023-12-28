package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.api.domain.SupplierMaterialList;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 现场考察对象 supplier_on_site_inspection
 *
 * @author ruoyi
 * @date 2023-12-15
 */
public class SupplierOnSiteInspection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 考察id */
    private Long soiId;

    /** 考察编码 */
    @Excel(name = "考察编码")
    private String soiCode;

    /** 考察标题 */
    @Excel(name = "考察标题")
    private String soiTitle;

    /** 考察类型 */
    @Excel(name = "考察类型")
    private Long soiType;

    /** 公司 */
    @Excel(name = "公司")
    private String company;

    /** 考察日期从 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考察日期从", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionDateFrom;

    /** 考察日期至 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考察日期至", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionDateTo;

    /** 评分方式 */
    @Excel(name = "评分方式")
    private Long scoringMethod;

    /** 供应商编码 */
    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String sdName;

    /** 供应商联系人 */
    @Excel(name = "供应商联系人")
    private String supplierContact;

    /** 供应商联系电话 */
    @Excel(name = "供应商联系电话")
    private String supplierContactPhone;

    /** 供应商联系邮箱 */
    @Excel(name = "供应商联系邮箱")
    private String supplierContactEmail;

    /** 实际考察地址 */
    @Excel(name = "实际考察地址")
    private String actualAddress;

    /** 考察结果 */
    @Excel(name = "考察结果")
    private Long inspectionResults;

    /** 最终得分 */
    @Excel(name = "最终得分")
    private BigDecimal finalScore;

    /** 等级 */
    @Excel(name = "等级")
    private Long level;

    /** 综合意见 */
    @Excel(name = "综合意见")
    private String comprehensiveOpinions;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applicant;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private Long examine;

    public String getSoiCode() {
        return soiCode;
    }

    public void setSoiCode(String soiCode) {
        this.soiCode = soiCode;
    }

    /** 供应商物料信息 */
    private List<SupplierMaterialList> supplierMaterialListList;

    public void setSoiId(Long soiId)
    {
        this.soiId = soiId;
    }

    public Long getSoiId()
    {
        return soiId;
    }
    public void setSoiTitle(String soiTitle)
    {
        this.soiTitle = soiTitle;
    }

    public String getSoiTitle()
    {
        return soiTitle;
    }
    public void setSoiType(Long soiType)
    {
        this.soiType = soiType;
    }

    public Long getSoiType()
    {
        return soiType;
    }
    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCompany()
    {
        return company;
    }
    public void setInspectionDateFrom(Date inspectionDateFrom)
    {
        this.inspectionDateFrom = inspectionDateFrom;
    }

    public Date getInspectionDateFrom()
    {
        return inspectionDateFrom;
    }
    public void setInspectionDateTo(Date inspectionDateTo)
    {
        this.inspectionDateTo = inspectionDateTo;
    }

    public Date getInspectionDateTo()
    {
        return inspectionDateTo;
    }
    public void setScoringMethod(Long scoringMethod)
    {
        this.scoringMethod = scoringMethod;
    }

    public Long getScoringMethod()
    {
        return scoringMethod;
    }
    public void setSdName(String sdName)
    {
        this.sdName = sdName;
    }

    public String getSdName()
    {
        return sdName;
    }
    public void setSupplierContact(String supplierContact)
    {
        this.supplierContact = supplierContact;
    }

    public String getSupplierContact()
    {
        return supplierContact;
    }
    public void setSupplierContactPhone(String supplierContactPhone)
    {
        this.supplierContactPhone = supplierContactPhone;
    }

    public String getSupplierContactPhone()
    {
        return supplierContactPhone;
    }
    public void setSupplierContactEmail(String supplierContactEmail)
    {
        this.supplierContactEmail = supplierContactEmail;
    }

    public String getSupplierContactEmail()
    {
        return supplierContactEmail;
    }
    public void setActualAddress(String actualAddress)
    {
        this.actualAddress = actualAddress;
    }

    public String getActualAddress()
    {
        return actualAddress;
    }
    public void setInspectionResults(Long inspectionResults)
    {
        this.inspectionResults = inspectionResults;
    }

    public Long getInspectionResults()
    {
        return inspectionResults;
    }
    public void setFinalScore(BigDecimal finalScore)
    {
        this.finalScore = finalScore;
    }

    public BigDecimal getFinalScore()
    {
        return finalScore;
    }
    public void setLevel(Long level)
    {
        this.level = level;
    }

    public Long getLevel()
    {
        return level;
    }
    public void setComprehensiveOpinions(String comprehensiveOpinions)
    {
        this.comprehensiveOpinions = comprehensiveOpinions;
    }

    public String getComprehensiveOpinions()
    {
        return comprehensiveOpinions;
    }
    public void setApplicant(String applicant)
    {
        this.applicant = applicant;
    }

    public String getApplicant()
    {
        return applicant;
    }
    public void setExamine(Long examine)
    {
        this.examine = examine;
    }

    public Long getExamine()
    {
        return examine;
    }

    public List<SupplierMaterialList> getSupplierMaterialListList()
    {
        return supplierMaterialListList;
    }

    public void setSupplierMaterialListList(List<SupplierMaterialList> supplierMaterialListList)
    {
        this.supplierMaterialListList = supplierMaterialListList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("soiId", getSoiId())
                .append("soiCode", getSoiCode())
                .append("soiTitle", getSoiTitle())
                .append("soiType", getSoiType())
                .append("company", getCompany())
                .append("inspectionDateFrom", getInspectionDateFrom())
                .append("inspectionDateTo", getInspectionDateTo())
                .append("scoringMethod", getScoringMethod())
                .append("sdCode", getSdName())
                .append("supplierContact", getSupplierContact())
                .append("supplierContactPhone", getSupplierContactPhone())
                .append("supplierContactEmail", getSupplierContactEmail())
                .append("actualAddress", getActualAddress())
                .append("inspectionResults", getInspectionResults())
                .append("finalScore", getFinalScore())
                .append("level", getLevel())
                .append("comprehensiveOpinions", getComprehensiveOpinions())
                .append("applicant", getApplicant())
                .append("examine", getExamine())
                .append("supplierMaterialListList", getSupplierMaterialListList())
                .toString();
    }
}
