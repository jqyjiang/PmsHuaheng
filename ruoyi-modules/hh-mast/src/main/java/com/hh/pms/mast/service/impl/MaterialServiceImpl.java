package com.hh.pms.mast.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.Material;
import com.ruoyi.system.api.domain.MaterialInformation;
import com.ruoyi.system.api.domain.OrderExecutionDetails;
import com.ruoyi.system.api.domain.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.MaterialMapper;
import com.hh.pms.mast.service.IMaterialService;

/**
 * 物料维护Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-29
 */
@Service
public class MaterialServiceImpl implements IMaterialService
{
    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 查询物料维护
     *
     * @param materialId 物料维护主键
     * @return 物料维护
     */
    @Override
    public Material selectMaterialByMaterialId(Long materialId)
    {
        return materialMapper.selectMaterialByMaterialId(materialId);
    }

    /**
     * 查询物料维护列表
     *
     * @param material 物料维护
     * @return 物料维护
     */
    @Override
    public List<Material> selectMaterialList(Material material)
    {
        return materialMapper.selectMaterialList(material);
    }

    /**
     * 新增物料维护
     *
     * @param material 物料维护
     * @return 结果
     */
    @Override
    public int insertMaterial(Material material)
    {
        return materialMapper.insertMaterial(material);
    }

    /**
     * 修改物料维护
     *
     * @param material 物料维护
     * @return 结果
     */
    @Override
    public int updateMaterial(Material material)
    {
        return materialMapper.updateMaterial(material);
    }

    /**
     * 批量删除物料维护
     *
     * @param materialIds 需要删除的物料维护主键
     * @return 结果
     */
    @Override
    public int deleteMaterialByMaterialIds(Long[] materialIds)
    {
        return materialMapper.deleteMaterialByMaterialIds(materialIds);
    }

    /**
     * 删除物料维护信息
     *
     * @param materialId 物料维护主键
     * @return 结果
     */
    @Override
    public int deleteMaterialByMaterialId(Long materialId)
    {
        return materialMapper.deleteMaterialByMaterialId(materialId);
    }

    @Override
    public List<MaterialInformation> selectMonthDemand() {
        return materialMapper.selectMonthDemand();
    }

    @Override
    public List<MaterialInformation> selectDemandStatus() {
        return materialMapper.selectDemandStatus();
    }

    @Override
    public List<OrderManager> selectOrderSource() {
        return materialMapper.selectOrderSource();
    }

    @Override
    public List<OrderManager> selectMonthOrder() {
        return materialMapper.selectMonthOrder();
    }

    @Override
    public List<OrderExecutionDetails> selectMonthOrderMoney() {
        return materialMapper.selectMonthOrderMoney();
    }

    @Override
    public Long selTreatNum() {
        return materialMapper.selTreatNum();
    }

    @Override
    public Long selTreatMoney() {
        return materialMapper.selTreatMoney();
    }

    @Override
    public Long selStopNum() {
        return materialMapper.selStopNum();
    }

    @Override
    public Long selCompleteMoney() {
        return materialMapper.selCompleteMoney();
    }


}
