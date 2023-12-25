package com.hh.pms.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.system.api.domain.OrderReceipt;
import com.ruoyi.system.api.domain.ReceiptDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 订单收货管理Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-20
 */
public interface OrderReceiptMapper 
{
    /**
     * 查询订单收货管理
     * 
     * @param receiptId 订单收货管理主键
     * @return 订单收货管理
     */
    public OrderReceipt selectOrderReceiptByReceiptId(Long receiptId);

    /**
     * 查询订单收货管理列表
     * 
     * @param orderReceipt 订单收货管理
     * @return 订单收货管理集合
     */
    public List<OrderReceipt> selectOrderReceiptList(OrderReceipt orderReceipt);

    /**
     * 新增订单收货管理
     * 
     * @param orderReceipt 订单收货管理
     * @return 结果
     */
    public int insertOrderReceipt(OrderReceipt orderReceipt);

    /**
     * 修改订单收货管理
     * 
     * @param orderReceipt 订单收货管理
     * @return 结果
     */
    public int updateOrderReceipt(OrderReceipt orderReceipt);

    /**
     * 删除订单收货管理
     * 
     * @param receiptId 订单收货管理主键
     * @return 结果
     */
    public int deleteOrderReceiptByReceiptId(Long receiptId);

    /**
     * 批量删除订单收货管理
     * 
     * @param receiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderReceiptByReceiptIds(Long[] receiptIds);

    /**
     * 批量删除收货明细信息
     * 
     * @param receiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReceiptDetailsByDeliveryNoteNos(Long[] receiptIds);
    
    /**
     * 批量新增收货明细信息
     * 
     * @param receiptDetailsList 收货明细信息列表
     * @return 结果
     */
    public int batchReceiptDetails(List<ReceiptDetails> receiptDetailsList);
    

    /**
     * 通过订单收货管理主键删除收货明细信息信息
     * 
     * @param receiptId 订单收货管理ID
     * @return 结果
     */
    public int deleteReceiptDetailsByDeliveryNoteNo(Long receiptId);

    @Select("select receiptNoteNo from order_receipt where to_days(create_time)= to_days(#{date}) group by receiptNoteNo order by create_time DESC limit 1")
    String selectReceiptCode(@Param("date") Date date);
}
