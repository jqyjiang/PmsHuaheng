package com.hh.pms.mast.service;

import java.util.List;
import com.hh.pms.mast.domain.Material;

/**
 * 物料Service接口
 *
 * @author ruoyi
 * @date 2023-11-23
 */
public interface IMaterialService
{
    /**
     * 查询物料
     *
     * @param materialId 物料主键
     * @return 物料
     */
    public Material selectMaterialByMaterialId(Long materialId);

    /**
     * 查询物料列表
     *
     * @param material 物料
     * @return 物料集合
     */
    public List<Material> selectMaterialList(Material material);

    /**
     * 新增物料
     *
     * @param material 物料
     * @return 结果
     */
    public int insertMaterial(Material material);

    /**
     * 修改物料
     *
     * @param material 物料
     * @return 结果
     */
    public int updateMaterial(Material material);

    /**
     * 批量删除物料
     *
     * @param materialIds 需要删除的物料主键集合
     * @return 结果
     */
    public int deleteMaterialByMaterialIds(Long[] materialIds);

    /**
     * 删除物料信息
     *
     * @param materialId 物料主键
     * @return 结果
     */
    public int deleteMaterialByMaterialId(Long materialId);
}