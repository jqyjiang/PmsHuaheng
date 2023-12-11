package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 银行对象 bank
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public class Bank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 银行序号 */
    private Long bankId;

    /** 银行编码 */
    @Excel(name = "银行编码")
    private String bankCode;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bankName;

    /** 银行简称 */
    @Excel(name = "银行简称")
    private String bankAbbreviation;

    /** 银行类型 */
    @Excel(name = "银行类型")
    private Long bankType;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Long enable;

    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public Long getBankId()
    {
        return bankId;
    }
    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }

    public String getBankCode()
    {
        return bankCode;
    }
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankName()
    {
        return bankName;
    }
    public void setBankAbbreviation(String bankAbbreviation)
    {
        this.bankAbbreviation = bankAbbreviation;
    }

    public String getBankAbbreviation()
    {
        return bankAbbreviation;
    }
    public void setBankType(Long bankType)
    {
        this.bankType = bankType;
    }

    public Long getBankType()
    {
        return bankType;
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
            .append("bankId", getBankId())
            .append("bankCode", getBankCode())
            .append("bankName", getBankName())
            .append("bankAbbreviation", getBankAbbreviation())
            .append("bankType", getBankType())
            .append("enable", getEnable())
            .toString();
    }
}
