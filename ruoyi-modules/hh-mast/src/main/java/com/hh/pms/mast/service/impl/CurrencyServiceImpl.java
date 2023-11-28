package com.hh.pms.mast.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.CurrencyMapper;
import com.hh.pms.mast.domain.Currency;
import com.hh.pms.mast.service.ICurrencyService;

/**
 * 币种Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-27
 */
@Service
public class CurrencyServiceImpl implements ICurrencyService
{
    @Autowired
    private CurrencyMapper currencyMapper;

    /**
     * 查询币种
     *
     * @param currencyId 币种主键
     * @return 币种
     */
    @Override
    public Currency selectCurrencyByCurrencyId(Long currencyId)
    {
        return currencyMapper.selectCurrencyByCurrencyId(currencyId);
    }

    /**
     * 查询币种列表
     *
     * @param currency 币种
     * @return 币种
     */
    @Override
    public List<Currency> selectCurrencyList(Currency currency)
    {
        return currencyMapper.selectCurrencyList(currency);
    }

    /**
     * 新增币种
     *
     * @param currency 币种
     * @return 结果
     */
    @Override
    public int insertCurrency(Currency currency)
    {
        return currencyMapper.insertCurrency(currency);
    }

    /**
     * 修改币种
     *
     * @param currency 币种
     * @return 结果
     */
    @Override
    public int updateCurrency(Currency currency)
    {
        return currencyMapper.updateCurrency(currency);
    }

    /**
     * 批量删除币种
     *
     * @param currencyIds 需要删除的币种主键
     * @return 结果
     */
    @Override
    public int deleteCurrencyByCurrencyIds(Long[] currencyIds)
    {
        return currencyMapper.deleteCurrencyByCurrencyIds(currencyIds);
    }

    /**
     * 删除币种信息
     *
     * @param currencyId 币种主键
     * @return 结果
     */
    @Override
    public int deleteCurrencyByCurrencyId(Long currencyId)
    {
        return currencyMapper.deleteCurrencyByCurrencyId(currencyId);
    }
}
