package com.hh.pms.service;

import java.util.List;
import com.hh.pms.domain.OrderMaterial;

/**
 * 订单物料明细Service接口
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
public interface IOrderMaterialService 
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
     * 修改订单物料明细
     * 
     * @param orderMaterial 订单物料明细
     * @return 结果
     */
    public int updateOrderMaterial(OrderMaterial orderMaterial);

    /**
     * 批量删除订单物料明细
     * 
     * @param orIds 需要删除的订单物料明细主键集合
     * @return 结果
     */
    public int deleteOrderMaterialByOrIds(Long[] orIds);

    /**
     * 删除订单物料明细信息
     * 
     * @param orId 订单物料明细主键
     * @return 结果
     */
    public int deleteOrderMaterialByOrId(Long orId);

    List<OrderMaterial> findTaskMaterial(String taskCode);
}
