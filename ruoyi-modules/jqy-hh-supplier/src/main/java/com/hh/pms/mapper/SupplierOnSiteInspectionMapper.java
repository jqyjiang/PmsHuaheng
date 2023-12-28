package com.hh.pms.mapper;

import java.util.List;
import com.ruoyi.system.api.domain.SupplierMaterialList;
import com.ruoyi.system.api.domain.SupplierOnSiteInspection;

/**
 * 现场考察Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-15
 */
public interface SupplierOnSiteInspectionMapper 
{
    /**
     * 查询现场考察
     * 
     * @param soiId 现场考察主键
     * @return 现场考察
     */
    public SupplierOnSiteInspection selectSupplierOnSiteInspectionBySoiId(Long soiId);

    /**
     * 查询现场考察列表
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 现场考察集合
     */
    public List<SupplierOnSiteInspection> selectSupplierOnSiteInspectionList(SupplierOnSiteInspection supplierOnSiteInspection);

    /**
     * 新增现场考察
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 结果
     */
    public int insertSupplierOnSiteInspection(SupplierOnSiteInspection supplierOnSiteInspection);

    /**
     * 修改现场考察
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 结果
     */
    public int updateSupplierOnSiteInspection(SupplierOnSiteInspection supplierOnSiteInspection);

    /**
     * 删除现场考察
     * 
     * @param soiId 现场考察主键
     * @return 结果
     */
    public int deleteSupplierOnSiteInspectionBySoiId(Long soiId);

    /**
     * 批量删除现场考察
     * 
     * @param soiIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierOnSiteInspectionBySoiIds(Long[] soiIds);

    /**
     * 批量删除供应商物料
     * 
     * @param soiIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierMaterialListBySoiIds(Long[] soiIds);
    
    /**
     * 批量新增供应商物料
     * 
     * @param supplierMaterialListList 供应商物料列表
     * @return 结果
     */
    public int batchSupplierMaterialList(List<SupplierMaterialList> supplierMaterialListList);
    

    /**
     * 通过现场考察主键删除供应商物料信息
     * 
     * @param soiId 现场考察ID
     * @return 结果
     */
    public int deleteSupplierMaterialListBySoiId(Long soiId);


}
