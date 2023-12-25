package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.OrderExecutionDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * mingxiMapper接口
 * 
 * @author ruoyi
 * @date 2023-12-14
 */
public interface OrderExecutionDetailsMapper 
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
    public int updateOrderExecutionDetailsandDelivery(OrderExecutionDetails orderExecutionDetails);
    /**
     * 删除mingxi
     * 
     * @param id mingxi主键
     * @return 结果
     */
    public int deleteOrderExecutionDetailsById(Long id);

    /**
     * 批量删除mingxi
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderExecutionDetailsByIds(Long[] ids);

    @Select("select deliveryNoteNo from order_execution_details where to_days(create_time)= to_days(#{date}) group by deliveryNoteNo order by create_time DESC limit 1")
    public String selectDeliveryCode(@Param("date") Date date);


    public List<OrderExecutionDetails> selectDeliveryNo(@Param("OrderCode") String OrderCode);

    public List<OrderExecutionDetails> selectOrderExecutionDetailsListByOrderCode(@Param("OrderCode") String OrderCode);

    @Update("update order_execution_details set orderStatus=#{orderStatus} where materialName =#{materialName} and orderCode =#{orderCode}")
    int updateMaterialState(@Param("orderStatus") Long orderStatus, @Param("materialName")String materialName, @Param("orderCode")String orderCode);

    List<OrderExecutionDetails> selectOrderDelivery(String orderLineNo);
}
