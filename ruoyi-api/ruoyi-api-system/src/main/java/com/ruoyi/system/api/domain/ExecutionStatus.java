package com.ruoyi.system.api.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 签署执行状态表对象 execution_status
 *
 * @author ruoyi
 * @date 2023-12-18
 */
public class ExecutionStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 签署执行状态Id */
    private Long executionId;

    /** 签署方数 */
    @Excel(name = "签署方数")
    private Long signatories;

    /** 我方主体 */
    @Excel(name = "我方主体")
    private String ourEntity;

    /** 乙方供应商 */
    @Excel(name = "乙方供应商")
    private String supplierB;

    /** 乙方联系人 */
    @Excel(name = "乙方联系人")
    private String personB;

    /** 乙方联系方式 */
    @Excel(name = "乙方联系方式")
    private String phoneB;

    /** 乙方开户行 */
    @Excel(name = "乙方开户行")
    private String bankB;

    /** 乙方开户行账号 */
    @Excel(name = "乙方开户行账号")
    private String accountOpeningB;

    /** 合同方金额 */
    @Excel(name = "合同方金额")
    private BigDecimal contractAmount;

    /** 币别 */
    @Excel(name = "币别")
    private String currencyName;

    /** 已支付金额 */
    @Excel(name = "已支付金额")
    private BigDecimal paidAmount;

    /** 锁定金额 */
    @Excel(name = "锁定金额")
    private BigDecimal lockInAmount;

    /** 剩余金额 */
    @Excel(name = "剩余金额")
    private BigDecimal remainingAmount;

    /** 合同Id */
    @Excel(name = "合同Id")
    private Long contractManagementId;


    @Excel(name = "双方")
    private Long bothSides;

    public void setExecutionId(Long executionId)
    {
        this.executionId = executionId;
    }

    public Long getExecutionId()
    {
        return executionId;
    }
    public void setSignatories(Long signatories)
    {
        this.signatories = signatories;
    }

    public Long getSignatories()
    {
        return signatories;
    }
    public void setOurEntity(String ourEntity)
    {
        this.ourEntity = ourEntity;
    }

    public String getOurEntity()
    {
        return ourEntity;
    }
    public void setSupplierB(String supplierB)
    {
        this.supplierB = supplierB;
    }

    public String getSupplierB()
    {
        return supplierB;
    }
    public void setPersonB(String personB)
    {
        this.personB = personB;
    }

    public String getPersonB()
    {
        return personB;
    }
    public void setPhoneB(String phoneB)
    {
        this.phoneB = phoneB;
    }

    public String getPhoneB()
    {
        return phoneB;
    }
    public void setBankB(String bankB)
    {
        this.bankB = bankB;
    }

    public String getBankB()
    {
        return bankB;
    }
    public void setAccountOpeningB(String accountOpeningB)
    {
        this.accountOpeningB = accountOpeningB;
    }

    public String getAccountOpeningB()
    {
        return accountOpeningB;
    }
    public void setContractAmount(BigDecimal contractAmount)
    {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getContractAmount()
    {
        return contractAmount;
    }
    public void setCurrencyName(String currencyName)
    {
        this.currencyName = currencyName;
    }

    public String getCurrencyName()
    {
        return currencyName;
    }
    public void setPaidAmount(BigDecimal paidAmount)
    {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getPaidAmount()
    {
        return paidAmount;
    }
    public void setLockInAmount(BigDecimal lockInAmount)
    {
        this.lockInAmount = lockInAmount;
    }

    public BigDecimal getLockInAmount()
    {
        return lockInAmount;
    }
    public void setRemainingAmount(BigDecimal remainingAmount)
    {
        this.remainingAmount = remainingAmount;
    }

    public BigDecimal getRemainingAmount()
    {
        return remainingAmount;
    }
    public void setContractManagementId(Long contractManagementId)
    {
        this.contractManagementId = contractManagementId;
    }

    public Long getContractManagementId()
    {
        return contractManagementId;
    }

    public Long getBothSides() {
        return bothSides;
    }

    public void setBothSides(Long bothSides) {
        this.bothSides = bothSides;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("executionId", getExecutionId())
                .append("signatories", getSignatories())
                .append("ourEntity", getOurEntity())
                .append("supplierB", getSupplierB())
                .append("personB", getPersonB())
                .append("phoneB", getPhoneB())
                .append("bankB", getBankB())
                .append("accountOpeningB", getAccountOpeningB())
                .append("contractAmount", getContractAmount())
                .append("currencyName", getCurrencyName())
                .append("paidAmount", getPaidAmount())
                .append("lockInAmount", getLockInAmount())
                .append("remainingAmount", getRemainingAmount())
                .append("contractManagementId", getContractManagementId())
                .toString();
    }
}
