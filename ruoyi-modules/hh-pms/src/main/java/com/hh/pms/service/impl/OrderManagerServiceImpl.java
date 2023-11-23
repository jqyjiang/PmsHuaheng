package com.hh.pms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.OrderManagerMapper;
import com.hh.pms.domain.OrderManager;
import com.hh.pms.service.IOrderManagerService;

/**
 * 采购订单管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-21
 */
@Service
public class OrderManagerServiceImpl implements IOrderManagerService 
{
    @Autowired
    private OrderManagerMapper orderManagerMapper;

    /**
     * 查询采购订单管理
     * 
     * @param orderId 采购订单管理主键
     * @return 采购订单管理
     */
    @Override
    public OrderManager selectOrderManagerByOrderId(Long orderId)
    {
        return orderManagerMapper.selectOrderManagerByOrderId(orderId);
    }

    /**
     * 查询采购订单管理列表
     * 
     * @param orderManager 采购订单管理
     * @return 采购订单管理
     */
    @Override
    public List<OrderManager> selectOrderManagerList(OrderManager orderManager)
    {
        return orderManagerMapper.selectOrderManagerList(orderManager);
    }

    /**
     * 新增采购订单管理
     * 
     * @param orderManager 采购订单管理
     * @return 结果
     */
    @Override
    public int insertOrderManager(OrderManager orderManager)
    {
        return orderManagerMapper.insertOrderManager(orderManager);
    }

    /**
     * 修改采购订单管理
     * 
     * @param orderManager 采购订单管理
     * @return 结果
     */
    @Override
    public int updateOrderManager(OrderManager orderManager)
    {
        return orderManagerMapper.updateOrderManager(orderManager);
    }

    /**
     * 批量删除采购订单管理
     * 
     * @param orderIds 需要删除的采购订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderManagerByOrderIds(Long[] orderIds)
    {
        return orderManagerMapper.deleteOrderManagerByOrderIds(orderIds);
    }

    /**
     * 删除采购订单管理信息
     * 
     * @param orderId 采购订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderManagerByOrderId(Long orderId)
    {
        return orderManagerMapper.deleteOrderManagerByOrderId(orderId);
    }
}
