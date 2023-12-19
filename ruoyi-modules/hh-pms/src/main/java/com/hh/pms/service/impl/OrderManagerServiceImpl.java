package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hh.pms.mapper.OrderMaterialMapper;
import com.hh.pms.model.ProcurementTaskServiceClient;
import com.ruoyi.system.api.domain.OrderManager;
import com.ruoyi.system.api.domain.OrderMaterial;
import com.ruoyi.system.api.domain.ProcurementTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.OrderManagerMapper;
import com.hh.pms.service.IOrderManagerService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 采购订单管理Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-21
 */
@Service
public class OrderManagerServiceImpl implements IOrderManagerService {
    @Autowired
    private OrderManagerMapper orderManagerMapper;

    @Autowired
    private OrderMaterialMapper orderMaterialMapper;

    @Autowired
    private ProcurementTaskServiceClient procurementTaskServiceClient;

    /**
     * 根据执行状态Id查询采购订单列表
     *
     * @param orId
     * @return
     */
    @Override
    public List<OrderManager> selectByOrderTypeRunning(Long orId) {
        return orderManagerMapper.selectByOrderTypeRunning(orId);
    }

    @Override
    public List<OrderManager> selectOrderManagerByOrderCode(String orderCode) {
        return orderManagerMapper.selectOrderManagerByOrderCode(orderCode);
    }

    /**
     * 查询执行状态个数
     *
     * @return
     */
    @Override
    public List<OrderManager> findRunTypeNumber() {
        return orderManagerMapper.findRunTypeNumber();
    }

    /**
     * 查询采购订单管理
     *
     * @param orderId 采购订单管理主键
     * @return 采购订单管理
     */
    @Override
    public OrderManager selectOrderManagerByOrderId(Long orderId) {
        return orderManagerMapper.selectOrderManagerByOrderId(orderId);
    }

    /**
     * 查询采购订单管理列表
     *
     * @param orderManager 采购订单管理
     * @return 采购订单管理
     */
    @Override
    public List<OrderManager> selectOrderManagerList(OrderManager orderManager) {
        return orderManagerMapper.selectOrderManagerList(orderManager);
    }

    /**
     * 新增采购订单管理
     *
     * @param orderManager 采购订单管理
     * @return 结果
     */
    @Override
    @Transactional
    public OrderManager insertOrderManager(OrderManager orderManager) {
        //先写订单生成
        Date date = new Date();
        orderManager.setCreateTime(date);
        String orderCode = createOrderCode(date);
        orderManager.setOrderCode(orderCode);
        orderManager.setOrderState(1l);
        //添加物料基础表
        //物料需求总数
        // BigDecimal totalDemandQuantity = BigDecimal.ZERO;
        List<OrderMaterial> orderMaterialList = orderManager.getOrderMaterialList();
        System.out.println("这是查出来的物料明细表:" + orderMaterialList);
        if (orderMaterialList != null && !orderMaterialList.isEmpty()) {
            boolean allOrderCodesExist = orderMaterialList.stream().allMatch(item -> item.getOrderCode() != null && !item.getOrderCode().isEmpty());
            if (allOrderCodesExist) {
                // 所有OrderMaterial对象的orderCode都存在的逻辑处理,有来源单号说明是需求转订单或者是合同转订单
                // 改变任务单号的受理数量根据物料所添加的数据来修改
                BigDecimal totalDemandQuantity = orderMaterialList.stream()
                        .map(OrderMaterial::getRequireNumber)
                        .filter(Objects::nonNull)
                        .filter(num -> num.compareTo(BigDecimal.ZERO) > 0)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                System.out.println("这是总需求量:" + totalDemandQuantity);
                //先根据任务单号查询任务表
                ProcurementTask procurementTask = procurementTaskServiceClient.selectProcurementTaskByTaskId(orderMaterialList.get(0).getOrderCode());
                System.out.println("这是任务单表:" + procurementTask);
                // 修改任务表状态
                BigDecimal taskAccepted = procurementTask.getTaskAccepted();
                System.out.println("这是查询之后返回的待受理数量:" + taskAccepted);
//                if (taskAccepted.compareTo(totalDemandQuantity) > 0) {
                BigDecimal newTaskAccepted = taskAccepted.subtract(totalDemandQuantity);
                System.out.println("这是相减之后的数" + newTaskAccepted);
                procurementTask.setTaskAccepted(newTaskAccepted);
                //}
                procurementTask.setTaskStatus(2l);
                procurementTaskServiceClient.updateProcurement(procurementTask);
                // 如果物料明细有修改还要考虑修改物料明细表的数据
                orderMaterialMapper.updateOrderMaterials(orderMaterialList);
                // 修改需求申请表状态
            } else {
                // 存在某些OrderMaterial对象的orderCode不存在或为空的逻辑处理
                // ...
                orderMaterialList.forEach(item -> {
                    item.setOrderCode(orderCode);
                });
                // 执行物料明细插入操作
                orderMaterialMapper.insertOrderMaterials(orderMaterialList);
                // 获取自增Id集合
                // 获取自增Id集合并转换为逗号隔开的字符串形式
                StringBuilder orIdBuilder = new StringBuilder();
                for (OrderMaterial item : orderMaterialList) {
                    if (orIdBuilder.length() > 0) {
                        orIdBuilder.append(",");
                    }
                    orIdBuilder.append(item.getOrId());
                }
                String orIdString = orIdBuilder.toString();
                orderManager.setMaterialId(orIdString);
                String connectCode = generateContractNumber();
                orderManager.setContractCode(connectCode);
            }
        } else {
            // 订单物料列表为空的逻辑处理
            // ...

        }
        int rows = orderManagerMapper.insertOrderManager(orderManager);

        return rows > 0 ? orderManager : null;
    }

    /**
     * 修改采购订单管理
     *
     * @param orderManager 采购订单管理
     * @return 结果
     */
    @Override
    public int updateOrderManager(OrderManager orderManager) {
        return orderManagerMapper.updateOrderManager(orderManager);
    }

    /**
     * 批量删除采购订单管理
     *
     * @param orderIds 需要删除的采购订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderManagerByOrderIds(Long[] orderIds) {
        return orderManagerMapper.deleteOrderManagerByOrderIds(orderIds);
    }

    /**
     * 删除采购订单管理信息
     *
     * @param orderId 采购订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderManagerByOrderId(Long orderId) {
        return orderManagerMapper.deleteOrderManagerByOrderId(orderId);
    }

    @Override
    public int updateOrderManagerState(OrderManager orderManager) {
        return orderManagerMapper.updateOrderManagerState(orderManager);
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
        String order = orderManagerMapper.selectOrderCode(date);
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

    /**
     * 根据uuid生成13位的随机数作为合同编号
     *
     * @return
     */
    public static String generateContractNumber() {
        UUID uuid = UUID.randomUUID();
        String contractNumber = uuid.toString().replace("-", "").substring(0, 13);
        return contractNumber;
    }
}
