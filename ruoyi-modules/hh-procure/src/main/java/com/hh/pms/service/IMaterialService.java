package com.hh.pms.service;

import java.util.List;
import com.hh.pms.domain.Material;

/**
 * 采购需求池Service接口
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
public interface IMaterialService 
{
    /**
     * 查询采购需求池
     * 
     * @param materialId 采购需求池主键
     * @return 采购需求池
     */
    public Material selectMaterialByMaterialId(Long materialId);

    /**
     * 查询采购需求池列表
     * 
     * @param material 采购需求池
     * @return 采购需求池集合
     */
    public List<Material> selectMaterialList(Material material);

    /**
     * 新增采购需求池
     * 
     * @param material 采购需求池
     * @return 结果
     */
    public int insertMaterial(Material material);

    /**
     * 修改采购需求池
     * 
     * @param material 采购需求池
     * @return 结果
     */
    public int updateMaterial(Material material);

    /**
     * 批量删除采购需求池
     * 
     * @param materialIds 需要删除的采购需求池主键集合
     * @return 结果
     */
    public int deleteMaterialByMaterialIds(Long[] materialIds);

    /**
     * 删除采购需求池信息
     * 
     * @param materialId 采购需求池主键
     * @return 结果
     */
    public int deleteMaterialByMaterialId(Long materialId);
}
