package com.hh.pms.mast.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.UnitOfAccountMapper;
import com.hh.pms.mast.domain.UnitOfAccount;
import com.hh.pms.mast.service.IUnitOfAccountService;

/**
 * 计算单位定义Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
@Service
public class UnitOfAccountServiceImpl implements IUnitOfAccountService 
{
    @Autowired
    private UnitOfAccountMapper unitOfAccountMapper;

    /**
     * 查询计算单位定义
     * 
     * @param unitId 计算单位定义主键
     * @return 计算单位定义
     */
    @Override
    public UnitOfAccount selectUnitOfAccountByUnitId(Long unitId)
    {
        return unitOfAccountMapper.selectUnitOfAccountByUnitId(unitId);
    }

    /**
     * 查询计算单位定义列表
     * 
     * @param unitOfAccount 计算单位定义
     * @return 计算单位定义
     */
    @Override
    public List<UnitOfAccount> selectUnitOfAccountList(UnitOfAccount unitOfAccount)
    {
        return unitOfAccountMapper.selectUnitOfAccountList(unitOfAccount);
    }

    /**
     * 新增计算单位定义
     * 
     * @param unitOfAccount 计算单位定义
     * @return 结果
     */
    @Override
    public int insertUnitOfAccount(UnitOfAccount unitOfAccount)
    {
        return unitOfAccountMapper.insertUnitOfAccount(unitOfAccount);
    }

    /**
     * 修改计算单位定义
     * 
     * @param unitOfAccount 计算单位定义
     * @return 结果
     */
    @Override
    public int updateUnitOfAccount(UnitOfAccount unitOfAccount)
    {
        return unitOfAccountMapper.updateUnitOfAccount(unitOfAccount);
    }

    /**
     * 批量删除计算单位定义
     * 
     * @param unitIds 需要删除的计算单位定义主键
     * @return 结果
     */
    @Override
    public int deleteUnitOfAccountByUnitIds(Long[] unitIds)
    {
        return unitOfAccountMapper.deleteUnitOfAccountByUnitIds(unitIds);
    }

    /**
     * 删除计算单位定义信息
     * 
     * @param unitId 计算单位定义主键
     * @return 结果
     */
    @Override
    public int deleteUnitOfAccountByUnitId(Long unitId)
    {
        return unitOfAccountMapper.deleteUnitOfAccountByUnitId(unitId);
    }
}
