package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.MaterialInformation;

import java.util.List;

/**
 * 采购需求池Mapper接口
 *
 * @author ruoyi
 * @date 2023-11-27
 */
public interface MaterialInformationMapper
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
     * 删除采购需求池
     *
     * @param miId 采购需求池主键
     * @return 结果
     */
    public int deleteMaterialInformationByMiId(Integer miId);

    /**
     * 批量删除采购需求池
     *
     * @param miIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialInformationByMiIds(Integer[] miIds);

    //  暂挂
    public int updateRequirementStatus(Integer[] miId);

    // 作废
    public int updateRequirementStatusCancel(Integer[] miId);



}
