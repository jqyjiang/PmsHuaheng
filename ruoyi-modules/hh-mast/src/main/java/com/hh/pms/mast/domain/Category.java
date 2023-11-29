package com.hh.pms.mast.domain;

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
 * @date 2023-11-29
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
    private String categoryName;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long enable;

    /** 是否允许超量送货 */
    @Excel(name = "是否允许超量送货")
    private Long isNo;

    /** 上级品类 */
    @Excel(name = "上级品类")
    private Long superiorCategory;

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

    /** 公司 */
    private Long company;

    /** 计量单位 */
    private Long meteringUnit;

    /** 来源系统 */
    private String sourceSystem;

    /** 引入要求 1:严格 0：一般 */
    private Long introductionRequirements;

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
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
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
    public void setSuperiorCategory(Long superiorCategory) 
    {
        this.superiorCategory = superiorCategory;
    }

    public Long getSuperiorCategory() 
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
    public void setCompany(Long company) 
    {
        this.company = company;
    }

    public Long getCompany() 
    {
        return company;
    }
    public void setMeteringUnit(Long meteringUnit) 
    {
        this.meteringUnit = meteringUnit;
    }

    public Long getMeteringUnit() 
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
    public void setIntroductionRequirements(Long introductionRequirements) 
    {
        this.introductionRequirements = introductionRequirements;
    }

    public Long getIntroductionRequirements() 
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
            .append("company", getCompany())
            .append("meteringUnit", getMeteringUnit())
            .append("sourceSystem", getSourceSystem())
            .append("introductionRequirements", getIntroductionRequirements())
            .append("assignPurchaser", getAssignPurchaser())
            .toString();
    }
}
