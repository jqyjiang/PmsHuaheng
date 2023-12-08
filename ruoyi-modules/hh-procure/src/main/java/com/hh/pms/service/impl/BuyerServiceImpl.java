package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.BuyerMapper;
import com.hh.pms.service.IBuyerService;

/**
 * 采购员Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@Service
public class BuyerServiceImpl implements IBuyerService 
{
    @Autowired
    private BuyerMapper buyerMapper;

    /**
     * 查询采购员
     * 
     * @param purchaserId 采购员主键
     * @return 采购员
     */
    @Override
    public Buyer selectBuyerByPurchaserId(Long purchaserId)
    {
        return buyerMapper.selectBuyerByPurchaserId(purchaserId);
    }

    /**
     * 查询采购员列表
     * 
     * @param buyer 采购员
     * @return 采购员
     */
    @Override
    public List<Buyer> selectBuyerList(Buyer buyer)
    {
        return buyerMapper.selectBuyerList(buyer);
    }

    /**
     * 新增采购员
     * 
     * @param buyer 采购员
     * @return 结果
     */
    @Override
    public int insertBuyer(Buyer buyer)
    {
        return buyerMapper.insertBuyer(buyer);
    }

    /**
     * 修改采购员
     * 
     * @param buyer 采购员
     * @return 结果
     */
    @Override
    public int updateBuyer(Buyer buyer)
    {
        return buyerMapper.updateBuyer(buyer);
    }

    /**
     * 批量删除采购员
     * 
     * @param purchaserIds 需要删除的采购员主键
     * @return 结果
     */
    @Override
    public int deleteBuyerByPurchaserIds(Long[] purchaserIds)
    {
        return buyerMapper.deleteBuyerByPurchaserIds(purchaserIds);
    }

    /**
     * 删除采购员信息
     * 
     * @param purchaserId 采购员主键
     * @return 结果
     */
    @Override
    public int deleteBuyerByPurchaserId(Long purchaserId)
    {
        return buyerMapper.deleteBuyerByPurchaserId(purchaserId);
    }
}
