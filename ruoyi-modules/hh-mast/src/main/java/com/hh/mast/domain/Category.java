package com.hh.mast.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 品类对象 category
 * 
 * @author ruoyi
 * @date 2023-11-22
 */
public class Category extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 品类序号 */
    private Long categoryid;

    /** 品类代码 */
    @Excel(name = "品类代码")
    private String categoryCode;

    /** 品类名称 */
    @Excel(name = "品类名称")
    private Long categoryName;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long enable;

    /** 是否允许超量送货 */
    @Excel(name = "是否允许超量送货")
    private Long isNo;

    /** 上级品类 */
    @Excel(name = "上级品类")
    private String superiorCategory;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creationTime;

    /** 最后更新人 */
    @Excel(name = "最后更新人")
    private String lUpdated;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lUpdateTime;

    /** 业务实体名称 */
    private String bEName;

    /** 公司 */
    private String company;

    /** 计量单位 */
    private String meteringUnit;

    /** 来源系统 */
    private String sourceSystem;

    /** 引入要求 */
    private String introductionRequirements;

    /** 分配采购人 */
    private String assignPurchaser;

    public void setCategoryid(Long categoryid) 
    {
        this.categoryid = categoryid;
    }

    public Long getCategoryid() 
    {
        return categoryid;
    }
    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }
    public void setCategoryName(Long categoryName) 
    {
        this.categoryName = categoryName;
    }

    public Long getCategoryName() 
    {
        return categoryName;
    }
    public void setEnable(Long enable) 
    {
        this.enable = enable;
    }

    public Long getEnable() 
    {
        return enable;
    }
    public void setIsNo(Long isNo) 
    {
        this.isNo = isNo;
    }

    public Long getIsNo() 
    {
        return isNo;
    }
    public void setSuperiorCategory(String superiorCategory) 
    {
        this.superiorCategory = superiorCategory;
    }

    public String getSuperiorCategory() 
    {
        return superiorCategory;
    }
    public void setCreationTime(Date creationTime) 
    {
        this.creationTime = creationTime;
    }

    public Date getCreationTime() 
    {
        return creationTime;
    }
    public void setlUpdated(String lUpdated) 
    {
        this.lUpdated = lUpdated;
    }

    public String getlUpdated() 
    {
        return lUpdated;
    }
    public void setlUpdateTime(Date lUpdateTime) 
    {
        this.lUpdateTime = lUpdateTime;
    }

    public Date getlUpdateTime() 
    {
        return lUpdateTime;
    }
    public void setbEName(String bEName) 
    {
        this.bEName = bEName;
    }

    public String getbEName() 
    {
        return bEName;
    }
    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }
    public void setMeteringUnit(String meteringUnit) 
    {
        this.meteringUnit = meteringUnit;
    }

    public String getMeteringUnit() 
    {
        return meteringUnit;
    }
    public void setSourceSystem(String sourceSystem) 
    {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceSystem() 
    {
        return sourceSystem;
    }
    public void setIntroductionRequirements(String introductionRequirements) 
    {
        this.introductionRequirements = introductionRequirements;
    }

    public String getIntroductionRequirements() 
    {
        return introductionRequirements;
    }
    public void setAssignPurchaser(String assignPurchaser) 
    {
        this.assignPurchaser = assignPurchaser;
    }

    public String getAssignPurchaser() 
    {
        return assignPurchaser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryid", getCategoryid())
            .append("categoryCode", getCategoryCode())
            .append("categoryName", getCategoryName())
            .append("enable", getEnable())
            .append("isNo", getIsNo())
            .append("superiorCategory", getSuperiorCategory())
            .append("creationTime", getCreationTime())
            .append("lUpdated", getlUpdated())
            .append("lUpdateTime", getlUpdateTime())
            .append("bEName", getbEName())
            .append("company", getCompany())
            .append("meteringUnit", getMeteringUnit())
            .append("sourceSystem", getSourceSystem())
            .append("introductionRequirements", getIntroductionRequirements())
            .append("assignPurchaser", getAssignPurchaser())
            .toString();
    }
}
