package com.hh.pms.mast.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.BankMapper;
import com.hh.pms.mast.service.IBankService;

/**
 * 银行Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@Service
public class BankServiceImpl implements IBankService
{
    @Autowired
    private BankMapper bankMapper;

    /**
     * 查询银行
     *
     * @param bankId 银行主键
     * @return 银行
     */
    @Override
    public Bank selectBankByBankId(Long bankId)
    {
        return bankMapper.selectBankByBankId(bankId);
    }

    /**
     * 查询银行列表
     *
     * @param bank 银行
     * @return 银行
     */
    @Override
    public List<Bank> selectBankList(Bank bank)
    {
        return bankMapper.selectBankList(bank);
    }

    /**
     * 新增银行
     *
     * @param bank 银行
     * @return 结果
     */
    @Override
    public int insertBank(Bank bank)
    {
        return bankMapper.insertBank(bank);
    }

    /**
     * 修改银行
     *
     * @param bank 银行
     * @return 结果
     */
    @Override
    public int updateBank(Bank bank)
    {
        return bankMapper.updateBank(bank);
    }

    /**
     * 批量删除银行
     *
     * @param bankIds 需要删除的银行主键
     * @return 结果
     */
    @Override
    public int deleteBankByBankIds(Long[] bankIds)
    {
        return bankMapper.deleteBankByBankIds(bankIds);
    }

    /**
     * 删除银行信息
     *
     * @param bankId 银行主键
     * @return 结果
     */
    @Override
    public int deleteBankByBankId(Long bankId)
    {
        return bankMapper.deleteBankByBankId(bankId);
    }
}
