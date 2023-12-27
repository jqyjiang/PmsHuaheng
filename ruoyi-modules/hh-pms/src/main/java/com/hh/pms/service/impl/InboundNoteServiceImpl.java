package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hh.pms.mapper.OrderExecutionDetailsMapper;
import com.hh.pms.mapper.OrderManagerMapper;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.domain.InboundMaterial;
import com.ruoyi.system.api.domain.InboundNote;
import com.ruoyi.system.api.domain.OrderExecutionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.hh.pms.mapper.InboundNoteMapper;

import com.hh.pms.service.IInboundNoteService;

/**
 * 订单入库管理Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-25
 */
@Service
public class InboundNoteServiceImpl implements IInboundNoteService {
    @Autowired
    private InboundNoteMapper inboundNoteMapper;

    @Autowired
    private OrderExecutionDetailsMapper orderExecutionDetailsMapper;

    @Autowired
    private OrderManagerMapper orderManagerMapper;

    /**
     * 查询订单入库管理
     *
     * @param id 订单入库管理主键
     * @return 订单入库管理
     */
    @Override
    public InboundNote selectInboundNoteById(Long id) {
        return inboundNoteMapper.selectInboundNoteById(id);
    }

    /**
     * 查询订单入库管理列表
     *
     * @param inboundNote 订单入库管理
     * @return 订单入库管理
     */
    @Override
    public List<InboundNote> selectInboundNoteList(InboundNote inboundNote) {
        return inboundNoteMapper.selectInboundNoteList(inboundNote);
    }

    /**
     * 新增订单入库管理
     *
     * @param inboundNote 订单入库管理
     * @return 结果
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int insertInboundNote(InboundNote inboundNote) {
        //跟条件相同的单条物流下的需求数量
        BigDecimal requireNumber = BigDecimal.ZERO;
        //这是总需求量的变量
        BigDecimal totalDemand = BigDecimal.ZERO;

        String ORDER = null;
        String orName = null;
        inboundNote.setCreatedTime(DateUtils.getNowDate());
        String orderCode = createOrderCode(DateUtils.getNowDate());//生成入库单号
        inboundNote.setStockInNoteNo(orderCode);
        int rows = inboundNoteMapper.insertInboundNote(inboundNote);//入库单表的插入
        OrderExecutionDetails orderExecutionDetails1 = new OrderExecutionDetails();
        //查询订单执行明细里面的相同订单下的物料信息
        List<OrderExecutionDetails> orderExecutionDetails = orderExecutionDetailsMapper.selectOrderExecutionDetailsListByOrderCode(inboundNote.getInboundMaterialList().get(0).getOrderCode());
        OrderExecutionDetails orderExecutionDetails2 = orderExecutionDetails.stream()
                .filter(order -> order.getMaterialName().equals(inboundNote.getInboundMaterialList().get(0).getOrName()))
                .findFirst()
                .orElse(null);

        if (orderExecutionDetails1 != null) {
            // 找到相同的物料
            orderExecutionDetails1.setStockInNoteNo(orderCode);
            orderExecutionDetails1.setReceiveQuantity(inboundNote.getReturnQuantity());
            orderExecutionDetails1.setStockInDate(DateUtils.getNowDate());
            orderExecutionDetails1.setStockInPerson(inboundNote.getStockInPerson());
            orderExecutionDetails1.setStockInQuantity(inboundNote.getStockInQuantity());
            orderExecutionDetails1.setStockInAmount(inboundNote.getStockInAmount());
            //获得该物料下的需求数量
            requireNumber = orderExecutionDetails2.getRequiredQuantity();
            //获得总需求量
            totalDemand = orderExecutionDetails2.getTotalDemand();
            //获得订单编号
            ORDER = orderExecutionDetails2.getOrderCode();
            //获得物料名称
            orName = orderExecutionDetails2.getMaterialName();
            if (inboundNote.getStockInQuantity().compareTo(requireNumber) == 0) {
                orderExecutionDetailsMapper.updateMaterialState(2l, orName, ORDER);//修改单条物料执行明细的状态
            }
            // 将 orderExecutionDetails1 对象保存到数据库中  条件是id
            orderExecutionDetailsMapper.updateOrderExecutionDetails(orderExecutionDetails1);
        }
        //计算总入库量 有则计算 无则加勉   计算单条物料的需求量是否等于入库量 如果=那么就将状态改成已完成  如果总入库量=总需求量那么这个订单就改为已完成
        // 计算 orderExecutionDetails 集合中所有物料的入库总量
        BigDecimal totalStockInQuantity = orderExecutionDetails.stream()
                .map(OrderExecutionDetails::getStockInQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalStockInQuantity.compareTo(totalDemand) == 0) {
            //表示已到需求量,将改物料所属订单号下的物料改为已完成
            orderManagerMapper.updateOrIDByOrderCode(7l, orderCode);
        }

        //insertInboundMaterial(inboundNote);
        return rows;
    }

    /**
     * 修改订单入库管理
     *
     * @param inboundNote 订单入库管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateInboundNote(InboundNote inboundNote) {
        inboundNoteMapper.deleteInboundMaterialById(inboundNote.getId());
        insertInboundMaterial(inboundNote);
        return inboundNoteMapper.updateInboundNote(inboundNote);
    }

    /**
     * 批量删除订单入库管理
     *
     * @param ids 需要删除的订单入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundNoteByIds(Long[] ids) {
        inboundNoteMapper.deleteInboundMaterialByIds(ids);
        return inboundNoteMapper.deleteInboundNoteByIds(ids);
    }

    /**
     * 删除订单入库管理信息
     *
     * @param id 订单入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundNoteById(Long id) {
        inboundNoteMapper.deleteInboundMaterialById(id);
        return inboundNoteMapper.deleteInboundNoteById(id);
    }

    @Override
    public List<InboundMaterial> selectInboundNoteByOrderCode(String orderCode, String orName) {
        return inboundNoteMapper.selectInboundNoteByOrderCode(orderCode, orName);
    }

    /**
     * 新增入库单物料信息
     *
     * @param inboundNote 订单入库管理对象
     */
    public void insertInboundMaterial(InboundNote inboundNote) {
        List<InboundMaterial> inboundMaterialList = inboundNote.getInboundMaterialList();
        Long id = inboundNote.getId();
        if (StringUtils.isNotNull(inboundMaterialList)) {
            List<InboundMaterial> list = new ArrayList<InboundMaterial>();
            for (InboundMaterial inboundMaterial : inboundMaterialList) {
                inboundMaterial.setId(id);
                list.add(inboundMaterial);
            }
            if (list.size() > 0) {
                inboundNoteMapper.batchInboundMaterial(list);
            }
        }
    }

    /**
     * 入库编号生成方法
     *
     * @param date 传入当前日期
     * @return 订单编号
     */
    public String createOrderCode(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newBidDate = dateFormat.format(date).replace("-", "");//把2020-11-11中-去掉
        String order = inboundNoteMapper.selectOrderCode(date);
        if (order != null) {
            //如果这个时间存在,说明今天已经有订单生成了
            String orderCode = order;
            orderCode = orderCode.substring(10, 13);
            int num = Integer.valueOf(orderCode);
            num++;
            if (num < 10) {
                String idNum = String.format("%03d", num);  //num<10,说明是个位数，前面要补两个0
                String code = "PO" + newBidDate + idNum;
                return code;
            } else if (num < 100) {
                String idNum = String.format("%03d", num);//num<100,说明是两位数，前面要补一个0
                String code = "PO" + newBidDate + idNum;
                return code;
            } else {
                String idNum = String.valueOf(num);
                String code = "PO" + newBidDate + idNum;  //date = 20201111+124
                return code;
            }
        } else {
            //如果今天时间不存在
            String code = "PO" + newBidDate + "001";
            return code;
        }
    }
}