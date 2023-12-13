package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.SupplierDetails;

import java.util.List;

/**
 * 供应商列表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-24
 */
public interface SupplierDetailsMapper 
{
    /**
     * 查询供应商列表
     * 
     * @param sdId 供应商列表主键
     * @return 供应商列表
     */
    public SupplierDetails selectSupplierDetailsBySdId(Long sdId);

    /**
     * 查询供应商列表列表
     * 
     * @param supplierDetails 供应商列表
     * @return 供应商列表集合
     */
    public List<SupplierDetails> selectSupplierDetailsList(SupplierDetails supplierDetails);

    /**
     * 查询供应商审核过了的列表
     * @param supplierDetails
     * @return
     */
    public List<SupplierDetails> selectSupplierLifeStage(SupplierDetails supplierDetails);
    /**
     * 新增供应商列表
     * 
     * @param supplierDetails 供应商列表
     * @return 结果
     */
    public int insertSupplierDetails(SupplierDetails supplierDetails);

    /**
     * 修改供应商列表
     * 
     * @param supplierDetails 供应商列表
     * @return 结果
     */
    public int updateSupplierDetails(SupplierDetails supplierDetails);
    /**
     * 审核供应商生命周期
     */
    public int updateSupplierExamine(SupplierDetails supplierDetails);
    /**
     * 删除供应商列表
     * 
     * @param sdId 供应商列表主键
     * @return 结果
     */
    public int deleteSupplierDetailsBySdId(Long sdId);

    /**
     * 批量删除供应商列表
     * 
     * @param sdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierDetailsBySdIds(Long[] sdIds);

    //删除供应商类别
    int deleteSupplierClassBySdIds(Long[] sdIds);

    //查询注册好的供应商(一般能做供应商操作的数据)
    List<SupplierDetails> selectCanSupplier();
}
