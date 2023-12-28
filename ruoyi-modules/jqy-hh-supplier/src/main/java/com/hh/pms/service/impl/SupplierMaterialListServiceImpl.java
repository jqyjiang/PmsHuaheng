package com.hh.pms.service.impl;

import java.util.List;

import com.hh.pms.mapper.SupplierMaterialListMapper;
import com.hh.pms.service.ISupplierMaterialListService;
import com.ruoyi.system.api.domain.SupplierMaterialList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 供应商物料Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@Service
public class SupplierMaterialListServiceImpl implements ISupplierMaterialListService
{
    @Autowired
    private SupplierMaterialListMapper supplierMaterialListMapper;

    /**
     * 查询供应商物料
     * 
     * @param supplierMaterialId 供应商物料主键
     * @return 供应商物料
     */
    @Override
    public SupplierMaterialList selectSupplierMaterialListBySupplierMaterialId(Long supplierMaterialId)
    {
        return supplierMaterialListMapper.selectSupplierMaterialListBySupplierMaterialId(supplierMaterialId);
    }

    /**
     * 查询供应商物料列表
     * 
     * @param supplierMaterialList 供应商物料
     * @return 供应商物料
     */
    @Override
    public List<SupplierMaterialList> selectSupplierMaterialListList(SupplierMaterialList supplierMaterialList)
    {
        return supplierMaterialListMapper.selectSupplierMaterialListList(supplierMaterialList);
    }

    /**
     * 新增供应商物料
     * 
     * @param supplierMaterialList 供应商物料
     * @return 结果
     */
    @Override
    public int insertSupplierMaterialList(SupplierMaterialList supplierMaterialList)
    {
        return supplierMaterialListMapper.insertSupplierMaterialList(supplierMaterialList);
    }

    /**
     * 修改供应商物料
     * 
     * @param supplierMaterialList 供应商物料
     * @return 结果
     */
    @Override
    public int updateSupplierMaterialList(SupplierMaterialList supplierMaterialList)
    {
        return supplierMaterialListMapper.updateSupplierMaterialList(supplierMaterialList);
    }

    /**
     * 批量删除供应商物料
     * 
     * @param supplierMaterialIds 需要删除的供应商物料主键
     * @return 结果
     */
    @Override
    public int deleteSupplierMaterialListBySupplierMaterialIds(Long[] supplierMaterialIds)
    {
        return supplierMaterialListMapper.deleteSupplierMaterialListBySupplierMaterialIds(supplierMaterialIds);
    }

    /**
     * 删除供应商物料信息
     * 
     * @param supplierMaterialId 供应商物料主键
     * @return 结果
     */
    @Override
    public int deleteSupplierMaterialListBySupplierMaterialId(Long supplierMaterialId)
    {
        return supplierMaterialListMapper.deleteSupplierMaterialListBySupplierMaterialId(supplierMaterialId);
    }

    //根据供货单id查询供应商物料编码
    @Override
    public List<SupplierMaterialList> selectSupplierMaterialListBySupplyId(Long supplyId) {
        return supplierMaterialListMapper.selectSupplierMaterialListBySupplyId(supplyId);
    }

    //根据供货id删除供应商物料
    @Override
    public int deleteSupplierMaterialListBySupplyIds(Long[] supplyId) {
        return supplierMaterialListMapper.deleteSupplierMaterialListBySupplyIds(supplyId);
    }
    /**
     * //根据现场考察id查询供应商物料编码
     *
     * @param soiId 现场考察ID
     * @return 结果
     */
    @Override
    public List<SupplierMaterialList> selectSupplierMaterialListBySoiId(Long soiId) {
        return supplierMaterialListMapper.selectSupplierMaterialListBySoiId(soiId);
    }

    //根据供货id删除供应商物料
    @Override
    public List<SupplierMaterialList> selectSupplierMaterialListBysampleId(Long sampleId) {
        return supplierMaterialListMapper.selectSupplierMaterialListBysampleId(sampleId);
    }
}
