package com.hh.pms.mast.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.BankType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.BankTypeMapper;
import com.hh.pms.mast.service.IBankTypeService;

/**
 * 银行类型Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@Service
public class BankTypeServiceImpl implements IBankTypeService
{
    @Autowired
    private BankTypeMapper bankTypeMapper;

    /**
     * 查询银行类型
     *
     * @param bankTypeId 银行类型主键
     * @return 银行类型
     */
    @Override
    public BankType selectBankTypeByBankTypeId(Long bankTypeId)
    {
        return bankTypeMapper.selectBankTypeByBankTypeId(bankTypeId);
    }

    /**
     * 查询银行类型列表
     *
     * @param bankType 银行类型
     * @return 银行类型
     */
    @Override
    public List<BankType> selectBankTypeList(BankType bankType)
    {
        return bankTypeMapper.selectBankTypeList(bankType);
    }

    /**
     * 新增银行类型
     *
     * @param bankType 银行类型
     * @return 结果
     */
    @Override
    public int insertBankType(BankType bankType)
    {
        return bankTypeMapper.insertBankType(bankType);
    }

    /**
     * 修改银行类型
     *
     * @param bankType 银行类型
     * @return 结果
     */
    @Override
    public int updateBankType(BankType bankType)
    {
        return bankTypeMapper.updateBankType(bankType);
    }

    /**
     * 批量删除银行类型
     *
     * @param bankTypeIds 需要删除的银行类型主键
     * @return 结果
     */
    @Override
    public int deleteBankTypeByBankTypeIds(Long[] bankTypeIds)
    {
        return bankTypeMapper.deleteBankTypeByBankTypeIds(bankTypeIds);
    }

    /**
     * 删除银行类型信息
     *
     * @param bankTypeId 银行类型主键
     * @return 结果
     */
    @Override
    public int deleteBankTypeByBankTypeId(Long bankTypeId)
    {
        return bankTypeMapper.deleteBankTypeByBankTypeId(bankTypeId);
    }
}
