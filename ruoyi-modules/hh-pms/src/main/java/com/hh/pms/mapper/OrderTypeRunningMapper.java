package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.OrderTypeRunning;

import java.util.List;

public interface OrderTypeRunningMapper {
    /**
     * 返回一个执行状态的列表集合
     * @return
     */
    public List<OrderTypeRunning> listName();

    /**
     * 查询订单类型
     *
     * @param ortId 订单类型主键
     * @return 订单类型
     */
    public OrderTypeRunning selectOrderTypeRunningByOrtId(Long ortId);
}
