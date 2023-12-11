package com.ruoyi.system.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 供应商列表对象 supplier_details
 *
 * @author ruoyi
 * @date 2023-11-28
 */
public class SupplierDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 供应商id */
    private Long sdId;

    /** 邀请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "邀请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sdTime;

    /** 供应商详细编码 */
    @Excel(name = "供应商详细编码")
    private String sdCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String sbiName;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String sdUscc;

    /** 简称 */
    @Excel(name = "简称")
    private String sbiAbbreviation;

    /** 服务范围 */
    @Excel(name = "服务范围")
    private String sbiServe;

    /** 行业 */
    @Excel(name = "行业")
    private String sdIndustry;

    /** 企业性质 */
    @Excel(name = "企业性质")
    private String sdEn;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sdRt;

    /** 负责人 */
    @Excel(name = "负责人")
    private String sdHead;

    /** 负责人手机 */
    @Excel(name = "负责人手机")
    private String sdHeadPhone;

    /** 主要联系人姓名 */
    @Excel(name = "主要联系人姓名")
    private String sdPcn;

    /** 主要联系人邮箱 */
    @Excel(name = "主要联系人邮箱")
    private String sdPce;

    /** 主要联系人手机号 */
    @Excel(name = "主要联系人手机号")
    private String sdPcp;

    /** 主要联系人性别 */
    @Excel(name = "主要联系人性别")
    private String sdPcg;

    /** 主要联系人职务 */
    @Excel(name = "主要联系人职务")
    private String sdMct;

    /** 国家地区 */
    @Excel(name = "国家地区")
    private String sdCountry;

    /** 所在区域 */
    @Excel(name = "所在区域")
    private String sdRegion;

    /** 供应商分类 */
    @Excel(name = "供应商分类")
    private String sdClass;

    /** 网址 */
    @Excel(name = "网址")
    private String sdWebsite;

    /** 简介 */
    @Excel(name = "简介")
    private String sdBrief;

    /** 法定代表 */
    @Excel(name = "法定代表")
    private String sdLegal;

    /** 注册信息 */
    @Excel(name = "注册信息")
    private String sdRi;

    /** 工商注册号 */
    @Excel(name = "工商注册号")
    private String sdBr;

    /** 注册资本 */
    @Excel(name = "注册资本")
    private String sdRc;

    /** 单位状态 */
    @Excel(name = "单位状态")
    private String sdStatus;

    /** 单位电话 */
    @Excel(name = "单位电话")
    private String sdPhone;

    /** 单位邮箱 */
    @Excel(name = "单位邮箱")
    private String sdEmail;

    /** 传真 */
    @Excel(name = "传真")
    private String sdFax;

    /** 省 */
    @Excel(name = "省")
    private String sdSave;

    /** 市 */
    @Excel(name = "市")
    private String sdCity;

    /** 区(县) */
    @Excel(name = "区(县)")
    private String sdCounty;

    /** 结算方式 */
    @Excel(name = "结算方式")
    private String sdSm;

    /** 开户行 */
    @Excel(name = "开户行")
    private String sdBank;

    /** 币种 */
    @Excel(name = "币种")
    private String sdCurrency;

    /** 账号 */
    @Excel(name = "账号")
    private String sdAccount;

    /** 证件编码 */
    @Excel(name = "证件编码")
    private String idCode;

    /** 证件名称 */
    @Excel(name = "证件名称")
    private String certificateName;

    /** 存档日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "存档日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date filingDate;

    /** 文件 */
    @Excel(name = "文件")
    private String filing;

    /** 数据状态 */
    @Excel(name = "数据状态")
    private String dataTatus;


    /** 分类编码 */
    @Excel(name = "分类编码")
    private String classCode;

    /** 分类描述 */
    @Excel(name = "分类描述")
    private String classDescribe;



    /** 生命周期id */
    @Excel(name = "生命周期id")
    private Long lifecycleId;

    /** 生命周期 */
    @Excel(name = "生命周期")
    private String cycle;

    /** 升降级状态 */
    @Excel(name = "升降级状态")
    private Long slStatus;
    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public String getClassCode()
    {
        return classCode;
    }
    public void setClassDescribe(String classDescribe)
    {
        this.classDescribe = classDescribe;
    }

    public String getClassDescribe()
    {
        return classDescribe;
    }

    public void setSdId(Long sdId)
    {
        this.sdId = sdId;
    }

    public Long getSdId()
    {
        return sdId;
    }
    public void setSdTime(Date sdTime)
    {
        this.sdTime = sdTime;
    }

    public Date getSdTime()
    {
        return sdTime;
    }
    public void setSdCode(String sdCode)
    {
        this.sdCode = sdCode;
    }

    public String getSdCode()
    {
        return sdCode;
    }
    public void setSbiName(String sbiName)
    {
        this.sbiName = sbiName;
    }

    public String getSbiName()
    {
        return sbiName;
    }
    public void setSdUscc(String sdUscc)
    {
        this.sdUscc = sdUscc;
    }

    public String getSdUscc()
    {
        return sdUscc;
    }
    public void setSbiAbbreviation(String sbiAbbreviation)
    {
        this.sbiAbbreviation = sbiAbbreviation;
    }

    public String getSbiAbbreviation()
    {
        return sbiAbbreviation;
    }
    public void setSbiServe(String sbiServe)
    {
        this.sbiServe = sbiServe;
    }

    public String getSbiServe()
    {
        return sbiServe;
    }
    public void setSdIndustry(String sdIndustry)
    {
        this.sdIndustry = sdIndustry;
    }

    public String getSdIndustry()
    {
        return sdIndustry;
    }
    public void setSdEn(String sdEn)
    {
        this.sdEn = sdEn;
    }

    public String getSdEn()
    {
        return sdEn;
    }
    public void setSdRt(Date sdRt)
    {
        this.sdRt = sdRt;
    }

    public Date getSdRt()
    {
        return sdRt;
    }
    public void setSdHead(String sdHead)
    {
        this.sdHead = sdHead;
    }

    public String getSdHead()
    {
        return sdHead;
    }
    public void setSdHeadPhone(String sdHeadPhone)
    {
        this.sdHeadPhone = sdHeadPhone;
    }

    public String getSdHeadPhone()
    {
        return sdHeadPhone;
    }
    public void setSdPcn(String sdPcn)
    {
        this.sdPcn = sdPcn;
    }

    public String getSdPcn()
    {
        return sdPcn;
    }
    public void setSdPce(String sdPce)
    {
        this.sdPce = sdPce;
    }

    public String getSdPce()
    {
        return sdPce;
    }
    public void setSdPcp(String sdPcp)
    {
        this.sdPcp = sdPcp;
    }

    public String getSdPcp()
    {
        return sdPcp;
    }
    public void setSdPcg(String sdPcg)
    {
        this.sdPcg = sdPcg;
    }

    public String getSdPcg()
    {
        return sdPcg;
    }
    public void setSdMct(String sdMct)
    {
        this.sdMct = sdMct;
    }

    public String getSdMct()
    {
        return sdMct;
    }
    public void setSdCountry(String sdCountry)
    {
        this.sdCountry = sdCountry;
    }

    public String getSdCountry()
    {
        return sdCountry;
    }
    public void setSdRegion(String sdRegion)
    {
        this.sdRegion = sdRegion;
    }

    public String getSdRegion()
    {
        return sdRegion;
    }
    public void setSdClass(String sdClass)
    {
        this.sdClass = sdClass;
    }

    public String getSdClass()
    {
        return sdClass;
    }
    public void setSdWebsite(String sdWebsite)
    {
        this.sdWebsite = sdWebsite;
    }

    public String getSdWebsite()
    {
        return sdWebsite;
    }
    public void setSdBrief(String sdBrief)
    {
        this.sdBrief = sdBrief;
    }

    public String getSdBrief()
    {
        return sdBrief;
    }
    public void setSdLegal(String sdLegal)
    {
        this.sdLegal = sdLegal;
    }

    public String getSdLegal()
    {
        return sdLegal;
    }
    public void setSdRi(String sdRi)
    {
        this.sdRi = sdRi;
    }

    public String getSdRi()
    {
        return sdRi;
    }
    public void setSdBr(String sdBr)
    {
        this.sdBr = sdBr;
    }

    public String getSdBr()
    {
        return sdBr;
    }
    public void setSdRc(String sdRc)
    {
        this.sdRc = sdRc;
    }

    public String getSdRc()
    {
        return sdRc;
    }
    public void setSdStatus(String sdStatus)
    {
        this.sdStatus = sdStatus;
    }

    public String getSdStatus()
    {
        return sdStatus;
    }
    public void setSdPhone(String sdPhone)
    {
        this.sdPhone = sdPhone;
    }

    public String getSdPhone()
    {
        return sdPhone;
    }
    public void setSdEmail(String sdEmail)
    {
        this.sdEmail = sdEmail;
    }

    public String getSdEmail()
    {
        return sdEmail;
    }
    public void setSdFax(String sdFax)
    {
        this.sdFax = sdFax;
    }

    public String getSdFax()
    {
        return sdFax;
    }
    public void setSdSave(String sdSave)
    {
        this.sdSave = sdSave;
    }

    public String getSdSave()
    {
        return sdSave;
    }
    public void setSdCity(String sdCity)
    {
        this.sdCity = sdCity;
    }

    public String getSdCity()
    {
        return sdCity;
    }
    public void setSdCounty(String sdCounty)
    {
        this.sdCounty = sdCounty;
    }

    public String getSdCounty()
    {
        return sdCounty;
    }
    public void setSdSm(String sdSm)
    {
        this.sdSm = sdSm;
    }

    public String getSdSm()
    {
        return sdSm;
    }
    public void setSdBank(String sdBank)
    {
        this.sdBank = sdBank;
    }

    public String getSdBank()
    {
        return sdBank;
    }
    public void setSdCurrency(String sdCurrency)
    {
        this.sdCurrency = sdCurrency;
    }

    public String getSdCurrency()
    {
        return sdCurrency;
    }
    public void setSdAccount(String sdAccount)
    {
        this.sdAccount = sdAccount;
    }

    public String getSdAccount()
    {
        return sdAccount;
    }
    public void setIdCode(String idCode)
    {
        this.idCode = idCode;
    }

    public String getIdCode()
    {
        return idCode;
    }
    public void setCertificateName(String certificateName)
    {
        this.certificateName = certificateName;
    }

    public String getCertificateName()
    {
        return certificateName;
    }
    public void setFilingDate(Date filingDate)
    {
        this.filingDate = filingDate;
    }

    public Date getFilingDate()
    {
        return filingDate;
    }
    public void setFiling(String filing)
    {
        this.filing = filing;
    }

    public String getFiling()
    {
        return filing;
    }
    public void setDataTatus(String dataTatus)
    {
        this.dataTatus = dataTatus;
    }

    public String getDataTatus()
    {
        return dataTatus;
    }
    public Long getLifecycleId() {
        return lifecycleId;
    }

    public void setLifecycleId(Long lifecycleId) {
        this.lifecycleId = lifecycleId;
    }

    public Long getSlStatus() {
        return slStatus;
    }

    public void setSlStatus(Long slStatus) {
        this.slStatus = slStatus;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("sdId", getSdId())
                .append("sdTime", getSdTime())
                .append("sdCode", getSdCode())
                .append("sbiName", getSbiName())
                .append("sdUscc", getSdUscc())
                .append("sbiAbbreviation", getSbiAbbreviation())
                .append("sbiServe", getSbiServe())
                .append("sdIndustry", getSdIndustry())
                .append("sdEn", getSdEn())
                .append("sdRt", getSdRt())
                .append("sdHead", getSdHead())
                .append("sdHeadPhone", getSdHeadPhone())
                .append("sdPcn", getSdPcn())
                .append("sdPce", getSdPce())
                .append("sdPcp", getSdPcp())
                .append("sdPcg", getSdPcg())
                .append("sdMct", getSdMct())
                .append("sdCountry", getSdCountry())
                .append("sdRegion", getSdRegion())
                .append("sdClass", getSdClass())
                .append("sdWebsite", getSdWebsite())
                .append("sdBrief", getSdBrief())
                .append("sdLegal", getSdLegal())
                .append("sdRi", getSdRi())
                .append("sdBr", getSdBr())
                .append("sdRc", getSdRc())
                .append("sdStatus", getSdStatus())
                .append("sdPhone", getSdPhone())
                .append("sdEmail", getSdEmail())
                .append("sdFax", getSdFax())
                .append("sdSave", getSdSave())
                .append("sdCity", getSdCity())
                .append("sdCounty", getSdCounty())
                .append("sdSm", getSdSm())
                .append("sdBank", getSdBank())
                .append("sdCurrency", getSdCurrency())
                .append("sdAccount", getSdAccount())
                .append("idCode", getIdCode())
                .append("certificateName", getCertificateName())
                .append("filingDate", getFilingDate())
                .append("filing", getFiling())
                .append("dataTatus", getDataTatus())
                .append("classCode", getClassCode())
                .append("classDescribe", getClassDescribe())
                .toString();
    }
}
