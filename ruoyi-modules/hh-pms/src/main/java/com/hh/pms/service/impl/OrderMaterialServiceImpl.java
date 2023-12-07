package com.hh.pms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.OrderMaterialMapper;
import com.hh.pms.domain.OrderMaterial;
import com.hh.pms.service.IOrderMaterialService;

/**
 * 订单物料明细Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
@Service
public class OrderMaterialServiceImpl implements IOrderMaterialService 
{
    @Autowired
    private OrderMaterialMapper orderMaterialMapper;

    /**
     * 查询订单物料明细
     * 
     * @param orId 订单物料明细主键
     * @return 订单物料明细
     */
    @Override
    public OrderMaterial selectOrderMaterialByOrId(Long orId)
    {
        return orderMaterialMapper.selectOrderMaterialByOrId(orId);
    }

    /**
     * 查询订单物料明细列表
     * 
     * @param orderMaterial 订单物料明细
     * @return 订单物料明细
     */
    @Override
    public List<OrderMaterial> selectOrderMaterialList(OrderMaterial orderMaterial)
    {
        return orderMaterialMapper.selectOrderMaterialList(orderMaterial);
    }

    /**
     * 新增订单物料明细
     * 
     * @param orderMaterial 订单物料明细
     * @return 结果
     */
    @Override
    public int insertOrderMaterial(OrderMaterial orderMaterial)
    {
        return orderMaterialMapper.insertOrderMaterial(orderMaterial);
    }

    /**
     * 修改订单物料明细
     * 
     * @param orderMaterial 订单物料明细
     * @return 结果
     */
    @Override
    public int updateOrderMaterial(OrderMaterial orderMaterial)
    {
        return orderMaterialMapper.updateOrderMaterial(orderMaterial);
    }

    /**
     * 批量删除订单物料明细
     * 
     * @param orIds 需要删除的订单物料明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderMaterialByOrIds(Long[] orIds)
    {
        return orderMaterialMapper.deleteOrderMaterialByOrIds(orIds);
    }

    /**
     * 删除订单物料明细信息
     * 
     * @param orId 订单物料明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderMaterialByOrId(Long orId)
    {
        return orderMaterialMapper.deleteOrderMaterialByOrId(orId);
    }

    @Override
    public List<OrderMaterial> findTaskMaterial(String taskCode) {
        return orderMaterialMapper.findTaskMaterial(taskCode);
    }
}
