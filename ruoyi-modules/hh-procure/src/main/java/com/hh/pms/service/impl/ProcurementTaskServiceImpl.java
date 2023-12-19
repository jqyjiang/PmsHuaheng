package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.ProcurementTask;
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


}
