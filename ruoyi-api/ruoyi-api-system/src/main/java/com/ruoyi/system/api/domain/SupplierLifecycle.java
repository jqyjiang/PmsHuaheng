package com.ruoyi.system.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 生命周期维护对象 supplier_lifecycle
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public class SupplierLifecycle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 生命周期id */
    private Long lifecycleId;

    /** 生命周期 */
    @Excel(name = "生命周期")
    private String cycle;

    /** 加入黑名单 */
    @Excel(name = "加入黑名单")
    private Long blacklisted;

    /** 优先等级 */
    @Excel(name = "优先等级")
    private Long priorityLevel;
    public void setLifecycleId(Long lifecycleId) 
    {
        this.lifecycleId = lifecycleId;
    }

    public Long getLifecycleId() 
    {
        return lifecycleId;
    }
    public void setCycle(String cycle) 
    {
        this.cycle = cycle;
    }

    public String getCycle() 
    {
        return cycle;
    }
    public void setBlacklisted(Long blacklisted) 
    {
        this.blacklisted = blacklisted;
    }

    public Long getBlacklisted() 
    {
        return blacklisted;
    }

    public Long getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Long PriorityLevel) {
        this.priorityLevel = PriorityLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("lifecycleId", getLifecycleId())
            .append("cycle", getCycle())
            .append("priorityLevel", getPriorityLevel())
            .toString();
    }
}
