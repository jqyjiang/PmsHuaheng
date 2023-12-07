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
     * 返回一个执行状态的列表集合
     * @return
     */
    List<RequirementTable> listRequirementTableName();
}
