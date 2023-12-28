package com.hh.pms.mapper;

import java.util.List;
import com.ruoyi.system.api.domain.Indicators;

/**
 * 考评指标Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
public interface IndicatorsMapper 
{
    /**
     * 查询考评指标
     * 
     * @param indicatorId 考评指标主键
     * @return 考评指标
     */
    public Indicators selectIndicatorsByIndicatorId(Long indicatorId);

    /**
     * 查询考评指标列表
     * 
     * @param indicators 考评指标
     * @return 考评指标集合
     */
    public List<Indicators> selectIndicatorsList(Indicators indicators);

    /**
     * 新增考评指标
     * 
     * @param indicators 考评指标
     * @return 结果
     */
    public int insertIndicators(Indicators indicators);

    /**
     * 修改考评指标
     * 
     * @param indicators 考评指标
     * @return 结果
     */
    public int updateIndicators(Indicators indicators);

    /**
     * 删除考评指标
     * 
     * @param indicatorId 考评指标主键
     * @return 结果
     */
    public int deleteIndicatorsByIndicatorId(Long indicatorId);

    /**
     * 批量删除考评指标
     * 
     * @param indicatorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIndicatorsByIndicatorIds(Long[] indicatorIds);
}
