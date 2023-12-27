package com.hh.pms.service;

import java.util.List;

import com.ruoyi.system.api.domain.QualityOrder;

/**
 * 质检单Service接口
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public interface IQualityOrderService 
{
    /**
     * 查询质检单
     * 
     * @param qualityId 质检单主键
     * @return 质检单
     */
    public QualityOrder selectQualityOrderByQualityId(Long qualityId);

    /**
     * 查询质检单列表
     * 
     * @param qualityOrder 质检单
     * @return 质检单集合
     */
    public List<QualityOrder> selectQualityOrderList(QualityOrder qualityOrder);

    /**
     * 新增质检单
     * 
     * @param qualityOrder 质检单
     * @return 结果
     */
    public int insertQualityOrder(QualityOrder qualityOrder);

    /**
     * 修改质检单
     * 
     * @param qualityOrder 质检单
     * @return 结果
     */
    public int updateQualityOrder(QualityOrder qualityOrder);

    /**
     * 批量删除质检单
     * 
     * @param qualityIds 需要删除的质检单主键集合
     * @return 结果
     */
    public int deleteQualityOrderByQualityIds(Long[] qualityIds);

    /**
     * 删除质检单信息
     * 
     * @param qualityId 质检单主键
     * @return 结果
     */
    public int deleteQualityOrderByQualityId(Long qualityId);

}
