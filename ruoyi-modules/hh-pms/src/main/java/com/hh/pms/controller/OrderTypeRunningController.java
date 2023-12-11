package com.hh.pms.controller;

import com.hh.pms.service.IOrderTypeRunningService;
import com.ruoyi.system.api.domain.OrderTypeRunning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/running")
public class OrderTypeRunningController {
    @Autowired
    private IOrderTypeRunningService iOrderTypeRunningService;
    @GetMapping("/listRunning")
    public List<OrderTypeRunning> list() {
        return iOrderTypeRunningService.listName();
    }
}
