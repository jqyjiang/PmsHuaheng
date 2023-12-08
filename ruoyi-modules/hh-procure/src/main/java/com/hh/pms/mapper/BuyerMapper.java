package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.Buyer;

import java.util.List;

/**
 * 采购员Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public interface BuyerMapper 
{
    /**
     * 查询采购员
     * 
     * @param purchaserId 采购员主键
     * @return 采购员
     */
    public Buyer selectBuyerByPurchaserId(Long purchaserId);

    /**
     * 查询采购员列表
     * 
     * @param buyer 采购员
     * @return 采购员集合
     */
    public List<Buyer> selectBuyerList(Buyer buyer);

    /**
     * 新增采购员
     * 
     * @param buyer 采购员
     * @return 结果
     */
    public int insertBuyer(Buyer buyer);

    /**
     * 修改采购员
     * 
     * @param buyer 采购员
     * @return 结果
     */
    public int updateBuyer(Buyer buyer);

    /**
     * 删除采购员
     * 
     * @param purchaserId 采购员主键
     * @return 结果
     */
    public int deleteBuyerByPurchaserId(Long purchaserId);

    /**
     * 批量删除采购员
     * 
     * @param purchaserIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBuyerByPurchaserIds(Long[] purchaserIds);
}
