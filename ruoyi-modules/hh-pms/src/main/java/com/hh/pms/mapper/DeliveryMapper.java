package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.Delivery;

import java.util.List;


/**
 * 产品明细Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface DeliveryMapper 
{
    /**
     * 查询产品明细
     * 
     * @param deliveryId 产品明细主键
     * @return 产品明细
     */
    public List<Delivery> selectDeliveryByDeliveryId(Long deliveryId);

    /**
     * 查询产品明细列表
     * 
     * @param delivery 产品明细
     * @return 产品明细集合
     */
    public List<Delivery> selectDeliveryList(Delivery delivery);

    /**
     * 新增产品明细
     * 
     * @param delivery 产品明细
     * @return 结果
     */
    public int insertDelivery(Delivery delivery);

    /**
     * 修改产品明细
     * 
     * @param delivery 产品明细
     * @return 结果
     */
    public int updateDelivery(Delivery delivery);

    /**
     * 删除产品明细
     * 
     * @param deliveryId 产品明细主键
     * @return 结果
     */
    public int deleteDeliveryByDeliveryId(Long deliveryId);

    /**
     * 批量删除产品明细
     * 
     * @param deliveryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeliveryByDeliveryIds(Long[] deliveryIds);
}
