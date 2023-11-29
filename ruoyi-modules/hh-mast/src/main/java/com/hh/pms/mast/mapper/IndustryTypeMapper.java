package com.hh.pms.mast.mapper;

import java.util.List;
import com.hh.pms.mast.domain.IndustryType;

/**
 * 行业类型Mapper接口
 *
 * @author ruoyi
 * @date 2023-11-29
 */
public interface IndustryTypeMapper
{
    /**
     * 查询行业类型
     *
     * @param industryTypeId 行业类型主键
     * @return 行业类型
     */
    public IndustryType selectIndustryTypeByIndustryTypeId(Long industryTypeId);

    /**
     * 查询行业类型列表
     *
     * @param industryType 行业类型
     * @return 行业类型集合
     */
    public List<IndustryType> selectIndustryTypeList(IndustryType industryType);

    /**
     * 新增行业类型
     *
     * @param industryType 行业类型
     * @return 结果
     */
    public int insertIndustryType(IndustryType industryType);

    /**
     * 修改行业类型
     *
     * @param industryType 行业类型
     * @return 结果
     */
    public int updateIndustryType(IndustryType industryType);

    /**
     * 删除行业类型
     *
     * @param industryTypeId 行业类型主键
     * @return 结果
     */
    public int deleteIndustryTypeByIndustryTypeId(Long industryTypeId);

    /**
     * 批量删除行业类型
     *
     * @param industryTypeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIndustryTypeByIndustryTypeIds(Long[] industryTypeIds);
}
