package com.hh.pms.mapper;

import java.util.List;

import com.ruoyi.system.api.domain.AttendSupplier;
import com.ruoyi.system.api.domain.PerformanceAppraisal;
import com.ruoyi.system.api.domain.ScoringDetails;

/**
 * 绩效考评Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-22
 */
public interface PerformanceAppraisalMapper 
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
    public int insertPerformanceAppraisal(PerformanceAppraisal performanceAppraisal);

    /**
     * 修改绩效考评
     * 
     * @param performanceAppraisal 绩效考评
     * @return 结果
     */
    public int updatePerformanceAppraisal(PerformanceAppraisal performanceAppraisal);

    /**
     * 删除绩效考评
     * 
     * @param evaluationId 绩效考评主键
     * @return 结果
     */
    public int deletePerformanceAppraisalByEvaluationId(Long evaluationId);

    /**
     * 批量删除绩效考评
     * 
     * @param evaluationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerformanceAppraisalByEvaluationIds(Long[] evaluationIds);

    /**
     * 批量删除参评供应商
     * 
     * @param evaluationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendSupplierByEvaluationIds(Long[] evaluationIds);
    
    /**
     * 批量新增参评供应商
     * 
     * @param attendSupplierList 参评供应商列表
     * @return 结果
     */
    public int batchAttendSupplier(List<AttendSupplier> attendSupplierList);
    

    /**
     * 通过绩效考评主键删除参评供应商信息
     * 
     * @param evaluationId 绩效考评ID
     * @return 结果
     */
    public int deleteAttendSupplierByEvaluationId(Long evaluationId);

    /**
     * 批量删除评分明细
     *
     * @param evaluationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScoringDetailsByEvaluationIds(Long[] evaluationIds);

    /**
     * 批量新增评分明细
     *
     * @param scoringDetailsList 评分明细列表
     * @return 结果
     */
    public int batchScoringDetails(List<ScoringDetails> scoringDetailsList);


    /**
     * 通过绩效考评主键删除评分明细信息
     *
     * @param evaluationId 绩效考评ID
     * @return 结果
     */
    public int deleteScoringDetailsByEvaluationId(Long evaluationId);
}
