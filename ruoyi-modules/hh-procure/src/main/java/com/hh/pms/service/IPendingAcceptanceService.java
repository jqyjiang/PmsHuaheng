package com.hh.pms.service;

import com.ruoyi.system.api.domain.ProcurementTask;

import java.util.List;

public interface IPendingAcceptanceService {
    List<ProcurementTask> selectTaskAccepted();
}
