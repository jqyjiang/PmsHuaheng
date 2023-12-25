package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.OrderManager;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
     * 根据订单号查询订单信息
     * @param orderCode
     * @return
     */
    public List<OrderManager> selectOrderManagerByOrderCode(@Param("orderCode") String orderCode);
    /**
     * 查询执行状态个数
     * @return
     */
    public List<OrderManager> findRunTypeNumber();

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
    /**
     * 修改订单状态
     * @param orderManager
     * @return
     */
    int updateOrderManagerState(OrderManager orderManager);

    @Update("update order_manager set or_id =#{orId} where order_code=#{orderCode}")
    int updateOrIDByOrderCode(@Param("orId") Long orId,@Param("orderCode") String orderCode);

}
