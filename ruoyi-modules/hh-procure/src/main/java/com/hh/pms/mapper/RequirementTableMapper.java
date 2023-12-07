package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.RequirementTable;

import java.util.List;

/**
 * 需求类型Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-01
 */
public interface RequirementTableMapper 
{
    /**
     * 返回一个执行状态的列表集合
     * @return
     */
    List<RequirementTable> listRequirementTableName();

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


}
