package com.hh.pms.service;
import java.util.List;

import com.ruoyi.system.api.domain.Delivery;
import com.ruoyi.system.api.domain.OrderDeliveryNote;
import org.apache.ibatis.annotations.Param;

/**
 * 产品明细Service接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface IDeliveryService 
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
     * 批量删除产品明细
     * 
     * @param deliveryIds 需要删除的产品明细主键集合
     * @return 结果
     */
    public int deleteDeliveryByDeliveryIds(Long[] deliveryIds);

    /**
     * 删除产品明细信息
     * 
     * @param deliveryId 产品明细主键
     * @return 结果
     */
    public int deleteDeliveryByDeliveryId(Long deliveryId);


}
