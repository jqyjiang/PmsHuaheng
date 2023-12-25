package com.hh.pms.service.impl;

import com.hh.pms.mapper.ReceiptDetailsMapper;
import com.hh.pms.service.IReceiptDetailsService;
import com.ruoyi.system.api.domain.OrderExecutionDetails;
import com.ruoyi.system.api.domain.ReceiptDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author yang
 */
@Service
public class ReceiptDetailsServiceImpl implements IReceiptDetailsService {
    @Autowired
    private ReceiptDetailsMapper receiptDetailsMapper;
    @Override
    public ReceiptDetails selectReceiptDetailsById(Long id) {
        return receiptDetailsMapper.selectReceiptDetailsById(id);
    }

    @Override
    public List<ReceiptDetails> selectReceiptDetailsList(ReceiptDetails receiptDetails) {
        return receiptDetailsMapper.selectReceiptDetailsList(receiptDetails);
    }

    @Override
    public ReceiptDetails selectOrderExecutionDetailsByDeliveryNo(String orderCode, String deliverNo) {
        return receiptDetailsMapper.selectOrderExecutionDetailsByDeliveryNo(orderCode, deliverNo);
    }

    @Override
    public int insertReceiptDetails(ReceiptDetails receiptDetails) {
        return receiptDetailsMapper.insertReceiptDetails(receiptDetails);
    }

    @Override
    public int updateReceiptDetails(ReceiptDetails receiptDetails) {
        return receiptDetailsMapper.updateReceiptDetails(receiptDetails);
    }

    @Override
    public int deleteReceiptDetailsById(Long id) {
        return receiptDetailsMapper.deleteReceiptDetailsById(id);
    }

    @Override
    public int deleteReceiptDetailsByIds(Long[] ids) {
        return receiptDetailsMapper.deleteReceiptDetailsByIds(ids);
    }
}
