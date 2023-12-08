package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.MaterialInformation;
import com.ruoyi.system.api.domain.MaterialRequirement;
import com.ruoyi.system.api.domain.ProcurementTask;
import org.apache.ibatis.annotations.Select;

/**
 * 采购需求申请Mapper接口
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public interface MaterialRequirementMapper {
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

    /**
     * 向需求申请表查询是否存在某天
     *
     * @param requirementTime
     * @return
     */

    @Select("SELECT requirement_code FROM material_requirement  WHERE TO_DAYS(create_time)=TO_DAYS(#{requirementTime}) GROUP BY requirement_code ORDER BY create_time DESC LIMIT 1")
    String selectRequirementCode(Date requirementTime);


    /**
     * 添加物料信息
     */
    public int insertRequirementInformations(List<MaterialInformation> materialInformations);


    /**
     * 查询采购需求池列表
     *
     * @param materialInformation 采购需求池
     * @return 采购需求池集合
     */
    public List<MaterialInformation> selectMaterialInformationList(MaterialInformation materialInformation);


    /**
     * 添加采购任务
     */
    public int insertProcurementTask(List<ProcurementTask> procurementTasks);
}
