package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.MaterialPool;

import java.util.List;

/**
 * 采购需求池Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
public interface MaterialPoolMapper
{
    /**
     * 查询采购需求池
     * 
     * @param materialId 采购需求池主键
     * @return 采购需求池
     */
    public MaterialPool selectMaterialByMaterialId(Long materialId);

    /**
     * 查询采购需求池列表
     * 
     * @param materialPool 采购需求池
     * @return 采购需求池集合
     */
    public List<MaterialPool> selectMaterialList(MaterialPool materialPool);

    /**
     * 新增采购需求池
     * 
     * @param materialPool 采购需求池
     * @return 结果
     */
    public int insertMaterial(MaterialPool materialPool);

    /**
     * 修改采购需求池
     * 
     * @param materialPool 采购需求池
     * @return 结果
     */
    public int updateMaterial(MaterialPool materialPool);

    /**
     * 删除采购需求池
     * 
     * @param materialId 采购需求池主键
     * @return 结果
     */
    public int deleteMaterialByMaterialId(Long materialId);

    /**
     * 批量删除采购需求池
     * 
     * @param materialIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialByMaterialIds(Long[] materialIds);
}
