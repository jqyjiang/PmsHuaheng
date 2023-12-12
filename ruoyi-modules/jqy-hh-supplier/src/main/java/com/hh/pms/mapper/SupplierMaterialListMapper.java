package com.hh.pms.mapper;

import java.util.List;

import com.ruoyi.system.api.domain.SupplierMaterialList;

/**
 * 供应商物料Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface SupplierMaterialListMapper 
{
    /**
     * 查询供应商物料
     * 
     * @param supplierMaterialId 供应商物料主键
     * @return 供应商物料
     */
    public SupplierMaterialList selectSupplierMaterialListBySupplierMaterialId(Long supplierMaterialId);

    /**
     * 查询供应商物料列表
     * 
     * @param supplierMaterialList 供应商物料
     * @return 供应商物料集合
     */
    public List<SupplierMaterialList> selectSupplierMaterialListList(SupplierMaterialList supplierMaterialList);

    /**
     * 新增供应商物料
     * 
     * @param supplierMaterialList 供应商物料
     * @return 结果
     */
    public int insertSupplierMaterialList(SupplierMaterialList supplierMaterialList);

    /**
     * 修改供应商物料
     * 
     * @param supplierMaterialList 供应商物料
     * @return 结果
     */
    public int updateSupplierMaterialList(SupplierMaterialList supplierMaterialList);

    /**
     * 删除供应商物料
     * 
     * @param supplierMaterialId 供应商物料主键
     * @return 结果
     */
    public int deleteSupplierMaterialListBySupplierMaterialId(Long supplierMaterialId);

    /**
     * 批量删除供应商物料
     * 
     * @param supplierMaterialIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierMaterialListBySupplierMaterialIds(Long[] supplierMaterialIds);
}
