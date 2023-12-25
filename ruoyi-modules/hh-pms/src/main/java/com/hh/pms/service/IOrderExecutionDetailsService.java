package com.hh.pms.service;

import java.util.List;
import com.ruoyi.system.api.domain.OrderExecutionDetails;

/**
 * mingxiService接口
 * 
 * @author ruoyi
 * @date 2023-12-14
 */
public interface IOrderExecutionDetailsService 
{
    /**
     * 查询mingxi
     * 
     * @param id mingxi主键
     * @return mingxi
     */
    public OrderExecutionDetails selectOrderExecutionDetailsById(Long id);

    /**
     * 查询mingxi列表
     * 
     * @param orderExecutionDetails mingxi
     * @return mingxi集合
     */
    public List<OrderExecutionDetails> selectOrderExecutionDetailsList(OrderExecutionDetails orderExecutionDetails);

    /**
     * 新增mingxi
     * 
     * @param orderExecutionDetails mingxi
     * @return 结果
     */
    public int insertOrderExecutionDetails(OrderExecutionDetails orderExecutionDetails);

    /**
     * 修改mingxi
     * 
     * @param orderExecutionDetails mingxi
     * @return 结果
     */
    public int updateOrderExecutionDetails(OrderExecutionDetails orderExecutionDetails);

    /**
     * 修改mingxi
     *
     * @param orderExecutionDetails mingxi
     * @return 结果
     */
    public int updateOrderExecutionDetailsandDelivery(OrderExecutionDetails orderExecutionDetails) throws Exception;

    /**
     * 批量删除mingxi
     * 
     * @param ids 需要删除的mingxi主键集合
     * @return 结果
     */
    public int deleteOrderExecutionDetailsByIds(Long[] ids);

    /**
     * 删除mingxi信息
     * 
     * @param id mingxi主键
     * @return 结果
     */
    public int deleteOrderExecutionDetailsById(Long id);

    /**
     * 查询收货管理列表
     *
     * @param orderExecutionDetails mingxi
     * @return mingxi集合
     */
    public List<OrderExecutionDetails> selectOrderExecutionDeliveryList(OrderExecutionDetails orderExecutionDetails);

    /**
     * 查询收货单列表
     * @param orderExecutionDetails
     * @return
     */
    List<OrderExecutionDetails> selectOrderReceiptList(OrderExecutionDetails orderExecutionDetails);
}
