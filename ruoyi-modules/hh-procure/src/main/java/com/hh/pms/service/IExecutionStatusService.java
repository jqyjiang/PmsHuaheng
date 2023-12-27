package com.hh.pms.service;

import com.ruoyi.system.api.domain.ExecutionStatus;

import java.util.List;

/**
 * 签署执行状态表Service接口
 *
 * @author ruoyi
 * @date 2023-12-18
 */
public interface IExecutionStatusService
{
    /**
     * 查询签署执行状态表
     *
     * @param executionId 签署执行状态表主键
     * @return 签署执行状态表
     */
    public ExecutionStatus selectExecutionStatusByExecutionId(Long executionId);

    /**
     * 查询签署执行状态表列表
     *
     * @param executionStatus 签署执行状态表
     * @return 签署执行状态表集合
     */
    public List<ExecutionStatus> selectExecutionStatusList(ExecutionStatus executionStatus);

    /**
     * 新增签署执行状态表
     *
     * @param executionStatus 签署执行状态表
     * @return 结果
     */
    public int insertExecutionStatus(ExecutionStatus executionStatus);

    /**
     * 修改签署执行状态表
     *
     * @param executionStatus 签署执行状态表
     * @return 结果
     */
    public int updateExecutionStatus(ExecutionStatus executionStatus);

    /**
     * 批量删除签署执行状态表
     *
     * @param executionIds 需要删除的签署执行状态表主键集合
     * @return 结果
     */
    public int deleteExecutionStatusByExecutionIds(Long[] executionIds);

    /**
     * 删除签署执行状态表信息
     *
     * @param executionId 签署执行状态表主键
     * @return 结果
     */
    public int deleteExecutionStatusByExecutionId(Long executionId);
}
