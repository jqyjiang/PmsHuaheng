package com.hh.pms.service;

import java.util.List;
import com.hh.pms.domain.ProcurementTask;

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
}
