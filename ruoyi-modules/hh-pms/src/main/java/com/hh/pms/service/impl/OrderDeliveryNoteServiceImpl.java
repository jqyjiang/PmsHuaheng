package com.hh.pms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.Delivery;
import com.ruoyi.system.api.domain.OrderDeliveryNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.hh.pms.mapper.OrderDeliveryNoteMapper;
import com.hh.pms.service.IOrderDeliveryNoteService;

/**
 * 订单送货管理Service业务层处理
 *
 * @author yt
 * @date 2023-12-08
 */
@Service
public class OrderDeliveryNoteServiceImpl implements IOrderDeliveryNoteService {
    @Autowired
    private OrderDeliveryNoteMapper orderDeliveryNoteMapper;

    /**
     * 查询订单送货管理
     *
     * @param orderDeliveryNoteId 订单送货管理主键
     * @return 订单送货管理
     */
    @Override
    public OrderDeliveryNote selectOrderDeliveryNoteByOrderDeliveryNoteId(Long orderDeliveryNoteId) {
        return orderDeliveryNoteMapper.selectOrderDeliveryNoteByOrderDeliveryNoteId(orderDeliveryNoteId);
    }

    /**
     * 查询订单送货管理列表
     *
     * @param orderDeliveryNote 订单送货管理
     * @return 订单送货管理
     */
    @Override
    public List<OrderDeliveryNote> selectOrderDeliveryNoteList(OrderDeliveryNote orderDeliveryNote) {
        return orderDeliveryNoteMapper.selectOrderDeliveryNoteList(orderDeliveryNote);
    }

    /**
     * 新增订单送货管理
     *
     * @param orderDeliveryNote 订单送货管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrderDeliveryNote(OrderDeliveryNote orderDeliveryNote) {
        Date date = new Date();
        String deliveryCode = createOrderCode(date);
        orderDeliveryNote.setOrderDeliveryCode(deliveryCode);
        int rows = orderDeliveryNoteMapper.insertOrderDeliveryNote(orderDeliveryNote);
        insertDelivery(orderDeliveryNote);
        return rows;
    }

    /**
     * 修改订单送货管理
     *
     * @param orderDeliveryNote 订单送货管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrderDeliveryNote(OrderDeliveryNote orderDeliveryNote) {
        orderDeliveryNoteMapper.deleteDeliveryByDeliveryId(orderDeliveryNote.getOrderDeliveryNoteId());
        insertDelivery(orderDeliveryNote);
        return orderDeliveryNoteMapper.updateOrderDeliveryNote(orderDeliveryNote);
    }

    /**
     * 批量删除订单送货管理
     *
     * @param orderDeliveryNoteIds 需要删除的订单送货管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderDeliveryNoteByOrderDeliveryNoteIds(Long[] orderDeliveryNoteIds) {
        orderDeliveryNoteMapper.deleteDeliveryByDeliveryIds(orderDeliveryNoteIds);
        return orderDeliveryNoteMapper.deleteOrderDeliveryNoteByOrderDeliveryNoteIds(orderDeliveryNoteIds);
    }

    /**
     * 删除订单送货管理信息
     *
     * @param orderDeliveryNoteId 订单送货管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderDeliveryNoteByOrderDeliveryNoteId(Long orderDeliveryNoteId) {
        orderDeliveryNoteMapper.deleteDeliveryByDeliveryId(orderDeliveryNoteId);
        return orderDeliveryNoteMapper.deleteOrderDeliveryNoteByOrderDeliveryNoteId(orderDeliveryNoteId);
    }

    /**
     * 新增产品明细信息
     *
     * @param orderDeliveryNote 订单送货管理对象
     */
    public void insertDelivery(OrderDeliveryNote orderDeliveryNote) {
        List<Delivery> deliveryList = orderDeliveryNote.getDeliveryList();
        Long orderDeliveryNoteId = orderDeliveryNote.getOrderDeliveryNoteId();
        if (StringUtils.isNotNull(deliveryList)) {
            List<Delivery> list = new ArrayList<Delivery>();
            for (Delivery delivery : deliveryList) {
                delivery.setDeliveryId(orderDeliveryNoteId);
                list.add(delivery);
            }
            if (list.size() > 0) {
                orderDeliveryNoteMapper.batchDelivery(list);
            }
        }
    }

    /**
     * 送货单号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createOrderCode(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String order = orderDeliveryNoteMapper.selectOrderCode(date);
        if (order != null) {
            //如果这个时间存在,说明今天已经有订单生成了
            String orderCode = order;
            orderCode = orderCode.substring(10, 13);
            int num = Integer.valueOf(orderCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "SHD" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%03d", num);//num<100,说明是两位数，前面要补一个0
                String code = "SHD" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "SHD" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        } else {
            //如果今天日期不存在
            String code = "SHD" + newBidDate + "001";
            return code;
        }
    }

    /**
     * //中通、韵达、艾斯客、闵鑫 自动加 1
     *
     * @param deliverynum 物流单号
     * @return 后一个物流单号
     */
    public static String getTwoNum(String deliverynum) {
        String deliveryvalue = "";
        int num = Integer.parseInt(deliverynum);
        num++;
        System.out.println(num);
        int intnum = String.valueOf(num).trim().length();//num 字符长度
        int strnum = deliverynum.trim().length();//输入的物流单号字符长度
            //判断int类型数据是否小于原来值的长度
           //如物流单号是已’0‘开头的，需要把零补上
        String value = "";
        if (intnum < strnum) {
            for (int j = 0; j < (strnum - intnum); j++) {
                value += "0";
            }
        }
        deliveryvalue = value + num;
        return deliveryvalue;
    }

}
