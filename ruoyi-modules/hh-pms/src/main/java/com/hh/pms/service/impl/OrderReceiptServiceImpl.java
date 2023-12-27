package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hh.pms.mapper.OrderExecutionDetailsMapper;
import com.hh.pms.mapper.OrderManagerMapper;
import com.hh.pms.mapper.QualityTaskListMapper;
import com.ruoyi.system.api.domain.OrderExecutionDetails;

import com.ruoyi.system.api.domain.OrderReceipt;
import com.ruoyi.system.api.domain.QualityTaskList;
import com.ruoyi.system.api.domain.ReceiptDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.hh.pms.mapper.OrderReceiptMapper;
import com.hh.pms.service.IOrderReceiptService;

/**
 * 订单收货管理Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-20
 */
@Service
public class OrderReceiptServiceImpl implements IOrderReceiptService {
    @Autowired
    private OrderReceiptMapper orderReceiptMapper;

    @Autowired
    private OrderExecutionDetailsMapper orderExecutionDetailsMapper;

    @Autowired
    private OrderManagerMapper orderManagerMapper;

    @Autowired
    private QualityTaskListMapper qualityTaskListMapper;

    /**
     * 查询订单收货管理
     *
     * @param receiptId 订单收货管理主键
     * @return 订单收货管理
     */
    @Override
    public OrderReceipt selectOrderReceiptByReceiptId(Long receiptId) {
        return orderReceiptMapper.selectOrderReceiptByReceiptId(receiptId);
    }

    /**
     * 查询订单收货管理列表
     *
     * @param orderReceipt 订单收货管理
     * @return 订单收货管理
     */
    @Override
    public List<OrderReceipt> selectOrderReceiptList(OrderReceipt orderReceipt) {
        return orderReceiptMapper.selectOrderReceiptList(orderReceipt);
    }

    /**
     * 新增订单收货管理
     *
     * @param orderReceipt 订单收货管理
     * @return 结果
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insertOrderReceipt(OrderReceipt orderReceipt) {
        //生成当前时间
        Date date = new Date();
        BigDecimal receiptQuantity = orderReceipt.getReceiveQuantity();//收货数量
        BigDecimal recepitAmountTax = orderReceipt.getRecepitAmountTax();//收货总金额

        //生成收货单号
        String receiptCode = createOrderCode(date);
        orderReceipt.setReceiptNoteNo(receiptCode);
        orderReceipt.setCreateTime(date);
        int rows = orderReceiptMapper.insertOrderReceipt(orderReceipt);
        //创建任务质检单
        QualityTaskList qualityTaskList = new QualityTaskList();
        BeanUtils.copyProperties(orderReceipt,qualityTaskList);
        BeanUtils.copyProperties(orderReceipt.getReceiptDetails().get(0),qualityTaskList);
        qualityTaskList.setReceiveDate(date);
        qualityTaskList.setConsignee(orderReceipt.getConsignee());
        qualityTaskList.setQualityStatus(1l);
        qualityTaskListMapper.insertQualityTaskList(qualityTaskList);
        //根据传进来的OrderReceipt对象 包括了订单收货明细对象 订单明细收货对象只需要修改部分数据
        //对这个对象的部分字段进行更改 订单执行状态 质检状态  订单处理状态 收货数量和收货总金额 收货时间
        OrderExecutionDetails orderExecutionDetails = new OrderExecutionDetails();
        orderExecutionDetails.setReceivedDate(date);
        //为收货数量和收货总金额赋值
        List<ReceiptDetails> receiptDetails1 = orderReceipt.getReceiptDetails();
        orderExecutionDetails.setReceiptNoteNo(orderReceipt.getReceiptNoteNo());//赋值收货单号
        orderExecutionDetails.setReceiveQuantity(receiptQuantity);//赋值收货数量
        orderExecutionDetails.setRecepitAmountTax(recepitAmountTax);
        BeanUtils.copyProperties(receiptDetails1.get(0), orderExecutionDetails);
        orderExecutionDetails.setOrderHandle(2l);//已收货
        orderExecutionDetails.setOrderQuanlity(1l);//已收货未质检
        //修改订单执行明细的部分字段
        orderExecutionDetailsMapper.updateOrderExecutionDetailsReceiptNoAndOrderCode(orderExecutionDetails);
        //orderExecutionDetails.setOrderStatus("2");//已完成
        // 这里还需判断这个订单管理下的需求数量是否全部发货成功并且已接收 如果已接收那么就把这个订单改为已完成 查询订单号下的执行明细的待发货数量  待发货数量为零 就改为已完成
        // 使用orderCode 查询订单执行明细下面的送货数量相加 或者说是收货数量相加是否等于总需求量  还需要判断 每个物料的需求数量是否已上限 然后再加全部的数量是否等于总需求
        String orderCode = orderExecutionDetails.getOrderCode();
        //在订单管理下这个orderCode作为条件 需要更改执行状态为已完成
        Long orId = 7l;
        //用这个orderCode需要查询订单明细表
        //用这个orderCode查询order_material表对比
        List<OrderExecutionDetails> orderExecutionDetails1 = orderExecutionDetailsMapper.selectOrderExecutionDetailsListByOrderCode(orderCode);
        for (OrderExecutionDetails order : orderExecutionDetails1) {
            //这是对单个同一物料状态的修改
            if (orderReceipt.getReceiptDetails().get(0).getOrName().equals(order.getMaterialName()) && orderReceipt.getReceiptDetails().get(0).getOrderCode().equals(order.getOrderCode())) {
                BigDecimal requireNumber = order.getDeliveryQuantity();
                requireNumber = requireNumber.add(order.getDeliveryQuantity());
                if (order.getRequiredQuantity().compareTo(requireNumber) == 0) {
                 //表示已到需求量,将改物料所属订单号下的物料改为已完成
                    Long orderStatus = 2l;
                    orderExecutionDetailsMapper.updateMaterialState(orderStatus,order.getMaterialName(),order.getOrderCode());
                }
            }
            BigDecimal result = order.getDeliveryQuantity();
            result=result.add(order.getDeliveryQuantity());
            if (result.compareTo(order.getTotalDemand())==0){
                //修改订单管理下面的执行状态为已完成
                orderManagerMapper.updateOrIDByOrderCode(orId, orderCode);
            }
        }
        // TODO 还需判断是否有退回的数量 暂且不管

        //orderExecutionDetailsMapper.updateOrderExecutionDetails(orderExecutionDetails);
        return rows;
    }

    /**
     * 修改订单收货管理
     *
     * @param orderReceipt 订单收货管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrderReceipt(OrderReceipt orderReceipt) {
        orderReceiptMapper.deleteReceiptDetailsByDeliveryNoteNo(orderReceipt.getReceiptId());
        insertReceiptDetails(orderReceipt);
        return orderReceiptMapper.updateOrderReceipt(orderReceipt);
    }

    /**
     * 批量删除订单收货管理
     *
     * @param receiptIds 需要删除的订单收货管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderReceiptByReceiptIds(Long[] receiptIds) {
        orderReceiptMapper.deleteReceiptDetailsByDeliveryNoteNos(receiptIds);
        return orderReceiptMapper.deleteOrderReceiptByReceiptIds(receiptIds);
    }

    /**
     * 删除订单收货管理信息
     *
     * @param receiptId 订单收货管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderReceiptByReceiptId(Long receiptId) {
        orderReceiptMapper.deleteReceiptDetailsByDeliveryNoteNo(receiptId);
        return orderReceiptMapper.deleteOrderReceiptByReceiptId(receiptId);
    }

    /**
     * 新增收货明细信息信息
     *
     * @param orderReceipt 订单收货管理对象
     */
    public void insertReceiptDetails(OrderReceipt orderReceipt) {
        List<ReceiptDetails> receiptDetailsList = orderReceipt.getReceiptDetailsList();
        Long receiptId = orderReceipt.getReceiptId();
        if (StringUtils.isNotNull(receiptDetailsList)) {
            List<ReceiptDetails> list = new ArrayList<ReceiptDetails>();
            for (ReceiptDetails receiptDetails : receiptDetailsList) {
                receiptDetails.setDeliveryNoteNo(String.valueOf(receiptId));
                list.add(receiptDetails);
            }
            if (list.size() > 0) {
                orderReceiptMapper.batchReceiptDetails(list);
            }
        }
    }

    /**
     * 收货单号生成方法
     *
     * @param date 传入当前日期
     * @return 收货单号
     */
    public String createOrderCode(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String order = orderReceiptMapper.selectReceiptCode(date);
        if (order != null) {
            //如果这个时间存在,说明今天已经有订单生成了
            String orderCode = order;
            orderCode = orderCode.substring(10, 13);
            int num = Integer.valueOf(orderCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "GR" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%03d", num);//num<100,说明是两位数，前面要补一个0
                String code = "GR" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "GR" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        } else {
            //如果今天时间不存在
            String code = "GR" + newBidDate + "001";
            return code;
        }
    }
}
