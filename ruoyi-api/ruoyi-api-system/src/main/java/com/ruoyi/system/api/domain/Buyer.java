package com.ruoyi.system.api.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 采购员对象 buyer
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public class Buyer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购员Id */
    private Long purchaserId;

    /** 安全级别 */
    @Excel(name = "安全级别")
    private Long securityLevel;

    /** LOGINID */
    @Excel(name = "LOGINID")
    private String loginId;

    /** 登录名 */
    @Excel(name = "登录名")
    private String loginName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    public void setPurchaserId(Long purchaserId) 
    {
        this.purchaserId = purchaserId;
    }

    public Long getPurchaserId() 
    {
        return purchaserId;
    }
    public void setSecurityLevel(Long securityLevel) 
    {
        this.securityLevel = securityLevel;
    }

    public Long getSecurityLevel() 
    {
        return securityLevel;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getLoginName() 
    {
        return loginName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("purchaserId", getPurchaserId())
            .append("securityLevel", getSecurityLevel())
            .append("LOGINID", getLoginId())
            .append("loginName", getLoginName())
            .append("sex", getSex())
            .toString();
    }
}
