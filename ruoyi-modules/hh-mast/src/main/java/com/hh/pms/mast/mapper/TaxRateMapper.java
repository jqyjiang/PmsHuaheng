package com.hh.pms.mast.mapper;

import com.ruoyi.system.api.domain.TaxRate;

import java.util.List;

/**
 * 税率Mapper接口
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public interface TaxRateMapper
{
    /**
     * 查询税率
     *
     * @param taxTypeId 税率主键
     * @return 税率
     */
    public TaxRate selectTaxRateByTaxTypeId(Long taxTypeId);

    /**
     * 查询税率列表
     *
     * @param taxRate 税率
     * @return 税率集合
     */
    public List<TaxRate> selectTaxRateList(TaxRate taxRate);

    /**
     * 新增税率
     *
     * @param taxRate 税率
     * @return 结果
     */
    public int insertTaxRate(TaxRate taxRate);

    /**
     * 修改税率
     *
     * @param taxRate 税率
     * @return 结果
     */
    public int updateTaxRate(TaxRate taxRate);

    /**
     * 删除税率
     *
     * @param taxTypeId 税率主键
     * @return 结果
     */
    public int deleteTaxRateByTaxTypeId(Long taxTypeId);

    /**
     * 批量删除税率
     *
     * @param taxTypeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaxRateByTaxTypeIds(Long[] taxTypeIds);
}
