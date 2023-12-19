package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.Delivery;
import com.ruoyi.system.api.domain.OrderDeliveryNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.DeliveryMapper;

import com.hh.pms.service.IDeliveryService;

/**
 * 产品明细Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@Service
public class DeliveryServiceImpl implements IDeliveryService 
{
    @Autowired
    private DeliveryMapper deliveryMapper;

    /**
     * 查询产品明细
     * 
     * @param deliveryId 产品明细主键
     * @return 产品明细
     */
    @Override
    public List<Delivery> selectDeliveryByDeliveryId(Long deliveryId)
    {
        return deliveryMapper.selectDeliveryByDeliveryId(deliveryId);
    }

    /**
     * 查询产品明细列表
     * 
     * @param delivery 产品明细
     * @return 产品明细
     */
    @Override
    public List<Delivery> selectDeliveryList(Delivery delivery)
    {
        return deliveryMapper.selectDeliveryList(delivery);
    }

    /**
     * 新增产品明细
     * 
     * @param delivery 产品明细
     * @return 结果
     */
    @Override
    public int insertDelivery(Delivery delivery)
    {
        return deliveryMapper.insertDelivery(delivery);
    }

    /**
     * 修改产品明细
     * 
     * @param delivery 产品明细
     * @return 结果
     */
    @Override
    public int updateDelivery(Delivery delivery)
    {
        return deliveryMapper.updateDelivery(delivery);
    }

    /**
     * 批量删除产品明细
     * 
     * @param deliveryIds 需要删除的产品明细主键
     * @return 结果
     */
    @Override
    public int deleteDeliveryByDeliveryIds(Long[] deliveryIds)
    {
        return deliveryMapper.deleteDeliveryByDeliveryIds(deliveryIds);
    }

    /**
     * 删除产品明细信息
     * 
     * @param deliveryId 产品明细主键
     * @return 结果
     */
    @Override
    public int deleteDeliveryByDeliveryId(Long deliveryId)
    {
        return deliveryMapper.deleteDeliveryByDeliveryId(deliveryId);
    }


}
