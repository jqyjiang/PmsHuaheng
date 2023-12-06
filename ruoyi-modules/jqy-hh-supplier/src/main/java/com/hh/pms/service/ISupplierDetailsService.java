package com.hh.pms.service;

import java.util.List;
import com.hh.pms.domain.SupplierDetails;

/**
 * 供应商列表Service接口
 * 
 * @author ruoyi
 * @date 2023-11-24
 */
public interface ISupplierDetailsService 
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
     * 批量删除供应商列表
     * 
     * @param sdIds 需要删除的供应商列表主键集合
     * @return 结果
     */
    public int deleteSupplierDetailsBySdIds(Long[] sdIds);

    /**
     * 删除供应商列表信息
     * 
     * @param sdId 供应商列表主键
     * @return 结果
     */
    public int deleteSupplierDetailsBySdId(Long sdId);

    int deleteSupplierClassBySdIds(Long[] sdIds);
}
