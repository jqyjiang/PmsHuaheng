package com.hh.pms.service;

import com.ruoyi.system.api.domain.OrderManager;
import com.ruoyi.system.api.domain.ProcurementTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 我的需求任务Service接口
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
public interface IProcurementTaskService 
{
    /**
     * 查询我的需求任务
     * 
     * @param taskId 我的需求任务主键
     * @return 我的需求任务
     */
    public ProcurementTask selectProcurementTaskByTaskId(Long taskId);

    /**
     * 查询我的需求任务列表
     * 
     * @param procurementTask 我的需求任务
     * @return 我的需求任务集合
     */
    public List<ProcurementTask> selectProcurementTaskList(ProcurementTask procurementTask);

    /**
     * 新增我的需求任务
     * 
     * @param procurementTask 我的需求任务
     * @return 结果
     */
    public int insertProcurementTask(ProcurementTask procurementTask);

    /**
     * 修改我的需求任务
     * 
     * @param procurementTask 我的需求任务
     * @return 结果
     */
    public int updateProcurementTask(ProcurementTask procurementTask);

    /**
     * 批量删除我的需求任务
     * 
     * @param taskIds 需要删除的我的需求任务主键集合
     * @return 结果
     */
    public int deleteProcurementTaskByTaskIds(Long[] taskIds);

    /**
     * 删除我的需求任务信息
     * 
     * @param taskId 我的需求任务主键
     * @return 结果
     */
    public int deleteProcurementTaskByTaskId(Long taskId);

    ProcurementTask selectProcurementTaskByTaskCode(String taskCode);

    int updateProcurement(ProcurementTask procurementTask);

    // 转办
    int updateProcurementTaskPurchaser(String purchaser,Integer[] taskId);
    // 受理
    int addOrderManger(OrderManager orderManager);
    int updateProcurementTaskNumber(String taskOccupied,String acceptedQuantity,String taskAccepted,Integer[] taskId);
}
