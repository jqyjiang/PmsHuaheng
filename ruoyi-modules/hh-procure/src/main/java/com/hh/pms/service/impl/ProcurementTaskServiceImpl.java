package com.hh.pms.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.OrderManager;
import com.ruoyi.system.api.domain.ProcurementTask;
import com.ruoyi.system.api.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.ProcurementTaskMapper;
import com.hh.pms.service.IProcurementTaskService;

/**
 * 我的需求任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
@Service
public class ProcurementTaskServiceImpl implements IProcurementTaskService 
{
    @Autowired
    private ProcurementTaskMapper procurementTaskMapper;

    /**
     * 查询我的需求任务
     * 
     * @param taskId 我的需求任务主键
     * @return 我的需求任务
     */
    @Override
    public ProcurementTask selectProcurementTaskByTaskId(Long taskId)
    {
        return procurementTaskMapper.selectProcurementTaskByTaskId(taskId);
    }

    /**
     * 查询我的需求任务列表
     * 
     * @param procurementTask 我的需求任务
     * @return 我的需求任务
     */
    @Override
    public List<ProcurementTask> selectProcurementTaskList(ProcurementTask procurementTask)
    {
        return procurementTaskMapper.selectProcurementTaskList(procurementTask);
    }

    /**
     * 新增我的需求任务
     * 
     * @param procurementTask 我的需求任务
     * @return 结果
     */
    @Override
    public int insertProcurementTask(ProcurementTask procurementTask)
    {

        return procurementTaskMapper.insertProcurementTask(procurementTask);
    }

    /**
     * 修改我的需求任务
     * 
     * @param procurementTask 我的需求任务
     * @return 结果
     */
    @Override
    public int updateProcurementTask(ProcurementTask procurementTask)
    {
        return procurementTaskMapper.updateProcurementTask(procurementTask);
    }

    /**
     * 批量删除我的需求任务
     * 
     * @param taskIds 需要删除的我的需求任务主键
     * @return 结果
     */
    @Override
    public int deleteProcurementTaskByTaskIds(Long[] taskIds)
    {
        return procurementTaskMapper.deleteProcurementTaskByTaskIds(taskIds);
    }

    /**
     * 删除我的需求任务信息
     * 
     * @param taskId 我的需求任务主键
     * @return 结果
     */
    @Override
    public int deleteProcurementTaskByTaskId(Long taskId)
    {
        return procurementTaskMapper.deleteProcurementTaskByTaskId(taskId);
    }

    @Override
    public ProcurementTask selectProcurementTaskByTaskCode(String taskCode) {
        return procurementTaskMapper.selectProcurementTaskByTaskCode(taskCode);
    }

    @Override
    public int updateProcurement(ProcurementTask procurementTask) {
        return procurementTaskMapper.updateProcurementTasks(procurementTask);
    }

    @Override
    public int updateProcurementTaskPurchaser(String purchaser,Integer[] taskId) {
        return procurementTaskMapper.updateProcurementTaskPurchaser(purchaser,taskId);
    }

    @Override
    public int addOrderManger(OrderManager orderManager) {
        //先写订单生成
        orderManager.setCreateTime(DateUtils.getNowDate());
        String orderCode = createOrderCode(DateUtils.getNowDate());
        orderManager.setOrderCode(orderCode);
        orderManager.setOrderState(1l);
        orderManager.setOrId(2L);
        orderManager.setMaterialId("云服务器,工业润滑剂");
        orderManager.setTotalDemand(BigDecimal.valueOf(5000));
        return procurementTaskMapper.addOrderManger(orderManager);
    }

    @Override
    public int updateProcurementTaskNumber(String taskOccupied,String acceptedQuantity,String taskAccepted,Integer[] taskId) {
        System.out.println("-------------------------");
        return procurementTaskMapper.updateProcurementTaskNumber(taskOccupied,acceptedQuantity,taskAccepted,taskId);
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
        String order = procurementTaskMapper.selectOrderCode(date);
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
