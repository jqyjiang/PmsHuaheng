package com.hh.pms.service.impl;

import java.util.List;

import com.hh.pms.mapper.MaterialPoolMapper;
import com.hh.pms.mast.service.IMaterialService;
import com.hh.pms.service.IMaterialPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.domain.MaterialPool;

/**
 * 采购需求池Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
@Service
public class MaterialPoolServiceImpl implements IMaterialPoolService
{
    @Autowired
    private MaterialPoolMapper materialPoolMapper;

    /**
     * 查询采购需求池
     * 
     * @param materialId 采购需求池主键
     * @return 采购需求池
     */
    @Override
    public MaterialPool selectMaterialByMaterialId(Long materialId)
    {
        return materialPoolMapper.selectMaterialByMaterialId(materialId);
    }

    /**
     * 查询采购需求池列表
     * 
     * @param materialPool 采购需求池
     * @return 采购需求池
     */
    @Override
    public List<MaterialPool> selectMaterialList(MaterialPool materialPool)
    {
        return materialPoolMapper.selectMaterialList(materialPool);
    }

    /**
     * 新增采购需求池
     * 
     * @param materialPool 采购需求池
     * @return 结果
     */
    @Override
    public int insertMaterial(MaterialPool materialPool)
    {
        return materialPoolMapper.insertMaterial(materialPool);
    }

    /**
     * 修改采购需求池
     * 
     * @param materialPool 采购需求池
     * @return 结果
     */
    @Override
    public int updateMaterial(MaterialPool materialPool)
    {
        return materialPoolMapper.updateMaterial(materialPool);
    }

    /**
     * 批量删除采购需求池
     * 
     * @param materialIds 需要删除的采购需求池主键
     * @return 结果
     */
    @Override
    public int deleteMaterialByMaterialIds(Long[] materialIds)
    {
        return materialPoolMapper.deleteMaterialByMaterialIds(materialIds);
    }

    /**
     * 删除采购需求池信息
     * 
     * @param materialId 采购需求池主键
     * @return 结果
     */
    @Override
    public int deleteMaterialByMaterialId(Long materialId)
    {
        return materialPoolMapper.deleteMaterialByMaterialId(materialId);
    }
}
