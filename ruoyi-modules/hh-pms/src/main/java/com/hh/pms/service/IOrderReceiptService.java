package com.hh.pms.service;

import java.util.List;

import com.ruoyi.system.api.domain.OrderReceipt;

/**
 * 订单收货管理Service接口
 * 
 * @author ruoyi
 * @date 2023-12-20
 */
public interface IOrderReceiptService 
{
    /**
     * 查询订单收货管理
     * 
     * @param receiptId 订单收货管理主键
     * @return 订单收货管理
     */
    public OrderReceipt selectOrderReceiptByReceiptId(Long receiptId);

    /**
     * 查询订单收货管理列表
     * 
     * @param orderReceipt 订单收货管理
     * @return 订单收货管理集合
     */
    public List<OrderReceipt> selectOrderReceiptList(OrderReceipt orderReceipt);

    /**
     * 新增订单收货管理
     * 
     * @param orderReceipt 订单收货管理
     * @return 结果
     */
    public int insertOrderReceipt(OrderReceipt orderReceipt);

    /**
     * 修改订单收货管理
     * 
     * @param orderReceipt 订单收货管理
     * @return 结果
     */
    public int updateOrderReceipt(OrderReceipt orderReceipt);

    /**
     * 批量删除订单收货管理
     * 
     * @param receiptIds 需要删除的订单收货管理主键集合
     * @return 结果
     */
    public int deleteOrderReceiptByReceiptIds(Long[] receiptIds);

    /**
     * 删除订单收货管理信息
     * 
     * @param receiptId 订单收货管理主键
     * @return 结果
     */
    public int deleteOrderReceiptByReceiptId(Long receiptId);
}
