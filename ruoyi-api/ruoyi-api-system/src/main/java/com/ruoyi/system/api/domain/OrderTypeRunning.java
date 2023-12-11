package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单类型对象 order_type_running
 *
 * @author ruoyi
 * @date 2023-11-27
 */
public class OrderTypeRunning extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单类型id */
    private Long ortId;

    /** 订单类型名称 */
    @Excel(name = "订单类型名称")
    private String ortName;

    public void setOrtId(Long ortId)
    {
        this.ortId = ortId;
    }

    public Long getOrtId()
    {
        return ortId;
    }
    public void setOrtName(String ortName)
    {
        this.ortName = ortName;
    }

    public String getOrtName()
    {
        return ortName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("ortId", getOrtId())
                .append("ortName", getOrtName())
                .toString();
    }
}
