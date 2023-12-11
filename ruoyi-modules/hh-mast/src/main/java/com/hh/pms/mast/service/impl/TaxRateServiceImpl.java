package com.hh.pms.mast.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.TaxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.TaxRateMapper;
import com.hh.pms.mast.service.ITaxRateService;

/**
 * 税率Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@Service
public class TaxRateServiceImpl implements ITaxRateService
{
    @Autowired
    private TaxRateMapper taxRateMapper;

    /**
     * 查询税率
     *
     * @param taxTypeId 税率主键
     * @return 税率
     */
    @Override
    public TaxRate selectTaxRateByTaxTypeId(Long taxTypeId)
    {
        return taxRateMapper.selectTaxRateByTaxTypeId(taxTypeId);
    }

    /**
     * 查询税率列表
     *
     * @param taxRate 税率
     * @return 税率
     */
    @Override
    public List<TaxRate> selectTaxRateList(TaxRate taxRate)
    {
        return taxRateMapper.selectTaxRateList(taxRate);
    }

    /**
     * 新增税率
     *
     * @param taxRate 税率
     * @return 结果
     */
    @Override
    public int insertTaxRate(TaxRate taxRate)
    {
        return taxRateMapper.insertTaxRate(taxRate);
    }

    /**
     * 修改税率
     *
     * @param taxRate 税率
     * @return 结果
     */
    @Override
    public int updateTaxRate(TaxRate taxRate)
    {
        return taxRateMapper.updateTaxRate(taxRate);
    }

    /**
     * 批量删除税率
     *
     * @param taxTypeIds 需要删除的税率主键
     * @return 结果
     */
    @Override
    public int deleteTaxRateByTaxTypeIds(Long[] taxTypeIds)
    {
        return taxRateMapper.deleteTaxRateByTaxTypeIds(taxTypeIds);
    }

    /**
     * 删除税率信息
     *
     * @param taxTypeId 税率主键
     * @return 结果
     */
    @Override
    public int deleteTaxRateByTaxTypeId(Long taxTypeId)
    {
        return taxRateMapper.deleteTaxRateByTaxTypeId(taxTypeId);
    }
}
