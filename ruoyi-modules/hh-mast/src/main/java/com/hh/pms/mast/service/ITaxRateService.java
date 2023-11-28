package com.hh.pms.mast.service;

import java.util.List;
import com.hh.pms.mast.domain.TaxRate;

/**
 * 税率Service接口
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public interface ITaxRateService
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
     * 批量删除税率
     *
     * @param taxTypeIds 需要删除的税率主键集合
     * @return 结果
     */
    public int deleteTaxRateByTaxTypeIds(Long[] taxTypeIds);

    /**
     * 删除税率信息
     *
     * @param taxTypeId 税率主键
     * @return 结果
     */
    public int deleteTaxRateByTaxTypeId(Long taxTypeId);
}
