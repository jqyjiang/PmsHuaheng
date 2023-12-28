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

    /** 品类编码 */
    @Excel(name = "品类编码")
    private String categoryCode;

    /** 供货id */
    @Excel(name = "供货id")
    private Long supplyId;

    /** 考察id */
    @Excel(name = "考察id")
    private Long soiId;

    /** 送样id */
    @Excel(name = "送样id")
    private Long sampleId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料名称 */
    @Excel(name = "品类名称")
    private String categoryName;

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

    public Long getSoiId() {
        return soiId;
    }

    public void setSoiId(Long soiId) {
        this.soiId = soiId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    @Override
    public String toString() {
        return "SupplierMaterialList{" +
                "supplierMaterialId=" + supplierMaterialId +
                ", materialCode='" + materialCode + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", supplyId=" + supplyId +
                ", soiId=" + soiId +
                ", sampleId=" + sampleId +
                ", materialName='" + materialName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", manufacturer=" + manufacturer +
                ", supplyCapacity='" + supplyCapacity + '\'' +
                ", notes='" + notes + '\'' +
                ", upload='" + upload + '\'' +
                '}';
    }
}
