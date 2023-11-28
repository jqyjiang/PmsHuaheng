package com.hh.pms.mast.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 币种对象 currency
 *
 * @author ruoyi
 * @date 2023-11-27
 */
public class Currency extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 币种ID */
    private Long currencyId;

    /** 币种编码 */
    @Excel(name = "币种编码")
    private String currencyCode;

    /** 币种名称 */
    @Excel(name = "币种名称")
    private String currencyName;

    /** 国家/地区 */
    @Excel(name = "国家/地区")
    private String countryRegion;

    /** 财务精度 */
    @Excel(name = "财务精度")
    private String financialAccuracy;

    /** 精度 */
    @Excel(name = "精度")
    private String accuracy;

    /** 货币符号 */
    @Excel(name = "货币符号")
    private String currencySymbol;

    /** 启用(1:代表启动,0:代表停用) */
    @Excel(name = "启用(1:代表启动,0:代表停用)")
    private Long enable;

    public void setCurrencyId(Long currencyId)
    {
        this.currencyId = currencyId;
    }

    public Long getCurrencyId()
    {
        return currencyId;
    }
    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }
    public void setCurrencyName(String currencyName)
    {
        this.currencyName = currencyName;
    }

    public String getCurrencyName()
    {
        return currencyName;
    }
    public void setCountryRegion(String countryRegion)
    {
        this.countryRegion = countryRegion;
    }

    public String getCountryRegion()
    {
        return countryRegion;
    }
    public void setFinancialAccuracy(String financialAccuracy)
    {
        this.financialAccuracy = financialAccuracy;
    }

    public String getFinancialAccuracy()
    {
        return financialAccuracy;
    }
    public void setAccuracy(String accuracy)
    {
        this.accuracy = accuracy;
    }

    public String getAccuracy()
    {
        return accuracy;
    }
    public void setCurrencySymbol(String currencySymbol)
    {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbol()
    {
        return currencySymbol;
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
            .append("currencyId", getCurrencyId())
            .append("currencyCode", getCurrencyCode())
            .append("currencyName", getCurrencyName())
            .append("countryRegion", getCountryRegion())
            .append("financialAccuracy", getFinancialAccuracy())
            .append("accuracy", getAccuracy())
            .append("currencySymbol", getCurrencySymbol())
            .append("enable", getEnable())
            .toString();
    }
}
