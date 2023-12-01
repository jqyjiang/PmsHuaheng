package com.hh.pms.service.impl;

import com.hh.pms.domain.OrderTypeRunning;
import com.hh.pms.mapper.OrderTypeRunningMapper;
import com.hh.pms.service.IOrderTypeRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderTypeRunningServiceImpl implements IOrderTypeRunningService {
    @Autowired
    private OrderTypeRunningMapper orderTypeRunningMapper;
    @Override
    public List<OrderTypeRunning> listName() {
        System.out.println(orderTypeRunningMapper.listName());
        return orderTypeRunningMapper.listName();
    }
}
