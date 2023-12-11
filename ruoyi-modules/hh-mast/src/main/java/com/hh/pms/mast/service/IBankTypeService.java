package com.hh.pms.mast.service;

import com.ruoyi.system.api.domain.BankType;

import java.util.List;

/**
 * 银行类型Service接口
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public interface IBankTypeService
{
    /**
     * 查询银行类型
     *
     * @param bankTypeId 银行类型主键
     * @return 银行类型
     */
    public BankType selectBankTypeByBankTypeId(Long bankTypeId);

    /**
     * 查询银行类型列表
     *
     * @param bankType 银行类型
     * @return 银行类型集合
     */
    public List<BankType> selectBankTypeList(BankType bankType);

    /**
     * 新增银行类型
     *
     * @param bankType 银行类型
     * @return 结果
     */
    public int insertBankType(BankType bankType);

    /**
     * 修改银行类型
     *
     * @param bankType 银行类型
     * @return 结果
     */
    public int updateBankType(BankType bankType);

    /**
     * 批量删除银行类型
     *
     * @param bankTypeIds 需要删除的银行类型主键集合
     * @return 结果
     */
    public int deleteBankTypeByBankTypeIds(Long[] bankTypeIds);

    /**
     * 删除银行类型信息
     *
     * @param bankTypeId 银行类型主键
     * @return 结果
     */
    public int deleteBankTypeByBankTypeId(Long bankTypeId);
}
