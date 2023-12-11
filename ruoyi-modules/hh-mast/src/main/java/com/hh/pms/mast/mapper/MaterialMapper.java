package com.hh.pms.mast.mapper;

import com.ruoyi.system.api.domain.Material;

import java.util.List;

/**
 * 物料维护Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-29
 */
public interface MaterialMapper 
{
    /**
     * 查询物料维护
     * 
     * @param materialId 物料维护主键
     * @return 物料维护
     */
    public Material selectMaterialByMaterialId(Long materialId);

    /**
     * 查询物料维护列表
     * 
     * @param material 物料维护
     * @return 物料维护集合
     */
    public List<Material> selectMaterialList(Material material);

    /**
     * 新增物料维护
     * 
     * @param material 物料维护
     * @return 结果
     */
    public int insertMaterial(Material material);

    /**
     * 修改物料维护
     * 
     * @param material 物料维护
     * @return 结果
     */
    public int updateMaterial(Material material);

    /**
     * 删除物料维护
     * 
     * @param materialId 物料维护主键
     * @return 结果
     */
    public int deleteMaterialByMaterialId(Long materialId);

    /**
     * 批量删除物料维护
     * 
     * @param materialIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialByMaterialIds(Long[] materialIds);
}
