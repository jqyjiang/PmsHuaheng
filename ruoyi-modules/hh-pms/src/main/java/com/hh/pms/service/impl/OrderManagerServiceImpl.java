package com.hh.pms.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
        Date date = new Date();
        orderManager.setCreateTime(date);
        String orderCode = createOrderCode(date);
        orderManager.setOrderCode(orderCode);
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

    /**
     * 订单编号生成方法
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createOrderCode(Date date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        System.out.println("long类型的时间戳:"+date);
        System.out.println("时间类型字段:"+newBidDate);
        String order = orderManagerMapper.selectOrderCode(date);
        System.out.println("物料编码:"+order);
        if (order!=null){
            //如果这个时间存在,说明今天已经有订单生成了
            System.out.println("物料编码:"+order+"+1");
            String orderCode = order;
            orderCode=orderCode.substring(10,13);
            int num=Integer.valueOf(orderCode);
            num++;
            if(num<10){
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code ="PO"+ newBidDate + idNum;
                return code;
            }
            else if(num<100){
                String idNum = String.format("%02d", num);//num<100,说明是两位数，前面要补一个0
                String code = "PO"+newBidDate + idNum;
                return code;
            }
            else {
                String idNum = String.valueOf(num);
                String code = "PO"+newBidDate + idNum;  //date = 20201111+124
                return code;
            }

        }
        return null;
    }
}
