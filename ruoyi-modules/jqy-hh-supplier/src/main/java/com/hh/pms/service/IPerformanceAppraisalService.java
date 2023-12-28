package com.hh.pms.service;

import java.util.List;
import com.ruoyi.system.api.domain.PerformanceAppraisal;

/**
 * 绩效考评Service接口
 * 
 * @author ruoyi
 * @date 2023-12-22
 */
public interface IPerformanceAppraisalService
{
    /**
     * 查询绩效考评
     *
     * @param evaluationId 绩效考评主键
     * @return 绩效考评
     */
    public PerformanceAppraisal selectPerformanceAppraisalByEvaluationId(Long evaluationId);

    /**
     * 查询绩效考评列表
     *
     * @param performanceAppraisal 绩效考评
     * @return 绩效考评集合
     */
    public List<PerformanceAppraisal> selectPerformanceAppraisalList(PerformanceAppraisal performanceAppraisal);

    /**
     * 新增绩效考评
     *
     * @param performanceAppraisal 绩效考评
     * @return 结果
     */
    public int insertPerformanceAppraisal(PerformanceAppraisal performanceAppraisal) throws CloneNotSupportedException;

    /**
     * 修改绩效考评
     *
     * @param performanceAppraisal 绩效考评
     * @return 结果
     */
    public int updatePerformanceAppraisal(PerformanceAppraisal performanceAppraisal) throws CloneNotSupportedException;

    /**
     * 批量删除绩效考评
     *
     * @param evaluationIds 需要删除的绩效考评主键集合
     * @return 结果
     */
    public int deletePerformanceAppraisalByEvaluationIds(Long[] evaluationIds);

    /**
     * 删除绩效考评信息
     *
     * @param evaluationId 绩效考评主键
     * @return 结果
     */
    public int deletePerformanceAppraisalByEvaluationId(Long evaluationId);
}
