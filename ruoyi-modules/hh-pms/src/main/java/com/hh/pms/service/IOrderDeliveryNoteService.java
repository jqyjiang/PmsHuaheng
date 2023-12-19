package com.hh.pms.service;

import com.ruoyi.system.api.domain.OrderDeliveryNote;

import java.util.List;

/**
 * 订单送货管理Service接口
 * 
 * @author yt
 * @date 2023-12-08
 */
public interface IOrderDeliveryNoteService 
{
    /**
     * 查询订单送货管理
     * 
     * @param orderDeliveryNoteId 订单送货管理主键
     * @return 订单送货管理
     */
    public OrderDeliveryNote selectOrderDeliveryNoteByOrderDeliveryNoteId(Long orderDeliveryNoteId);

    /**
     * 查询订单送货管理列表
     * 
     * @param orderDeliveryNote 订单送货管理
     * @return 订单送货管理集合
     */
    public List<OrderDeliveryNote> selectOrderDeliveryNoteList(OrderDeliveryNote orderDeliveryNote);

    /**
     * 新增订单送货管理
     * 
     * @param orderDeliveryNote 订单送货管理
     * @return 结果
     */
    public int insertOrderDeliveryNote(OrderDeliveryNote orderDeliveryNote);

    /**
     * 修改订单送货管理
     * 
     * @param orderDeliveryNote 订单送货管理
     * @return 结果
     */
    public int updateOrderDeliveryNote(OrderDeliveryNote orderDeliveryNote);

    /**
     * 批量删除订单送货管理
     * 
     * @param orderDeliveryNoteIds 需要删除的订单送货管理主键集合
     * @return 结果
     */
    public int deleteOrderDeliveryNoteByOrderDeliveryNoteIds(Long[] orderDeliveryNoteIds);

    /**
     * 删除订单送货管理信息
     * 
     * @param orderDeliveryNoteId 订单送货管理主键
     * @return 结果
     */
    public int deleteOrderDeliveryNoteByOrderDeliveryNoteId(Long orderDeliveryNoteId);


}
