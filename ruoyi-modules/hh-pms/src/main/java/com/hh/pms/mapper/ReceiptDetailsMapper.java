package com.hh.pms.mapper;

import java.util.List;

import com.ruoyi.system.api.domain.OrderExecutionDetails;
import com.ruoyi.system.api.domain.ReceiptDetails;
import org.apache.ibatis.annotations.Param;

/**
 * 收货明细信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-12-20
 */
public interface ReceiptDetailsMapper
{
    /**
     * 查询收货明细信息
     *
     * @param id 收货明细信息主键
     * @return 收货明细信息
     */
    public ReceiptDetails selectReceiptDetailsById(Long id);

    /**
     * 查询收货明细信息列表
     *
     * @param receiptDetails 收货明细信息
     * @return 收货明细信息集合
     */
    public List<ReceiptDetails> selectReceiptDetailsList(ReceiptDetails receiptDetails);

    /**
     * 根据查询订单号和发货单号查询发货物料信息
     * @param orderCode
     * @param deliverNo
     * @return
     */
    public ReceiptDetails selectOrderExecutionDetailsByDeliveryNo(@Param("orderCode") String orderCode, @Param("deliverNo") String deliverNo);
    /**
     * 新增收货明细信息
     *
     * @param receiptDetails 收货明细信息
     * @return 结果
     */
    public int insertReceiptDetails(ReceiptDetails receiptDetails);

    /**
     * 修改收货明细信息
     *
     * @param receiptDetails 收货明细信息
     * @return 结果
     */
    public int updateReceiptDetails(ReceiptDetails receiptDetails);

    /**
     * 删除收货明细信息
     *
     * @param id 收货明细信息主键
     * @return 结果
     */
    public int deleteReceiptDetailsById(Long id);

    /**
     * 批量删除收货明细信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReceiptDetailsByIds(Long[] ids);
}

