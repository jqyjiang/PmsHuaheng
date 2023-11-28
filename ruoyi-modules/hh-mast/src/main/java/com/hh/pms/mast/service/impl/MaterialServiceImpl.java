package com.hh.pms.mast.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.MaterialMapper;
import com.hh.pms.mast.domain.Material;
import com.hh.pms.mast.service.IMaterialService;

/**
 * 物料Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
@Service
public class MaterialServiceImpl implements IMaterialService 
{
    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 查询物料
     * 
     * @param materialId 物料主键
     * @return 物料
     */
    @Override
    public Material selectMaterialByMaterialId(Long materialId)
    {
        return materialMapper.selectMaterialByMaterialId(materialId);
    }

    /**
     * 查询物料列表
     * 
     * @param material 物料
     * @return 物料
     */
    @Override
    public List<Material> selectMaterialList(Material material)
    {
        return materialMapper.selectMaterialList(material);
    }

    /**
     * 新增物料
     * 
     * @param material 物料
     * @return 结果
     */
    @Override
    public int insertMaterial(Material material)
    {
        return materialMapper.insertMaterial(material);
    }

    /**
     * 修改物料
     * 
     * @param material 物料
     * @return 结果
     */
    @Override
    public int updateMaterial(Material material)
    {
        return materialMapper.updateMaterial(material);
    }

    /**
     * 批量删除物料
     * 
     * @param materialIds 需要删除的物料主键
     * @return 结果
     */
    @Override
    public int deleteMaterialByMaterialIds(Long[] materialIds)
    {
        return materialMapper.deleteMaterialByMaterialIds(materialIds);
    }

    /**
     * 删除物料信息
     * 
     * @param materialId 物料主键
     * @return 结果
     */
    @Override
    public int deleteMaterialByMaterialId(Long materialId)
    {
        return materialMapper.deleteMaterialByMaterialId(materialId);
    }
}
