package com.hh.pms.service.impl;

import com.hh.pms.mapper.PendingAcceptanceMapper;
import com.hh.pms.service.IPendingAcceptanceService;
import com.ruoyi.system.api.domain.ProcurementTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingAcceptanceServiceImpl implements IPendingAcceptanceService {

    @Autowired
    private PendingAcceptanceMapper pendingAcceptanceMapper;

    @Override
    public List<ProcurementTask> selectTaskAccepted() {
        return pendingAcceptanceMapper.selectTaskAccepted();
    }
}
