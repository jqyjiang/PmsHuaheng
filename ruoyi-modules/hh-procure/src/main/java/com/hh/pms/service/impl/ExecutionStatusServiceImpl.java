package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.ExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.ExecutionStatusMapper;
import com.hh.pms.service.IExecutionStatusService;

/**
 * 签署执行状态表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-18
 */
@Service
public class ExecutionStatusServiceImpl implements IExecutionStatusService
{
    @Autowired
    private ExecutionStatusMapper executionStatusMapper;

    /**
     * 查询签署执行状态表
     *
     * @param executionId 签署执行状态表主键
     * @return 签署执行状态表
     */
    @Override
    public ExecutionStatus selectExecutionStatusByExecutionId(Long executionId)
    {
        return executionStatusMapper.selectExecutionStatusByExecutionId(executionId);
    }

    /**
     * 查询签署执行状态表列表
     *
     * @param executionStatus 签署执行状态表
     * @return 签署执行状态表
     */
    @Override
    public List<ExecutionStatus> selectExecutionStatusList(ExecutionStatus executionStatus)
    {
        return executionStatusMapper.selectExecutionStatusList(executionStatus);
    }

    /**
     * 新增签署执行状态表
     *
     * @param executionStatus 签署执行状态表
     * @return 结果
     */
    @Override
    public int insertExecutionStatus(ExecutionStatus executionStatus)
    {
        return executionStatusMapper.insertExecutionStatus(executionStatus);
    }

    /**
     * 修改签署执行状态表
     *
     * @param executionStatus 签署执行状态表
     * @return 结果
     */
    @Override
    public int updateExecutionStatus(ExecutionStatus executionStatus)
    {
        return executionStatusMapper.updateExecutionStatus(executionStatus);
    }

    /**
     * 批量删除签署执行状态表
     *
     * @param executionIds 需要删除的签署执行状态表主键
     * @return 结果
     */
    @Override
    public int deleteExecutionStatusByExecutionIds(Long[] executionIds)
    {
        return executionStatusMapper.deleteExecutionStatusByExecutionIds(executionIds);
    }

    /**
     * 删除签署执行状态表信息
     *
     * @param executionId 签署执行状态表主键
     * @return 结果
     */
    @Override
    public int deleteExecutionStatusByExecutionId(Long executionId)
    {
        return executionStatusMapper.deleteExecutionStatusByExecutionId(executionId);
    }
}

