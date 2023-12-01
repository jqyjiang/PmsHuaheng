package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;
import com.hh.pms.domain.OrderManager;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 向订单表中查询是否存在某天
     * @param orderTime
     * @return
     */
    @Select("select order_code from order_manager where to_days(create_time)= to_days(#{orderTime}) group by order_code order by create_time DESC limit 1")
    String selectOrderCode(Date orderTime);

    /**
     * 根据执行状态Id查询采购订单列表
     * @param orId
     * @return
     */
    List<OrderManager> selectByOrderTypeRunning(Long orId);
}
