package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.OrderExecutionDetails;
import com.ruoyi.system.api.domain.OrderMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.OrderExecutionDetailsMapper;
import com.hh.pms.service.IOrderExecutionDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * mingxiService业务层处理
 *
 * @author ruoyi
 * @date 2023-12-14
 */
@Service
public class OrderExecutionDetailsServiceImpl implements IOrderExecutionDetailsService {
    @Autowired
    private OrderExecutionDetailsMapper orderExecutionDetailsMapper;

    /**
     * 查询mingxi
     *
     * @param id mingxi主键
     * @return mingxi
     */
    @Override
    public OrderExecutionDetails selectOrderExecutionDetailsById(Long id) {
        return orderExecutionDetailsMapper.selectOrderExecutionDetailsById(id);
    }

    /**
     * 查询mingxi列表
     *
     * @param orderExecutionDetails mingxi
     * @return mingxi
     */
    @Override
    public List<OrderExecutionDetails> selectOrderExecutionDetailsList(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.selectOrderExecutionDetailsList(orderExecutionDetails);
    }

    /**
     * 新增mingxi
     *
     * @param orderExecutionDetails mingxi
     * @return 结果
     */
    @Override
    public int insertOrderExecutionDetails(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.insertOrderExecutionDetails(orderExecutionDetails);
    }

    /**
     * 修改mingxi
     *
     * @param orderExecutionDetails mingxi
     * @return 结果
     */
    @Override
    public int updateOrderExecutionDetails(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.updateOrderExecutionDetails(orderExecutionDetails);
    }

    @Override
    @Transactional
    public int updateOrderExecutionDetailsandDelivery(OrderExecutionDetails orderExecutionDetails) throws Exception {
        //发货状态是执行中状态
        //现在是执行中状态  需要计算 发货金额(发货总金额) 待发货数量  待发货金额
        //获取总金额 获取含税单价 获取数量
        //计算发货单号
        Date date = new Date();
        String deliveryCode = createOrderCode(date);
        orderExecutionDetails.setCreateTime(date);
        orderExecutionDetails.setDeliveryNoteNo(deliveryCode);
        BigDecimal subtractResult = BigDecimal.valueOf(0);
        List<OrderMaterial> list = orderExecutionDetails.getOrderMaterials();
        BigDecimal result = BigDecimal.valueOf(0);
        for (OrderMaterial material : list) {
            if (orderExecutionDetails.getMaterialCode().equals(material.getOrCode()) && orderExecutionDetails.getMaterialName().equals(material.getOrName())) {
                //  判断输入的送货数量是否大于需求量
                if ( orderExecutionDetails.getDeliveryQuantity().compareTo(material.getRequireNumber()) > 0) {
                    throw new Exception("发货数量大于需求数量");
                }
                if (material.getTax() == BigDecimal.valueOf(0)) {
                    //如果税率为0 南无就用单价计算发货总金额
                    result = material.getNoTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity());
                    System.out.println("这是没有税率计算的发货总金额:" + result);
                    orderExecutionDetails.setDeliveredAmount(result);//为发货总金额赋值
                } else {
                    //如果税率不为0,那么就用 含税单价计算发货总金额
                    result = material.getTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity());
                    System.out.println("这是有税率计算的发货总金额:" + result);
                    orderExecutionDetails.setDeliveredAmount(result);//为发货总金额赋值
                }
            } else {
                //这是未选中部分物料的计算待发货数量和待发货金额
                if (material.getTax() == BigDecimal.valueOf(0)) {
                    //如果税率为0 南无就用单价计算待发货总金额
                    result = material.getNoTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity());
                    System.out.println("这是没有税率计算的发货总金额:" + result);
                    orderExecutionDetails.setAwaitingDeliveryAmount(result);//为待发货总金额赋值
                } else {
                    //如果税率不为0,那么就用 含税单价计算待发货总金额
                    result = material.getTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity());
                    orderExecutionDetails.setAwaitingDeliveryAmount(result);//为待发货总金额赋值
                    System.out.println("这是有税率计算的发货总金额:" + result);
                }
            }
        }
        //计算待发货数量 需求总量-填写的发货数量-订单量deliveryNoteNo
        List<OrderExecutionDetails> orderExecutionDetails1 = orderExecutionDetailsMapper.selectDeliveryNo(orderExecutionDetails.getOrderCode());
        if (orderExecutionDetails1.size() > 0) {
            for (OrderExecutionDetails executionDetails : orderExecutionDetails1) {
                if (executionDetails.getDeliveryQuantity()!=null){
                    BigDecimal deliveryQuantity = executionDetails.getDeliveryQuantity();
                    System.out.println("这是orderExecutionDetails1的数量:" + deliveryQuantity);
                    if (deliveryQuantity.compareTo(BigDecimal.ZERO) != 0 && deliveryQuantity != null) {
                        BigDecimal totalDemand = orderExecutionDetails.getTotalDemand();
                        System.out.println("这是需求总数量:" + totalDemand);
                        subtractResult = totalDemand.subtract(deliveryQuantity).subtract(deliveryQuantity);
                        System.out.println("这是计算之后的数量:" + subtractResult);
                        //executionDetails.setDeliveredQuantity(subtractResult);
                        orderExecutionDetails.setAwaitingDeliveryQuantity(subtractResult);//为待发货数量赋值
                    }
                }
            }
        } else {
            //需求总量-填写的发货量
            BigDecimal deliveryQuantity = orderExecutionDetails.getTotalDemand();
            orderExecutionDetails.setAwaitingDeliveryQuantity(deliveryQuantity.subtract(orderExecutionDetails.getDeliveryQuantity()));
        }
        int rows = 0;
        //计算发货行号
        String deliverLineNo = null;
        if (orderExecutionDetails1.size() > 1) {
            deliverLineNo = orderExecutionDetails1.get(orderExecutionDetails1.size()).getDeliveryNoteLineNo();
        } else if (orderExecutionDetails1.size() == 1) {
            deliverLineNo = orderExecutionDetails1.get(0).getDeliveryNoteLineNo();
        } else {
            deliverLineNo = null;
        }
        //插入数据
        if (deliverLineNo == null) {
            //判断它是否为空 为空从1开始递增
            deliverLineNo = deliveryCode + "-1";
            orderExecutionDetails.setDeliveryNoteLineNo(deliverLineNo);
        } else {
            int index = deliverLineNo.lastIndexOf("-");
            String numberStr = deliverLineNo.substring(index + 1);  // "1"
            int num = Integer.valueOf(numberStr);
            num++;
            orderExecutionDetails.setDeliveryNoteLineNo(deliveryCode + "-" + num);
        }
        orderExecutionDetails.setOrderStatus("4");//执行中状态
        rows = orderExecutionDetailsMapper.insertOrderExecutionDetails(orderExecutionDetails);
        return rows;
    }

    /**
     * 批量删除mingxi
     *
     * @param ids 需要删除的mingxi主键
     * @return 结果
     */
    @Override
    public int deleteOrderExecutionDetailsByIds(Long[] ids) {
        return orderExecutionDetailsMapper.deleteOrderExecutionDetailsByIds(ids);
    }

    /**
     * 删除mingxi信息
     *
     * @param id mingxi主键
     * @return 结果
     */
    @Override
    public int deleteOrderExecutionDetailsById(Long id) {
        return orderExecutionDetailsMapper.deleteOrderExecutionDetailsById(id);
    }

    /**
     * 订单编号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createOrderCode(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String order = orderExecutionDetailsMapper.selectDeliveryCode(date);
        if (order != null) {
            //如果这个时间存在,说明今天已经有订单生成了
            String orderCode = order;
            orderCode = orderCode.substring(11, 14);
            int num = Integer.valueOf(orderCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "ASN" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%03d", num);//num<100,说明是两位数，前面要补一个0
                String code = "ASN" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "ASN" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        } else {
            //如果今天时间不存在
            String code = "ASN" + newBidDate + "001";
            return code;
        }
    }
}
