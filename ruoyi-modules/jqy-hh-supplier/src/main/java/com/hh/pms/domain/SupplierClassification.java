package com.hh.pms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 供应商分类定义对象 supplier_classification
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
public class SupplierClassification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类id */
    private Long classId;

    /** 分类编码 */
    @Excel(name = "分类编码")
    private String classCode;

    /** 分类描述 */
    @Excel(name = "分类描述")
    private String classDescribe;

    public void setClassId(Long classId) 
    {
        this.classId = classId;
    }

    public Long getClassId() 
    {
        return classId;
    }
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classId", getClassId())
            .append("classCode", getClassCode())
            .append("classDescribe", getClassDescribe())
            .toString();
    }
}
