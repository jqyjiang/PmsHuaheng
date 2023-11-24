package com.hh.pms.mapper;

import java.util.List;
import com.hh.pms.domain.MaterialRequirement;

/**
 * 采购需求申请Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-24
 */
public interface MaterialRequirementMapper 
{
    /**
     * 查询采购需求申请
     * 
     * @param requirementId 采购需求申请主键
     * @return 采购需求申请
     */
    public MaterialRequirement selectMaterialRequirementByRequirementId(Long requirementId);

    /**
     * 查询采购需求申请列表
     * 
     * @param materialRequirement 采购需求申请
     * @return 采购需求申请集合
     */
    public List<MaterialRequirement> selectMaterialRequirementList(MaterialRequirement materialRequirement);

    /**
     * 新增采购需求申请
     * 
     * @param materialRequirement 采购需求申请
     * @return 结果
     */
    public int insertMaterialRequirement(MaterialRequirement materialRequirement);

    /**
     * 修改采购需求申请
     * 
     * @param materialRequirement 采购需求申请
     * @return 结果
     */
    public int updateMaterialRequirement(MaterialRequirement materialRequirement);

    /**
     * 删除采购需求申请
     * 
     * @param requirementId 采购需求申请主键
     * @return 结果
     */
    public int deleteMaterialRequirementByRequirementId(Long requirementId);

    /**
     * 批量删除采购需求申请
     * 
     * @param requirementIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialRequirementByRequirementIds(Long[] requirementIds);
}
