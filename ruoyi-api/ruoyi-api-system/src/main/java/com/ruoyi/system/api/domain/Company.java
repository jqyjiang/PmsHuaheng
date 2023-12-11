package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司表对象 companies
 *
 * @author ruoyi
 * @date 2023-12-05
 */
public class Company extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公司ID */
    private Long companiesId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String companiesName;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String creditCode;

    /** 公司简称 */
    @Excel(name = "公司简称")
    private String companiesAbbreviation;

    /** 是否集团 */
    @Excel(name = "是否集团")
    private Long isgroup;

    /** 公司编码 */
    @Excel(name = "公司编码")
    private String companiesCode;

    /** 企业注册登记号 */
    @Excel(name = "企业注册登记号")
    private String registrationNumber;

    /** 企业类型 */
    @Excel(name = "企业类型")
    private String enterpriseType;

    /** 注册国家 */
    @Excel(name = "注册国家")
    private String registeredCountry;

    /** 法定代表人 */
    @Excel(name = "法定代表人")
    private String representative;

    /** 注册资本（万） */
    @Excel(name = "注册资本", readConverterExp = "万=")
    private BigDecimal registeredCapital;

    /** 成立日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date establishmentDate;

    /** 经营期限 */
    @Excel(name = "经营期限")
    private BigDecimal operatingPeriod;

    /** 对应基础分部 */
    @Excel(name = "对应基础分部")
    private String basicDivision;

    /** 上传营业执照 */
    @Excel(name = "上传营业执照")
    private String businessLicense;

    /** 纳税人标识 */
    @Excel(name = "纳税人标识")
    private String taxpayer;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** 币种ID */
    @Excel(name = "币种ID")
    private Long currencyId2;

    /** 邓白氏编码 */
    @Excel(name = "邓白氏编码")
    private String dengCode;

    /** 公司简介 */
    @Excel(name = "公司简介")
    private String profile;

    public void setCompaniesId(Long companiesId)
    {
        this.companiesId = companiesId;
    }

    public Long getCompaniesId()
    {
        return companiesId;
    }
    public void setCompaniesName(String companiesName)
    {
        this.companiesName = companiesName;
    }

    public String getCompaniesName()
    {
        return companiesName;
    }
    public void setCreditCode(String creditCode)
    {
        this.creditCode = creditCode;
    }

    public String getCreditCode()
    {
        return creditCode;
    }
    public void setCompaniesAbbreviation(String companiesAbbreviation)
    {
        this.companiesAbbreviation = companiesAbbreviation;
    }

    public String getCompaniesAbbreviation()
    {
        return companiesAbbreviation;
    }
    public void setIsgroup(Long isgroup)
    {
        this.isgroup = isgroup;
    }

    public Long getIsgroup()
    {
        return isgroup;
    }
    public void setCompaniesCode(String companiesCode)
    {
        this.companiesCode = companiesCode;
    }

    public String getCompaniesCode()
    {
        return companiesCode;
    }
    public void setRegistrationNumber(String registrationNumber)
    {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber()
    {
        return registrationNumber;
    }
    public void setEnterpriseType(String enterpriseType)
    {
        this.enterpriseType = enterpriseType;
    }

    public String getEnterpriseType()
    {
        return enterpriseType;
    }
    public void setRegisteredCountry(String registeredCountry)
    {
        this.registeredCountry = registeredCountry;
    }

    public String getRegisteredCountry()
    {
        return registeredCountry;
    }
    public void setRepresentative(String representative)
    {
        this.representative = representative;
    }

    public String getRepresentative()
    {
        return representative;
    }
    public void setRegisteredCapital(BigDecimal registeredCapital)
    {
        this.registeredCapital = registeredCapital;
    }

    public BigDecimal getRegisteredCapital()
    {
        return registeredCapital;
    }
    public void setEstablishmentDate(Date establishmentDate)
    {
        this.establishmentDate = establishmentDate;
    }

    public Date getEstablishmentDate()
    {
        return establishmentDate;
    }
    public void setOperatingPeriod(BigDecimal operatingPeriod)
    {
        this.operatingPeriod = operatingPeriod;
    }

    public BigDecimal getOperatingPeriod()
    {
        return operatingPeriod;
    }
    public void setBasicDivision(String basicDivision)
    {
        this.basicDivision = basicDivision;
    }

    public String getBasicDivision()
    {
        return basicDivision;
    }
    public void setBusinessLicense(String businessLicense)
    {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense()
    {
        return businessLicense;
    }
    public void setTaxpayer(String taxpayer)
    {
        this.taxpayer = taxpayer;
    }

    public String getTaxpayer()
    {
        return taxpayer;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvince()
    {
        return province;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setCurrencyId2(Long currencyId2)
    {
        this.currencyId2 = currencyId2;
    }

    public Long getCurrencyId2()
    {
        return currencyId2;
    }
    public void setDengCode(String dengCode)
    {
        this.dengCode = dengCode;
    }

    public String getDengCode()
    {
        return dengCode;
    }
    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getProfile()
    {
        return profile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("companiesId", getCompaniesId())
            .append("companiesName", getCompaniesName())
            .append("creditCode", getCreditCode())
            .append("companiesAbbreviation", getCompaniesAbbreviation())
            .append("isgroup", getIsgroup())
            .append("companiesCode", getCompaniesCode())
            .append("registrationNumber", getRegistrationNumber())
            .append("enterpriseType", getEnterpriseType())
            .append("registeredCountry", getRegisteredCountry())
            .append("representative", getRepresentative())
            .append("registeredCapital", getRegisteredCapital())
            .append("establishmentDate", getEstablishmentDate())
            .append("operatingPeriod", getOperatingPeriod())
            .append("basicDivision", getBasicDivision())
            .append("businessLicense", getBusinessLicense())
            .append("taxpayer", getTaxpayer())
            .append("province", getProvince())
            .append("address", getAddress())
            .append("currencyId2", getCurrencyId2())
            .append("dengCode", getDengCode())
            .append("profile", getProfile())
            .toString();
    }
}
