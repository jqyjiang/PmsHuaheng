package com.hh.pms.service;

import com.ruoyi.system.api.domain.MaterialInformation;

import java.util.List;

/**
 * 采购需求池Service接口
 *
 * @author ruoyi
 * @date 2023-11-29
 */
public interface IMaterialInformationService
{
    /**
     * 查询采购需求池
     *
     * @param miId 采购需求池主键
     * @return 采购需求池
     */
    public MaterialInformation selectMaterialInformationByMiId(Integer miId);

    /**
     * 查询采购需求池列表
     *
     * @param materialInformation 采购需求池
     * @return 采购需求池集合
     */
    public List<MaterialInformation> selectMaterialInformationList(MaterialInformation materialInformation);

    /**
     * 新增采购需求池
     *
     * @param materialInformation 采购需求池
     * @return 结果
     */
    public int insertMaterialInformation(MaterialInformation materialInformation);

    /**
     * 修改采购需求池
     *
     * @param materialInformation 采购需求池
     * @return 结果
     */
    public int updateMaterialInformation(MaterialInformation materialInformation);

    /**
     * 批量删除采购需求池
     *
     * @param miIds 需要删除的采购需求池主键集合
     * @return 结果
     */
    public int deleteMaterialInformationByMiIds(Integer[] miIds);

    /**
     * 删除采购需求池信息
     *
     * @param miId 采购需求池主键
     * @return 结果
     */
    public int deleteMaterialInformationByMiId(Integer miId);


    //  暂挂
    public int updateRequirementStatus(Integer[] miId);

    // 作废
    public int updateRequirementStatusCancel(Integer[] miId);

    // 分配
    public int updateRequirementStatusAllocation(String purchaser,Integer[] miId);
    public int updateProcurementTask(String purchaser,String requirementCode);


}
