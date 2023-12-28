package com.hh.pms.service.impl;

import java.util.Calendar;
import java.util.List;

import com.ruoyi.system.api.domain.Information;
import com.ruoyi.system.api.domain.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.hh.pms.mapper.QuestionnaireMapper;
import com.hh.pms.service.IQuestionnaireService;

/**
 * 调查表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-18
 */
@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService 
{
    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    /**
     * 查询调查表
     * 
     * @param questionnaireId 调查表主键
     * @return 调查表
     */
    @Override
    public Questionnaire selectQuestionnaireByQuestionnaireId(Long questionnaireId)
    {
        return questionnaireMapper.selectQuestionnaireByQuestionnaireId(questionnaireId);
    }

    /**
     * 查询调查表列表
     * 
     * @param questionnaire 调查表
     * @return 调查表
     */
    @Override
    public List<Questionnaire> selectQuestionnaireList(Questionnaire questionnaire)
    {
        return questionnaireMapper.selectQuestionnaireList(questionnaire);
    }

    /**
     * 新增调查表
     * 
     * @param questionnaire 调查表
     * @return 结果
     */
    @Transactional
    @Override
    public int insertQuestionnaire(Questionnaire questionnaire)
    {
        questionnaire.setQuestionnaireState(2L);
        Calendar c   =   Calendar.getInstance();//可以用set()对每个时间域单独修改
        int   year   =   c.get(Calendar.YEAR);
        //month都需要+1才表示当前月份
        int   month   =   c.get(Calendar.MONTH);
        int   date   =   c.get(Calendar.DATE);
        int   hour_of_day   =   c.get(Calendar.HOUR_OF_DAY);
        int   minute   =   c.get(Calendar.MINUTE);
        String nowTime = year+""+ (month+1)+""+date+""+hour_of_day+""+minute;
        questionnaire.setQuestionnaireCode("DCB"+nowTime+Math.round(Math.random()*10000));
        int rows = questionnaireMapper.insertQuestionnaire(questionnaire);
        insertInformation(questionnaire);
        return rows;
    }

    /**
     * 修改调查表
     *
     * @param questionnaire 调查表
     * @return 结果
     */
    @Transactional
    @Override
    public int updateQuestionnaire(Questionnaire questionnaire)
    {
        //questionnaire.setQuestionnaireState(4L);
        questionnaireMapper.deleteInformationByQuestionnaireId(questionnaire.getQuestionnaireId());
        insertInformation(questionnaire);
        return questionnaireMapper.updateQuestionnaire(questionnaire);
    }

    /**
     * 批量删除调查表
     *
     * @param questionnaireIds 需要删除的调查表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteQuestionnaireByQuestionnaireIds(Long[] questionnaireIds)
    {
        questionnaireMapper.deleteInformationByQuestionnaireIds(questionnaireIds);
        return questionnaireMapper.deleteQuestionnaireByQuestionnaireIds(questionnaireIds);
    }

    /**
     * 删除调查表信息
     *
     * @param questionnaireId 调查表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteQuestionnaireByQuestionnaireId(Long questionnaireId)
    {
        questionnaireMapper.deleteInformationByQuestionnaireId(questionnaireId);
        return questionnaireMapper.deleteQuestionnaireByQuestionnaireId(questionnaireId);
    }

    /**
     * 新增企业基本信息信息
     *
     * @param questionnaire 调查表对象
     */
    public void insertInformation(Questionnaire questionnaire)
    {
        Information informationList = questionnaire.getInformationList();
        Long questionnaireId = questionnaire.getQuestionnaireId();
        if (StringUtils.isNotNull(informationList))
        {
            informationList.setQuestionnaireId(questionnaireId);
            questionnaireMapper.batchInformation(informationList);
        }
    }
}
