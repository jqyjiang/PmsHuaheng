package com.hh.pms.mast.service;

import java.util.List;
import com.hh.pms.mast.domain.UnitOfAccount;

/**
 * 计算单位定义Service接口
 *
 * @author ruoyi
 * @date 2023-11-28
 */
public interface IUnitOfAccountService
{
    /**
     * 查询计算单位定义
     *
     * @param unitId 计算单位定义主键
     * @return 计算单位定义
     */
    public UnitOfAccount selectUnitOfAccountByUnitId(Long unitId);

    /**
     * 查询计算单位定义列表
     *
     * @param unitOfAccount 计算单位定义
     * @return 计算单位定义集合
     */
    public List<UnitOfAccount> selectUnitOfAccountList(UnitOfAccount unitOfAccount);

    /**
     * 新增计算单位定义
     *
     * @param unitOfAccount 计算单位定义
     * @return 结果
     */
    public int insertUnitOfAccount(UnitOfAccount unitOfAccount);

    /**
     * 修改计算单位定义
     *
     * @param unitOfAccount 计算单位定义
     * @return 结果
     */
    public int updateUnitOfAccount(UnitOfAccount unitOfAccount);

    /**
     * 批量删除计算单位定义
     *
     * @param unitIds 需要删除的计算单位定义主键集合
     * @return 结果
     */
    public int deleteUnitOfAccountByUnitIds(Long[] unitIds);

    /**
     * 删除计算单位定义信息
     *
     * @param unitId 计算单位定义主键
     * @return 结果
     */
    public int deleteUnitOfAccountByUnitId(Long unitId);
}
