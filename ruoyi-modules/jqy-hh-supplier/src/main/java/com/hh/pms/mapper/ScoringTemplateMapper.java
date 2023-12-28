package com.hh.pms.mapper;

import java.util.List;

import com.ruoyi.system.api.domain.ScoringDetails;
import com.ruoyi.system.api.domain.ScoringTemplate;

/**
 * 考评模板Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
public interface ScoringTemplateMapper 
{
    /**
     * 查询考评模板
     * 
     * @param scoringTemplateId 考评模板主键
     * @return 考评模板
     */
    public ScoringTemplate selectScoringTemplateByScoringTemplateId(Long scoringTemplateId);

    /**
     * 查询考评模板列表
     * 
     * @param scoringTemplate 考评模板
     * @return 考评模板集合
     */
    public List<ScoringTemplate> selectScoringTemplateList(ScoringTemplate scoringTemplate);

    /**
     * 新增考评模板
     * 
     * @param scoringTemplate 考评模板
     * @return 结果
     */
    public int insertScoringTemplate(ScoringTemplate scoringTemplate);

    /**
     * 修改考评模板
     * 
     * @param scoringTemplate 考评模板
     * @return 结果
     */
    public int updateScoringTemplate(ScoringTemplate scoringTemplate);

    /**
     * 删除考评模板
     * 
     * @param scoringTemplateId 考评模板主键
     * @return 结果
     */
    public int deleteScoringTemplateByScoringTemplateId(Long scoringTemplateId);

    /**
     * 批量删除考评模板
     * 
     * @param scoringTemplateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScoringTemplateByScoringTemplateIds(Long[] scoringTemplateIds);

    /**
     * 批量删除评分明细
     * 
     * @param scoringTemplateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScoringDetailsByScoringTemplateIds(Long[] scoringTemplateIds);
    
    /**
     * 批量新增评分明细
     * 
     * @param scoringDetailsList 评分明细列表
     * @return 结果
     */
    public int batchScoringDetails(List<ScoringDetails> scoringDetailsList);
    

    /**
     * 通过考评模板主键删除评分明细信息
     * 
     * @param scoringTemplateId 考评模板ID
     * @return 结果
     */
    public int deleteScoringDetailsByScoringTemplateId(Long scoringTemplateId);
}
