package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 税率对象 tax_rate
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public class TaxRate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 税种序号 */
    private Long taxTypeId;

    /** 税种代码 */
    @Excel(name = "税种代码")
    private String taxCode;

    /** 描述 */
    @Excel(name = "描述")
    private String describes;

    /** 税率 */
    @Excel(name = "税率")
    private String taxRate;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long enable;

    public void setTaxTypeId(Long taxTypeId)
    {
        this.taxTypeId = taxTypeId;
    }

    public Long getTaxTypeId()
    {
        return taxTypeId;
    }
    public void setTaxCode(String taxCode)
    {
        this.taxCode = taxCode;
    }

    public String getTaxCode()
    {
        return taxCode;
    }
    public void setDescribes(String describes)
    {
        this.describes = describes;
    }

    public String getDescribes()
    {
        return describes;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public void setEnable(Long enable)
    {
        this.enable = enable;
    }

    public Long getEnable()
    {
        return enable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taxTypeId", getTaxTypeId())
            .append("taxCode", getTaxCode())
            .append("describes", getDescribes())
            .append("taxRate", getTaxRate())
            .append("enable", getEnable())
            .toString();
    }
}
