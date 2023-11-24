package com.hh.pms.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.MaterialRequirementMapper;
import com.hh.pms.domain.MaterialRequirement;
import com.hh.pms.service.IMaterialRequirementService;

/**
 * 采购需求申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-24
 */
@Service
public class MaterialRequirementServiceImpl implements IMaterialRequirementService 
{
    @Autowired
    private MaterialRequirementMapper materialRequirementMapper;

    /**
     * 查询采购需求申请
     * 
     * @param requirementId 采购需求申请主键
     * @return 采购需求申请
     */
    @Override
    public MaterialRequirement selectMaterialRequirementByRequirementId(Long requirementId)
    {
        return materialRequirementMapper.selectMaterialRequirementByRequirementId(requirementId);
    }

    /**
     * 查询采购需求申请列表
     * 
     * @param materialRequirement 采购需求申请
     * @return 采购需求申请
     */
    @Override
    public List<MaterialRequirement> selectMaterialRequirementList(MaterialRequirement materialRequirement)
    {
        return materialRequirementMapper.selectMaterialRequirementList(materialRequirement);
    }

    /**
     * 新增采购需求申请
     * 
     * @param materialRequirement 采购需求申请
     * @return 结果
     */
    @Override
    public int insertMaterialRequirement(MaterialRequirement materialRequirement)
    {
        materialRequirement.setCreateTime(DateUtils.getNowDate());
        return materialRequirementMapper.insertMaterialRequirement(materialRequirement);
    }

    /**
     * 修改采购需求申请
     * 
     * @param materialRequirement 采购需求申请
     * @return 结果
     */
    @Override
    public int updateMaterialRequirement(MaterialRequirement materialRequirement)
    {
        return materialRequirementMapper.updateMaterialRequirement(materialRequirement);
    }

    /**
     * 批量删除采购需求申请
     * 
     * @param requirementIds 需要删除的采购需求申请主键
     * @return 结果
     */
    @Override
    public int deleteMaterialRequirementByRequirementIds(Long[] requirementIds)
    {
        return materialRequirementMapper.deleteMaterialRequirementByRequirementIds(requirementIds);
    }

    /**
     * 删除采购需求申请信息
     * 
     * @param requirementId 采购需求申请主键
     * @return 结果
     */
    @Override
    public int deleteMaterialRequirementByRequirementId(Long requirementId)
    {
        return materialRequirementMapper.deleteMaterialRequirementByRequirementId(requirementId);
    }
}
