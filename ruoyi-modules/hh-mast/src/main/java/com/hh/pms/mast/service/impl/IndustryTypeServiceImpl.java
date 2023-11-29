package com.hh.pms.mast.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.IndustryTypeMapper;
import com.hh.pms.mast.domain.IndustryType;
import com.hh.pms.mast.service.IIndustryTypeService;

/**
 * 行业类型Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-29
 */
@Service
public class IndustryTypeServiceImpl implements IIndustryTypeService
{
    @Autowired
    private IndustryTypeMapper industryTypeMapper;

    /**
     * 查询行业类型
     *
     * @param industryTypeId 行业类型主键
     * @return 行业类型
     */
    @Override
    public IndustryType selectIndustryTypeByIndustryTypeId(Long industryTypeId)
    {
        return industryTypeMapper.selectIndustryTypeByIndustryTypeId(industryTypeId);
    }

    /**
     * 查询行业类型列表
     *
     * @param industryType 行业类型
     * @return 行业类型
     */
    @Override
    public List<IndustryType> selectIndustryTypeList(IndustryType industryType)
    {
        return industryTypeMapper.selectIndustryTypeList(industryType);
    }

    /**
     * 新增行业类型
     *
     * @param industryType 行业类型
     * @return 结果
     */
    @Override
    public int insertIndustryType(IndustryType industryType)
    {
        return industryTypeMapper.insertIndustryType(industryType);
    }

    /**
     * 修改行业类型
     *
     * @param industryType 行业类型
     * @return 结果
     */
    @Override
    public int updateIndustryType(IndustryType industryType)
    {
        return industryTypeMapper.updateIndustryType(industryType);
    }

    /**
     * 批量删除行业类型
     *
     * @param industryTypeIds 需要删除的行业类型主键
     * @return 结果
     */
    @Override
    public int deleteIndustryTypeByIndustryTypeIds(Long[] industryTypeIds)
    {
        return industryTypeMapper.deleteIndustryTypeByIndustryTypeIds(industryTypeIds);
    }

    /**
     * 删除行业类型信息
     *
     * @param industryTypeId 行业类型主键
     * @return 结果
     */
    @Override
    public int deleteIndustryTypeByIndustryTypeId(Long industryTypeId)
    {
        return industryTypeMapper.deleteIndustryTypeByIndustryTypeId(industryTypeId);
    }
}
