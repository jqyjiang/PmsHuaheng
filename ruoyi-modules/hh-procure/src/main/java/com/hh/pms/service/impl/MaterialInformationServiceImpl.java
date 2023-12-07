package com.hh.pms.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.MaterialInformationMapper;
import com.hh.pms.domain.MaterialInformation;
import com.hh.pms.service.IMaterialInformationService;

/**
 * 采购需求池Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
@Service
public class MaterialInformationServiceImpl implements IMaterialInformationService 
{
    @Autowired
    private MaterialInformationMapper materialInformationMapper;

    /**
     * 查询采购需求池
     * 
     * @param miId 采购需求池主键
     * @return 采购需求池
     */
    @Override
    public MaterialInformation selectMaterialInformationByMiId(Integer miId)
    {
        return materialInformationMapper.selectMaterialInformationByMiId(miId);
    }

    /**
     * 查询采购需求池列表
     * 
     * @param materialInformation 采购需求池
     * @return 采购需求池
     */
    @Override
    public List<MaterialInformation> selectMaterialInformationList(MaterialInformation materialInformation)
    {
        return materialInformationMapper.selectMaterialInformationList(materialInformation);
    }

    /**
     * 新增采购需求池
     * 
     * @param materialInformation 采购需求池
     * @return 结果
     */
    @Override
    public int insertMaterialInformation(MaterialInformation materialInformation)
    {
        return materialInformationMapper.insertMaterialInformation(materialInformation);
    }

    /**
     * 修改采购需求池
     * 
     * @param materialInformation 采购需求池
     * @return 结果
     */
    @Override
    public int updateMaterialInformation(MaterialInformation materialInformation)
    {
        return materialInformationMapper.updateMaterialInformation(materialInformation);
    }

    /**
     * 批量删除采购需求池
     * 
     * @param miIds 需要删除的采购需求池主键
     * @return 结果
     */
    @Override
    public int deleteMaterialInformationByMiIds(Integer[] miIds)
    {
        return materialInformationMapper.deleteMaterialInformationByMiIds(miIds);
    }

    /**
     * 删除采购需求池信息
     * 
     * @param miId 采购需求池主键
     * @return 结果
     */
    @Override
    public int deleteMaterialInformationByMiId(Integer miId)
    {
        return materialInformationMapper.deleteMaterialInformationByMiId(miId);
    }

    @Override
    public int updateRequirementStatus(Integer[] miId) {
        return materialInformationMapper.updateRequirementStatus(miId);
    }

    @Override
    public int updateRequirementStatusCancel(Integer[] miId) {
        return materialInformationMapper.updateRequirementStatusCancel(miId);
    }


}
