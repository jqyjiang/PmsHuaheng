package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.OrderExecutionDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    /**订单业务对账*/
    public List<OrderExecutionDetails> selectReconciliation(OrderExecutionDetails orderExecutionDetails);
    /**手动对账*/
    public int updateReconciliation1(OrderExecutionDetails orderExecutionDetails);
    /**采方确认对账*/
    public int updateReconciliation2(List<Long> ids);
    /**退回对账*/
    public int updateReconciliation3(List<Long> ids);
    /**供方确认对账*/
    public int updateReconciliation4(List<Long> ids);
    /**供方拒绝对账单*/
    public int updateReconciliation5(List<Long> ids);

    public List<OrderExecutionDetails> selectOrderExecutionDetailsListByOrderCode(@Param("OrderCode") String OrderCode);

    @Update("update order_execution_details set orderStatus=#{orderStatus} where materialName =#{materialName} and orderCode =#{orderCode}")
    int updateMaterialState(@Param("orderStatus") Long orderStatus, @Param("materialName")String materialName, @Param("orderCode")String orderCode);

    List<OrderExecutionDetails> selectOrderDelivery(String orderLineNo);
    /**
     * 根据订单号和收货单号查询
     * @param orderCode
     * @param receiptNo
     * @return
     */
    OrderExecutionDetails selectOrderExecutionDetailsByReceiptAndOrderCode(@Param("orderCode") String orderCode, @Param("receiptNo") String receiptNo);

    @Update("update order_execution_details set order_quanlity=#{l} where deliveryNoteNo =#{deliveryNoteNo} and orderCode =#{orderCode} and receiptNoteNo=#{receiptNoteNo}")
    int updateOrderDetailQualityStatus(@Param("orderCode") String orderCode, @Param("receiptNoteNo")String receiptNoteNo, @Param("deliveryNoteNo")String deliveryNoteNo, @Param("l")long l);

    List<OrderExecutionDetails> selectOrderStockInList(OrderExecutionDetails orderExecutionDetails);

    int updateOrderExecutionDetailsReceiptNoAndOrderCode(OrderExecutionDetails orderExecutionDetails);
}
