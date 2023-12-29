package com.hh.pms.mast.service;

import com.ruoyi.system.api.domain.Material;
import com.ruoyi.system.api.domain.MaterialInformation;
import com.ruoyi.system.api.domain.OrderManager;
import com.ruoyi.system.api.domain.MaterialInformation;
import com.ruoyi.system.api.domain.OrderExecutionDetails;
import com.ruoyi.system.api.domain.OrderManager;

import java.util.List;

/**
 * 物料维护Service接口
 *
 * @author ruoyi
 * @date 2023-11-29
 */
public interface IMaterialService
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
     * 批量删除物料维护
     *
     * @param materialIds 需要删除的物料维护主键集合
     * @return 结果
     */
    public int deleteMaterialByMaterialIds(Long[] materialIds);

    /**
     * 删除物料维护信息
     *
     * @param materialId 物料维护主键
     * @return 结果
     */
    public int deleteMaterialByMaterialId(Long materialId);

    //统计分析月度需求数量统计
    List<MaterialInformation> selectMonthDemand();
    //统计分析需求状态
    List<MaterialInformation> selectDemandStatus();
    //统计分析订单来源系统
    List<OrderManager> selectOrderSource();
    //统计分析月度订单
    List<OrderManager> selectMonthOrder();
    //统计分析月度订单金额
    List<OrderExecutionDetails> selectMonthOrderMoney();
    //待收货订单
    Long selTreatNum();
    //待收货金额
    Long selTreatMoney();
    //已完成订单
    Long selStopNum();
    //已完成金额
    Long selCompleteMoney();
    //需求申请数
    Long selApplyNum();
    //对账结算申请数
    Long selReconciliationRefuseNum();
    //对账结算拒绝数
    Long selReconciliationNum();
    //总订单数
    Long selOrderTotal();
    //总收货金额
    Long selOrderTotalAmount();
    //总合同数
    Long selMentNum();
    //合同未激活数
    Long selMentNumNo();
    //质检总数
    Long selInspectTotal();
    //质检未执行数
    Long selInspectTotalNo();

}
