package com.hh.pms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.RequirementTableMapper;
import com.hh.pms.domain.RequirementTable;
import com.hh.pms.service.IRequirementTableService;

/**
 * 需求类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-01
 */
@Service
public class RequirementTableServiceImpl implements IRequirementTableService 
{
    @Autowired
    private RequirementTableMapper requirementTableMapper;

    /**
     * 查询需求类型
     * 
     * @param requirementId 需求类型主键
     * @return 需求类型
     */
    @Override
    public RequirementTable selectRequirementTableByRequirementId(Long requirementId)
    {
        return requirementTableMapper.selectRequirementTableByRequirementId(requirementId);
    }

    /**
     * 查询需求类型列表
     * 
     * @param requirementTable 需求类型
     * @return 需求类型
     */
    @Override
    public List<RequirementTable> selectRequirementTableList(RequirementTable requirementTable)
    {
        return requirementTableMapper.selectRequirementTableList(requirementTable);
    }

    /**
     * 新增需求类型
     * 
     * @param requirementTable 需求类型
     * @return 结果
     */
    @Override
    public int insertRequirementTable(RequirementTable requirementTable)
    {
        return requirementTableMapper.insertRequirementTable(requirementTable);
    }

    /**
     * 修改需求类型
     * 
     * @param requirementTable 需求类型
     * @return 结果
     */
    @Override
    public int updateRequirementTable(RequirementTable requirementTable)
    {
        return requirementTableMapper.updateRequirementTable(requirementTable);
    }

    /**
     * 批量删除需求类型
     * 
     * @param requirementIds 需要删除的需求类型主键
     * @return 结果
     */
    @Override
    public int deleteRequirementTableByRequirementIds(Long[] requirementIds)
    {
        return requirementTableMapper.deleteRequirementTableByRequirementIds(requirementIds);
    }

    /**
     * 删除需求类型信息
     * 
     * @param requirementId 需求类型主键
     * @return 结果
     */
    @Override
    public int deleteRequirementTableByRequirementId(Long requirementId)
    {
        return requirementTableMapper.deleteRequirementTableByRequirementId(requirementId);
    }
}
