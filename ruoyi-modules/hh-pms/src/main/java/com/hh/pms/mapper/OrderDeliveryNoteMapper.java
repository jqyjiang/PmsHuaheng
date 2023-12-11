package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.Delivery;
import com.ruoyi.system.api.domain.OrderDeliveryNote;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 订单送货管理Mapper接口
 * 
 * @author yt
 * @date 2023-12-08
 */
public interface OrderDeliveryNoteMapper 
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
     * 删除订单送货管理
     * 
     * @param orderDeliveryNoteId 订单送货管理主键
     * @return 结果
     */
    public int deleteOrderDeliveryNoteByOrderDeliveryNoteId(Long orderDeliveryNoteId);

    /**
     * 批量删除订单送货管理
     * 
     * @param orderDeliveryNoteIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderDeliveryNoteByOrderDeliveryNoteIds(Long[] orderDeliveryNoteIds);

    /**
     * 批量删除产品明细
     * 
     * @param orderDeliveryNoteIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeliveryByDeliveryIds(Long[] orderDeliveryNoteIds);
    
    /**
     * 批量新增产品明细
     * 
     * @param deliveryList 产品明细列表
     * @return 结果
     */
    public int batchDelivery(List<Delivery> deliveryList);
    

    /**
     * 通过订单送货管理主键删除产品明细信息
     * 
     * @param orderDeliveryNoteId 订单送货管理ID
     * @return 结果
     */
    public int deleteDeliveryByDeliveryId(Long orderDeliveryNoteId);

    /**
     * 查询当天日期是否存在
     * @param date
     * @return
     */
    @Select("select order_delivery_code from order_delivery_note where to_days(create_time)= to_days(#{date}) group by order_delivery_code order by create_time DESC limit 1")
    String selectOrderCode(@Param("date") Date date);
}
