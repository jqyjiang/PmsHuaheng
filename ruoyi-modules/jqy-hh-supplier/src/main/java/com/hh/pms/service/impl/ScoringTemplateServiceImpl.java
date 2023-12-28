package com.hh.pms.service.impl;

import java.util.Calendar;
import java.util.List;

import com.ruoyi.system.api.domain.ScoringDetails;
import com.ruoyi.system.api.domain.ScoringTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.hh.pms.mapper.ScoringTemplateMapper;
import com.hh.pms.service.IScoringTemplateService;

/**
 * 考评模板Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
@Service
public class ScoringTemplateServiceImpl implements IScoringTemplateService 
{
    @Autowired
    private ScoringTemplateMapper scoringTemplateMapper;

    /**
     * 查询考评模板
     * 
     * @param scoringTemplateId 考评模板主键
     * @return 考评模板
     */
    @Override
    public ScoringTemplate selectScoringTemplateByScoringTemplateId(Long scoringTemplateId)
    {
        return scoringTemplateMapper.selectScoringTemplateByScoringTemplateId(scoringTemplateId);
    }

    /**
     * 查询考评模板列表
     * 
     * @param scoringTemplate 考评模板
     * @return 考评模板
     */
    @Override
    public List<ScoringTemplate> selectScoringTemplateList(ScoringTemplate scoringTemplate)
    {
        return scoringTemplateMapper.selectScoringTemplateList(scoringTemplate);
    }

    /**
     * 新增考评模板
     * 
     * @param scoringTemplate 考评模板
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScoringTemplate(ScoringTemplate scoringTemplate)
    {
        Calendar c   =   Calendar.getInstance();//可以用set()对每个时间域单独修改
        int   year   =   c.get(Calendar.YEAR);
        //month都需要+1才表示当前月份
        int   month   =   c.get(Calendar.MONTH);
        int   date   =   c.get(Calendar.DATE);
        int   hour_of_day   =   c.get(Calendar.HOUR_OF_DAY);
        int   minute   =   c.get(Calendar.MINUTE);
        String nowTime = year+""+ (month+1)+""+date+""+hour_of_day+""+minute;
        scoringTemplate.setScoringTemplateCode("KPMB"+nowTime+Math.round(Math.random()*10000));
        scoringTemplate.setTemplateState(2L);
        int rows = scoringTemplateMapper.insertScoringTemplate(scoringTemplate);
        insertScoringDetails(scoringTemplate);
        return rows;
    }

    /**
     * 修改考评模板
     * 
     * @param scoringTemplate 考评模板
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScoringTemplate(ScoringTemplate scoringTemplate)
    {
        scoringTemplateMapper.deleteScoringDetailsByScoringTemplateId(scoringTemplate.getScoringTemplateId());
        insertScoringDetails(scoringTemplate);
        return scoringTemplateMapper.updateScoringTemplate(scoringTemplate);
    }

    /**
     * 批量删除考评模板
     * 
     * @param scoringTemplateIds 需要删除的考评模板主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScoringTemplateByScoringTemplateIds(Long[] scoringTemplateIds)
    {
        scoringTemplateMapper.deleteScoringDetailsByScoringTemplateIds(scoringTemplateIds);
        return scoringTemplateMapper.deleteScoringTemplateByScoringTemplateIds(scoringTemplateIds);
    }

    /**
     * 删除考评模板信息
     * 
     * @param scoringTemplateId 考评模板主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScoringTemplateByScoringTemplateId(Long scoringTemplateId)
    {
        scoringTemplateMapper.deleteScoringDetailsByScoringTemplateId(scoringTemplateId);
        return scoringTemplateMapper.deleteScoringTemplateByScoringTemplateId(scoringTemplateId);
    }

    /**
     * 新增评分明细信息
     * 
     * @param scoringTemplate 考评模板对象
     */
    public void insertScoringDetails(ScoringTemplate scoringTemplate)
    {
        List<ScoringDetails> scoringDetailsList = scoringTemplate.getScoringDetailsList();
        Long scoringTemplateId = scoringTemplate.getScoringTemplateId();
        if (StringUtils.isNotNull(scoringDetailsList))
        {
            List<ScoringDetails> list = new ArrayList<ScoringDetails>();
            for (ScoringDetails scoringDetails : scoringDetailsList)
            {
                scoringDetails.setScoringTemplateId(scoringTemplateId);
                list.add(scoringDetails);
            }
            if (list.size() > 0)
            {
                scoringTemplateMapper.batchScoringDetails(list);
            }
        }
    }
}
