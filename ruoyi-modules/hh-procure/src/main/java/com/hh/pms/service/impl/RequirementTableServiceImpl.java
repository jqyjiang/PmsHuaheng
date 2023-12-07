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

    @Override
    public List<RequirementTable> listRequirementTableName() {
        return requirementTableMapper.listRequirementTableName();
    }


}
