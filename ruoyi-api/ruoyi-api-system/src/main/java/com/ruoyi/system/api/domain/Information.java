package com.ruoyi.system.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 企业基本信息对象 information
 *
 * @author ruoyi
 * @date 2023-12-19
 */
public class Information extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 企业id */
    private Long enterpriseId;

    /** 董事长 */
    @Excel(name = "董事长")
    private String chairman;

    /** 总经理 */
    @Excel(name = "总经理")
    private String president;

    /** 是否上市 */
    @Excel(name = "是否上市")
    private String listed;

    /** 股票代码 */
    @Excel(name = "股票代码")
    private String stockCode;

    /** 股权结构附件 */
    @Excel(name = "股权结构附件")
    private String attachment;

    /** 行业排名 */
    @Excel(name = "行业排名")
    private Long industryRanking;

    /** 是否愿意配合审计 */
    @Excel(name = "是否愿意配合审计")
    private String cooperateWith;

    /** 公司员工数量 */
    @Excel(name = "公司员工数量")
    private Long numberOfEmployees;

    /** 管理人员数量 */
    @Excel(name = "管理人员数量")
    private Long administrativeNumber;

    /** 组织架构附件 */
    @Excel(name = "组织架构附件")
    private String Structure;

    /** 经主要竞争对手营范围 */
    @Excel(name = "经主要竞争对手营范围")
    private String majorCompetitors;

    /** 经营范围 */
    @Excel(name = "经营范围")
    private String businessScope;

    /** 调查表id */
    @Excel(name = "调查表id")
    private Long questionnaireId;

    public void setEnterpriseId(Long enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId()
    {
        return enterpriseId;
    }
    public void setChairman(String chairman)
    {
        this.chairman = chairman;
    }

    public String getChairman()
    {
        return chairman;
    }
    public void setPresident(String president)
    {
        this.president = president;
    }

    public String getPresident()
    {
        return president;
    }
    public void setListed(String listed)
    {
        this.listed = listed;
    }

    public String getListed()
    {
        return listed;
    }
    public void setStockCode(String stockCode)
    {
        this.stockCode = stockCode;
    }

    public String getStockCode()
    {
        return stockCode;
    }
    public void setAttachment(String attachment)
    {
        this.attachment = attachment;
    }

    public String getAttachment()
    {
        return attachment;
    }
    public void setIndustryRanking(Long industryRanking)
    {
        this.industryRanking = industryRanking;
    }

    public Long getIndustryRanking()
    {
        return industryRanking;
    }
    public void setCooperateWith(String cooperateWith)
    {
        this.cooperateWith = cooperateWith;
    }

    public String getCooperateWith()
    {
        return cooperateWith;
    }
    public void setNumberOfEmployees(Long numberOfEmployees)
    {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Long getNumberOfEmployees()
    {
        return numberOfEmployees;
    }
    public void setAdministrativeNumber(Long administrativeNumber)
    {
        this.administrativeNumber = administrativeNumber;
    }

    public Long getAdministrativeNumber()
    {
        return administrativeNumber;
    }
    public void setStructure(String Structure)
    {
        this.Structure = Structure;
    }

    public String getStructure()
    {
        return Structure;
    }
    public void setMajorCompetitors(String majorCompetitors)
    {
        this.majorCompetitors = majorCompetitors;
    }

    public String getMajorCompetitors()
    {
        return majorCompetitors;
    }
    public void setBusinessScope(String businessScope)
    {
        this.businessScope = businessScope;
    }

    public String getBusinessScope()
    {
        return businessScope;
    }
    public void setQuestionnaireId(Long questionnaireId)
    {
        this.questionnaireId = questionnaireId;
    }

    public Long getQuestionnaireId()
    {
        return questionnaireId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("enterpriseId", getEnterpriseId())
                .append("chairman", getChairman())
                .append("president", getPresident())
                .append("listed", getListed())
                .append("stockCode", getStockCode())
                .append("attachment", getAttachment())
                .append("industryRanking", getIndustryRanking())
                .append("cooperateWith", getCooperateWith())
                .append("numberOfEmployees", getNumberOfEmployees())
                .append("administrativeNumber", getAdministrativeNumber())
                .append("Structure", getStructure())
                .append("majorCompetitors", getMajorCompetitors())
                .append("businessScope", getBusinessScope())
                .append("questionnaireId", getQuestionnaireId())
                .toString();
    }
}
