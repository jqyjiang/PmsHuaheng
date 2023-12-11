package com.hh.pms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.SupplyMapper;
import com.hh.pms.domain.Supply;
import com.hh.pms.service.ISupplyService;

/**
 * 供货管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
@Service
public class SupplyServiceImpl implements ISupplyService 
{
    @Autowired
    private SupplyMapper supplyMapper;

    /**
     * 查询供货管理
     * 
     * @param supplyId 供货管理主键
     * @return 供货管理
     */
    @Override
    public Supply selectSupplyBySupplyId(Long supplyId)
    {
        return supplyMapper.selectSupplyBySupplyId(supplyId);
    }

    /**
     * 查询供货管理列表
     * 
     * @param supply 供货管理
     * @return 供货管理
     */
    @Override
    public List<Supply> selectSupplyList(Supply supply)
    {
        return supplyMapper.selectSupplyList(supply);
    }

    /**
     * 新增供货管理
     * 
     * @param supply 供货管理
     * @return 结果
     */
    @Override
    public int insertSupply(Supply supply)
    {
        return supplyMapper.insertSupply(supply);
    }

    /**
     * 修改供货管理
     * 
     * @param supply 供货管理
     * @return 结果
     */
    @Override
    public int updateSupply(Supply supply)
    {
        return supplyMapper.updateSupply(supply);
    }

    /**
     * 批量删除供货管理
     * 
     * @param supplyIds 需要删除的供货管理主键
     * @return 结果
     */
    @Override
    public int deleteSupplyBySupplyIds(Long[] supplyIds)
    {
        return supplyMapper.deleteSupplyBySupplyIds(supplyIds);
    }

    /**
     * 删除供货管理信息
     * 
     * @param supplyId 供货管理主键
     * @return 结果
     */
    @Override
    public int deleteSupplyBySupplyId(Long supplyId)
    {
        return supplyMapper.deleteSupplyBySupplyId(supplyId);
    }

    @Override
    public List<Supply> selectCompanies() {
        return supplyMapper.selectCompanies();
    }
}
