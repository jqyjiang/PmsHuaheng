package com.hh.pms.service;

import java.util.List;
import com.ruoyi.system.api.domain.ScoringTemplate;

/**
 * 考评模板Service接口
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
public interface IScoringTemplateService 
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
     * 批量删除考评模板
     * 
     * @param scoringTemplateIds 需要删除的考评模板主键集合
     * @return 结果
     */
    public int deleteScoringTemplateByScoringTemplateIds(Long[] scoringTemplateIds);

    /**
     * 删除考评模板信息
     * 
     * @param scoringTemplateId 考评模板主键
     * @return 结果
     */
    public int deleteScoringTemplateByScoringTemplateId(Long scoringTemplateId);
}
