package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.hh.pms.mapper.OrderManagerMapper;

import com.hh.pms.mapper.ReceiptDetailsMapper;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.OrderExecutionDetails;
import com.ruoyi.system.api.domain.OrderMaterial;
import com.ruoyi.system.api.domain.ReceiptDetails;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private ReceiptDetailsMapper receiptDetailsMapper;

    @Autowired
    private OrderManagerMapper orderManagerMapper;

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

    /**
     * 新建送货单
     *
     * @param orderExecutionDetails mingxi
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public int updateOrderExecutionDetailsandDelivery(OrderExecutionDetails orderExecutionDetails) throws Exception {
        //发货状态是执行中状态
        //现在是执行中状态  需要计算 发货金额(发货总金额) 待发货数量  待发货金额
        //获取总金额 获取含税单价 获取数量
        //计算发货单号
        ReceiptDetails receiptDetails = new ReceiptDetails();
        Date date = new Date();
        String deliveryCode = createOrderCode(date);
        orderExecutionDetails.setCreateTime(date);
        orderExecutionDetails.setDeliveryNoteNo(deliveryCode);

        List<OrderExecutionDetails> deliveries = orderExecutionDetailsMapper.selectOrderDelivery(orderExecutionDetails.getOrderLineNo());
        if (!deliveries.isEmpty()){
            if (deliveries.stream()
                    .map(OrderExecutionDetails::getDeliveryQuantity)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .add(orderExecutionDetails.getDeliveryQuantity())
                    .compareTo(orderExecutionDetails.getRequiredQuantity()) > 0) {
                throw new Exception("该物料已经超过上限了");
            }
        }

        orderExecutionDetails.getOrderMaterials().forEach(material -> {
            if (!orderExecutionDetails.getMaterialCode().equals(material.getOrCode()) || !orderExecutionDetails.getMaterialName().equals(material.getOrName())) {
                BigDecimal awaitingDeliveryAmount = material.getTax().equals(BigDecimal.ZERO) ?
                        material.getNoTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity()) :
                        material.getTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity());
                orderExecutionDetails.setAwaitingDeliveryAmount(awaitingDeliveryAmount);
            } else {
                if (orderExecutionDetails.getDeliveryQuantity().compareTo(material.getRequireNumber()) > 0) {
                    throw new RuntimeException("发货数量大于需求数量");
                }

                BeanUtils.copyProperties(material, receiptDetails);
                BeanUtils.copyProperties(orderExecutionDetails, receiptDetails);
                receiptDetails.setExemptFromMaterialInspection(1L);
                receiptDetails.setExemptFromSupplyCapacityCheck(1L);


                BigDecimal result = material.getTax().equals(BigDecimal.ZERO) ?
                        material.getNoTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity()) :
                        material.getTaxPrice().multiply(orderExecutionDetails.getDeliveryQuantity());
                orderExecutionDetails.setDeliveredAmount(result);
            }
        });
        //插入收货物料明细
        receiptDetailsMapper.insertReceiptDetails(receiptDetails);
        //计算待发货数量 需求总量-填写的发货数量-订单量
        List<OrderExecutionDetails> orderExecutionDetails1 = orderExecutionDetailsMapper.selectDeliveryNo(orderExecutionDetails.getOrderCode());
        Optional<OrderExecutionDetails> executionDetailsOptional = orderExecutionDetails1.stream().findFirst();
        if (executionDetailsOptional.isPresent()) {
            OrderExecutionDetails executionDetails = executionDetailsOptional.get();
            BigDecimal deliveryQuantity = executionDetails.getDeliveryQuantity();
            if (deliveryQuantity != null && deliveryQuantity.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal totalDemand = orderExecutionDetails.getTotalDemand();
                BigDecimal subtractResult1 = totalDemand.subtract(deliveryQuantity).subtract(deliveryQuantity);
                orderExecutionDetails.setAwaitingDeliveryQuantity(subtractResult1);
            }

            if (orderExecutionDetails.getMaterialName().equals(executionDetails.getMaterialName())
                    && orderExecutionDetails.getOrderCode().equals(executionDetails.getOrderCode())
                    && orderExecutionDetails.getOrderLineNo().equals(executionDetails.getOrderLineNo())) {
                if ((orderExecutionDetails.getDeliveryQuantity().add(executionDetails.getReceiveQuantity()))
                        .compareTo(executionDetails.getRequiredQuantity()) > 0) {
                    BigDecimal showNumber = executionDetails.getRequiredQuantity()
                            .subtract(orderExecutionDetails.getDeliveryQuantity().add(executionDetails.getReceiveQuantity()));
                    throw new Exception("该物料还剩余" + showNumber);
                }
            }
        } else {
            BigDecimal deliveryQuantity = orderExecutionDetails.getTotalDemand();
            orderExecutionDetails.setAwaitingDeliveryQuantity(deliveryQuantity.subtract(orderExecutionDetails.getDeliveryQuantity()));
        }

        int rows = 0;
        //计算发货行号
        String deliverLineNo = null;
        if (!orderExecutionDetails1.isEmpty()) {
            deliverLineNo = orderExecutionDetails1.get(orderExecutionDetails1.size() - 1).getDeliveryNoteLineNo();
        }
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

        // 获取当前的用户信息
        String createAccount = SecurityUtils.getUsername();
        orderExecutionDetails.setOrderStatus("4");//执行中状态
        orderExecutionDetails.setOrderHandle(1L);//待收货状态

        orderExecutionDetails.setReceiver(createAccount);
        rows = orderExecutionDetailsMapper.insertOrderExecutionDetails(orderExecutionDetails);

        //修改订单管理订单号下的执行状态为执行中
        Long runningId = 5L;
        orderManagerMapper.updateOrIDByOrderCode(runningId, orderExecutionDetails.getOrderCode());

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

    @Override
    public List<OrderExecutionDetails> selectReconciliation(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.selectReconciliation(orderExecutionDetails);
    }

    @Override
    public int updateReconciliation1(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.updateReconciliation1(orderExecutionDetails);
    }

    @Override
    public int updateReconciliation2(List<Long> ids) {
        return orderExecutionDetailsMapper.updateReconciliation2(ids);
    }

    @Override
    public int updateReconciliation3(List<Long> ids) {
        return orderExecutionDetailsMapper.updateReconciliation3(ids);
    }

    @Override
    public OrderExecutionDetails selectOrderExecutionDetailsByReceiptAndOrderCode(String orderCode, String receiptNo) {
        return orderExecutionDetailsMapper.selectOrderExecutionDetailsByReceiptAndOrderCode(orderCode, receiptNo);
    }

    @Override
    public List<OrderExecutionDetails> selectOrderStockInList(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.selectOrderStockInList(orderExecutionDetails);
    }

    @Override
    public int updateReconciliation4(List<Long> ids) {
        return orderExecutionDetailsMapper.updateReconciliation4(ids);
    }

    @Override
    public int updateReconciliation5(List<Long> ids) {
        return orderExecutionDetailsMapper.updateReconciliation5(ids);
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

    @Override
    public List<OrderExecutionDetails> selectOrderExecutionDeliveryList(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.selectOrderExecutionDeliveryList(orderExecutionDetails);
    }

    @Override
    public List<OrderExecutionDetails> selectOrderReceiptList(OrderExecutionDetails orderExecutionDetails) {
        return orderExecutionDetailsMapper.selectOrderReceiptList(orderExecutionDetails);
    }

    /**
     * 发货单号生成方法
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
