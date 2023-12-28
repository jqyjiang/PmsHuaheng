package com.hh.pms.service;

import java.util.List;
import com.ruoyi.system.api.domain.Questionnaire;

/**
 * 调查表Service接口
 *
 * @author ruoyi
 * @date 2023-12-19
 */
public interface IQuestionnaireService
{
    /**
     * 查询调查表
     *
     * @param questionnaireId 调查表主键
     * @return 调查表
     */
    public Questionnaire selectQuestionnaireByQuestionnaireId(Long questionnaireId);

    /**
     * 查询调查表列表
     *
     * @param questionnaire 调查表
     * @return 调查表集合
     */
    public List<Questionnaire> selectQuestionnaireList(Questionnaire questionnaire);

    /**
     * 新增调查表
     *
     * @param questionnaire 调查表
     * @return 结果
     */
    public int insertQuestionnaire(Questionnaire questionnaire);

    /**
     * 修改调查表
     *
     * @param questionnaire 调查表
     * @return 结果
     */
    public int updateQuestionnaire(Questionnaire questionnaire);

    /**
     * 批量删除调查表
     *
     * @param questionnaireIds 需要删除的调查表主键集合
     * @return 结果
     */
    public int deleteQuestionnaireByQuestionnaireIds(Long[] questionnaireIds);

    /**
     * 删除调查表信息
     *
     * @param questionnaireId 调查表主键
     * @return 结果
     */
    public int deleteQuestionnaireByQuestionnaireId(Long questionnaireId);
}
