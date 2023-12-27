package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.QualityOrder;
import com.ruoyi.system.api.domain.QualityTaskList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 质检单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public interface QualityOrderMapper 
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
     * 删除质检单
     * 
     * @param qualityId 质检单主键
     * @return 结果
     */
    public int deleteQualityOrderByQualityId(Long qualityId);

    /**
     * 批量删除质检单
     * 
     * @param qualityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQualityOrderByQualityIds(Long[] qualityIds);

    @Select("select quality_code from quality_order where to_days(create_time)= to_days(#{orderTime}) group by quality_code order by create_time DESC limit 1")
    String selectQualityCode(@Param("orderTime") Date nowDate);


}
