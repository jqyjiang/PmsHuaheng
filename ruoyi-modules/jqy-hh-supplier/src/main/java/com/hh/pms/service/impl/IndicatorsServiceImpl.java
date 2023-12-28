package com.hh.pms.service.impl;

import java.util.Calendar;
import java.util.List;

import com.ruoyi.system.api.domain.Indicators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.IndicatorsMapper;
import com.hh.pms.service.IIndicatorsService;

/**
 * 考评指标Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
@Service
public class IndicatorsServiceImpl implements IIndicatorsService 
{
    @Autowired
    private IndicatorsMapper indicatorsMapper;

    /**
     * 查询考评指标
     * 
     * @param indicatorId 考评指标主键
     * @return 考评指标
     */
    @Override
    public Indicators selectIndicatorsByIndicatorId(Long indicatorId)
    {
        return indicatorsMapper.selectIndicatorsByIndicatorId(indicatorId);
    }

    /**
     * 查询考评指标列表
     * 
     * @param indicators 考评指标
     * @return 考评指标
     */
    @Override
    public List<Indicators> selectIndicatorsList(Indicators indicators)
    {
        return indicatorsMapper.selectIndicatorsList(indicators);
    }

    /**
     * 新增考评指标
     * 
     * @param indicators 考评指标
     * @return 结果
     */
    @Override
    public int insertIndicators(Indicators indicators)
    {
        Calendar c = Calendar.getInstance();//可以用set()对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        //month都需要+1才表示当前月份
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour_of_day = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String nowTime = year + "" + (month + 1) + "" + date + "" + hour_of_day + "" + minute;
        indicators.setIndicatorCode("ZB" + nowTime + Math.round(Math.random() * 10000));
        indicators.setConfiguration("0");
        indicators.setState("0");
        return indicatorsMapper.insertIndicators(indicators);
    }

    /**
     * 修改考评指标
     * 
     * @param indicators 考评指标
     * @return 结果
     */
    @Override
    public int updateIndicators(Indicators indicators)
    {
        return indicatorsMapper.updateIndicators(indicators);
    }

    /**
     * 批量删除考评指标
     * 
     * @param indicatorIds 需要删除的考评指标主键
     * @return 结果
     */
    @Override
    public int deleteIndicatorsByIndicatorIds(Long[] indicatorIds)
    {
        return indicatorsMapper.deleteIndicatorsByIndicatorIds(indicatorIds);
    }

    /**
     * 删除考评指标信息
     * 
     * @param indicatorId 考评指标主键
     * @return 结果
     */
    @Override
    public int deleteIndicatorsByIndicatorId(Long indicatorId)
    {
        return indicatorsMapper.deleteIndicatorsByIndicatorId(indicatorId);
    }
}
