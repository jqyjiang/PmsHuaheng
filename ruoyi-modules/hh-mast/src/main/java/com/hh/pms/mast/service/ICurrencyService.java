package com.hh.pms.mast.service;

import java.util.List;
import com.hh.pms.mast.domain.Currency;

/**
 * 币种Service接口
 *
 * @author ruoyi
 * @date 2023-11-27
 */
public interface ICurrencyService
{
    /**
     * 查询币种
     *
     * @param currencyId 币种主键
     * @return 币种
     */
    public Currency selectCurrencyByCurrencyId(Long currencyId);

    /**
     * 查询币种列表
     *
     * @param currency 币种
     * @return 币种集合
     */
    public List<Currency> selectCurrencyList(Currency currency);

    /**
     * 新增币种
     *
     * @param currency 币种
     * @return 结果
     */
    public int insertCurrency(Currency currency);

    /**
     * 修改币种
     *
     * @param currency 币种
     * @return 结果
     */
    public int updateCurrency(Currency currency);

    /**
     * 批量删除币种
     *
     * @param currencyIds 需要删除的币种主键集合
     * @return 结果
     */
    public int deleteCurrencyByCurrencyIds(Long[] currencyIds);

    /**
     * 删除币种信息
     *
     * @param currencyId 币种主键
     * @return 结果
     */
    public int deleteCurrencyByCurrencyId(Long currencyId);
}
