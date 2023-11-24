package com.hh.pms.mast.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 银行类型对象 bank_type
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public class BankType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 银行类型ID */
    private Long bankTypeId;

    /** 银行类型 */
    @Excel(name = "银行类型")
    private String bankType;

    public void setBankTypeId(Long bankTypeId)
    {
        this.bankTypeId = bankTypeId;
    }

    public Long getBankTypeId()
    {
        return bankTypeId;
    }
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }

    public String getBankType()
    {
        return bankType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bankTypeId", getBankTypeId())
            .append("bankType", getBankType())
            .toString();
    }
}
