package com.hh.pms.service;

import java.util.List;
import com.hh.pms.domain.Supply;

/**
 * 供货管理Service接口
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
public interface ISupplyService 
{
    /**
     * 查询供货管理
     * 
     * @param supplyId 供货管理主键
     * @return 供货管理
     */
    public Supply selectSupplyBySupplyId(Long supplyId);

    /**
     * 查询供货管理列表
     * 
     * @param supply 供货管理
     * @return 供货管理集合
     */
    public List<Supply> selectSupplyList(Supply supply);

    /**
     * 新增供货管理
     * 
     * @param supply 供货管理
     * @return 结果
     */
    public int insertSupply(Supply supply);

    /**
     * 修改供货管理
     * 
     * @param supply 供货管理
     * @return 结果
     */
    public int updateSupply(Supply supply);

    /**
     * 批量删除供货管理
     * 
     * @param supplyIds 需要删除的供货管理主键集合
     * @return 结果
     */
    public int deleteSupplyBySupplyIds(Long[] supplyIds);

    /**
     * 删除供货管理信息
     * 
     * @param supplyId 供货管理主键
     * @return 结果
     */
    public int deleteSupplyBySupplyId(Long supplyId);

    /**
     * 简单查询公司
     */
    public List<Supply> selectCompanies();
}
