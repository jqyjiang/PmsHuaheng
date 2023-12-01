package com.hh.pms.service;

import java.util.List;
import com.hh.pms.domain.RequirementTable;

/**
 * 需求类型Service接口
 * 
 * @author ruoyi
 * @date 2023-12-01
 */
public interface IRequirementTableService 
{
    /**
     * 查询需求类型
     * 
     * @param requirementId 需求类型主键
     * @return 需求类型
     */
    public RequirementTable selectRequirementTableByRequirementId(Long requirementId);

    /**
     * 查询需求类型列表
     * 
     * @param requirementTable 需求类型
     * @return 需求类型集合
     */
    public List<RequirementTable> selectRequirementTableList(RequirementTable requirementTable);

    /**
     * 新增需求类型
     * 
     * @param requirementTable 需求类型
     * @return 结果
     */
    public int insertRequirementTable(RequirementTable requirementTable);

    /**
     * 修改需求类型
     * 
     * @param requirementTable 需求类型
     * @return 结果
     */
    public int updateRequirementTable(RequirementTable requirementTable);

    /**
     * 批量删除需求类型
     * 
     * @param requirementIds 需要删除的需求类型主键集合
     * @return 结果
     */
    public int deleteRequirementTableByRequirementIds(Long[] requirementIds);

    /**
     * 删除需求类型信息
     * 
     * @param requirementId 需求类型主键
     * @return 结果
     */
    public int deleteRequirementTableByRequirementId(Long requirementId);
}
