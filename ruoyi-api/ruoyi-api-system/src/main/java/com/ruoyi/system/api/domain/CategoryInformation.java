package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 品类信息对象 category_information
 * 
 * @author ruoyi
 * @date 2023-11-29
 */
public class CategoryInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 品类序号 */
    private Long categoryId;

    /** 品类代码 */
    @Excel(name = "品类代码")
    private String categoryCode;

    /** 品类名称 */
    @Excel(name = "品类名称")
    private Long categoryName;

    /** 是否主品类 */
    @Excel(name = "是否主品类")
    private Long isHost;

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
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
    public void setIsHost(Long isHost) 
    {
        this.isHost = isHost;
    }

    public Long getIsHost() 
    {
        return isHost;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("categoryCode", getCategoryCode())
            .append("categoryName", getCategoryName())
            .append("isHost", getIsHost())
            .toString();
    }
}
