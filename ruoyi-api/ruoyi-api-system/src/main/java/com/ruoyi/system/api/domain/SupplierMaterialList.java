package com.ruoyi.system.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * 供应商物料对象 supplier_material_list
 *
 * @author ruoyi
 * @date 2023-12-12
 */
public class SupplierMaterialList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 供应商物料id */
    private Long supplierMaterialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 供货id */
    @Excel(name = "供货id")
    private Long supplyId;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private Long manufacturer;

    /** 供货能力评级 */
    @Excel(name = "供货能力评级")
    private String supplyCapacity;

    /** 备注 */
    @Excel(name = "备注")
    private String notes;

    /** 文件上传 */
    @Excel(name = "文件上传")
    private String upload;

    /** 采购需求池信息 */
    private List<MaterialInformation> materialInformationList;

    public List<MaterialInformation> getMaterialInformationList() {
        return materialInformationList;
    }

    public void setMaterialInformationList(List<MaterialInformation> materialInformationList) {
        this.materialInformationList = materialInformationList;
    }

    public void setSupplierMaterialId(Long supplierMaterialId)
    {
        this.supplierMaterialId = supplierMaterialId;
    }

    public Long getSupplierMaterialId()
    {
        return supplierMaterialId;
    }
    public void setMaterialCode(String materialCode)
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode()
    {
        return materialCode;
    }
    public void setSupplyId(Long supplyId)
    {
        this.supplyId = supplyId;
    }

    public Long getSupplyId()
    {
        return supplyId;
    }
    public void setManufacturer(Long manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public Long getManufacturer()
    {
        return manufacturer;
    }
    public void setSupplyCapacity(String supplyCapacity)
    {
        this.supplyCapacity = supplyCapacity;
    }

    public String getSupplyCapacity()
    {
        return supplyCapacity;
    }
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getNotes()
    {
        return notes;
    }
    public void setUpload(String upload)
    {
        this.upload = upload;
    }

    public String getUpload()
    {
        return upload;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("supplierMaterialId", getSupplierMaterialId())
                .append("materialCode", getMaterialCode())
                .append("supplyId", getSupplyId())
                .append("manufacturer", getManufacturer())
                .append("supplyCapacity", getSupplyCapacity())
                .append("notes", getNotes())
                .append("upload", getUpload())
                .append("materialInformationList", getMaterialInformationList())
                .toString();
    }
}
