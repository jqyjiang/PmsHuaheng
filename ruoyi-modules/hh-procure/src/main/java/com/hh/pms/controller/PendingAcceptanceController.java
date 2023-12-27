package com.hh.pms.controller;


import com.hh.pms.service.IPendingAcceptanceService;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.MaterialRequirement;
import com.ruoyi.system.api.domain.ProcurementTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pendingAcceptance")
public class PendingAcceptanceController {
    @Autowired
    private IPendingAcceptanceService pendingAcceptanceService;

    /**
     * 查询待受理数量
     */
    @RequiresPermissions("procure:pendingAcceptance:list")
    @GetMapping("/list")
    public TableDataInfo list()
    {
        return (TableDataInfo) pendingAcceptanceService.selectTaskAccepted();
    }
}
