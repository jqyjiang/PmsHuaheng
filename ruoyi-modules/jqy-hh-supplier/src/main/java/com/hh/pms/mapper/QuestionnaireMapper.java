package com.hh.pms.mapper;

import java.util.List;
import com.ruoyi.system.api.domain.Information;
import com.ruoyi.system.api.domain.Questionnaire;

/**
 * 调查表Mapper接口
 *
 * @author ruoyi
 * @date 2023-12-19
 */
public interface QuestionnaireMapper
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
     * 删除调查表
     *
     * @param questionnaireId 调查表主键
     * @return 结果
     */
    public int deleteQuestionnaireByQuestionnaireId(Long questionnaireId);

    /**
     * 批量删除调查表
     *
     * @param questionnaireIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionnaireByQuestionnaireIds(Long[] questionnaireIds);

    /**
     * 批量删除企业基本信息
     *
     * @param questionnaireIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInformationByQuestionnaireIds(Long[] questionnaireIds);

    /**
     * 批量新增企业基本信息
     *
     * @param informationList 企业基本信息列表
     * @return 结果
     */
    public int batchInformation(Information informationList);


    /**
     * 通过调查表主键删除企业基本信息信息
     *
     * @param questionnaireId 调查表ID
     * @return 结果
     */
    public int deleteInformationByQuestionnaireId(Long questionnaireId);
}
