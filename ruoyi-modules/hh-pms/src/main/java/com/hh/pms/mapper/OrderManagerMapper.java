package com.hh.pms.mapper;

import java.util.List;
import com.hh.pms.domain.OrderManager;

/**
 * 采购订单管理Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-21
 */
public interface OrderManagerMapper 
{
    /**
     * 查询采购订单管理
     * 
     * @param orderId 采购订单管理主键
     * @return 采购订单管理
     */
    public OrderManager selectOrderManagerByOrderId(Long orderId);

    /**
     * 查询采购订单管理列表
     * 
     * @param orderManager 采购订单管理
     * @return 采购订单管理集合
     */
    public List<OrderManager> selectOrderManagerList(OrderManager orderManager);

    /**
     * 新增采购订单管理
     * 
     * @param orderManager 采购订单管理
     * @return 结果
     */
    public int insertOrderManager(OrderManager orderManager);

    /**
     * 修改采购订单管理
     * 
     * @param orderManager 采购订单管理
     * @return 结果
     */
    public int updateOrderManager(OrderManager orderManager);

    /**
     * 删除采购订单管理
     * 
     * @param orderId 采购订单管理主键
     * @return 结果
     */
    public int deleteOrderManagerByOrderId(Long orderId);

    /**
     * 批量删除采购订单管理
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderManagerByOrderIds(Long[] orderIds);
}
