package com.hh.pms.mapper;

import java.util.List;
import com.hh.pms.domain.ProcurementTask;

/**
 * 我的需求任务Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
public interface ProcurementTaskMapper 
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
     * 删除我的需求任务
     * 
     * @param taskId 我的需求任务主键
     * @return 结果
     */
    public int deleteProcurementTaskByTaskId(Long taskId);

    /**
     * 批量删除我的需求任务
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcurementTaskByTaskIds(Long[] taskIds);
}
