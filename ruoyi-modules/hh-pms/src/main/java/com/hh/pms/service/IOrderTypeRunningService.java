package com.hh.pms.service;

import com.ruoyi.system.api.domain.OrderTypeRunning;

import java.util.List;

public interface IOrderTypeRunningService {
    /**
     * 返回一个执行状态的列表集合
     * @return
     */
    public List<OrderTypeRunning> listName();
}
