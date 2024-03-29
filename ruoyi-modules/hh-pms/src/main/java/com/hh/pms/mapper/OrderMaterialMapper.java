package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.OrderMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单物料明细Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
public interface OrderMaterialMapper 
{
    /**
     * 查询订单物料明细
     * 
     * @param orId 订单物料明细主键
     * @return 订单物料明细
     */
    public OrderMaterial selectOrderMaterialByOrId(Long orId);

    /**
     * 查询订单物料明细列表
     * 
     * @param orderMaterial 订单物料明细
     * @return 订单物料明细集合
     */
    public List<OrderMaterial> selectOrderMaterialList(OrderMaterial orderMaterial);

    /**
     * 新增订单物料明细
     * 
     * @param orderMaterial 订单物料明细
     * @return 结果
     */
    public int insertOrderMaterial(OrderMaterial orderMaterial);

    /**
     * 批量新增订单物料明细
     * @param orderMaterials 订单物料明细
     * @return 结束
     */
    public int insertOrderMaterials(List<OrderMaterial> orderMaterials);

    /**
     * 根据任务单号查询物料明细
     * @param taskCode
     * @return
     */
    List<OrderMaterial> findTaskMaterial(String taskCode);

    /**
     * 根据来源单号查询物料明细
     * @param orderCode
     * @return
     */
    List<OrderMaterial> findByOrderCodeMaterial(String orderCode);
    /**
     * 修改订单物料明细
     * 
     * @param orderMaterial 订单物料明细
     * @return 结果
     */
    public int updateOrderMaterial(OrderMaterial orderMaterial);

    /**
     * 批量修改订单物料明细
     * @param orderMaterial
     * @return
     */
    public int updateOrderMaterials(List<OrderMaterial> orderMaterial);
    /**
     * 删除订单物料明细
     * 
     * @param orId 订单物料明细主键
     * @return 结果
     */
    public int deleteOrderMaterialByOrId(Long orId);
    /**
     * 根据订单编号删除订单物料明细
     *
     * @param orderCode 订单物料明细主键
     * @return 结果
     */
    public int deleteOrderMaterialByOrderCode(String orderCode);

    /**
     * 批量删除订单物料明细
     * 
     * @param orIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderMaterialByOrIds(Long[] orIds);

    /**
     * 根据物料名称和订单编号查询品类
     * @param orName
     * @param orderCode
     * @return
     */
    OrderMaterial selectOrderMaterialByOrNameAndOrderCode(@Param("orName") String orName,@Param("orderCode") String orderCode);

    /**
     * 根据收货单号和订单号查询
     * @param orderCode
     * @param orName
     * @return
     */
    OrderMaterial findByOrderCodeMaterialReceipt(@Param("orderCode") String orderCode, @Param("orName") String orName);
}
