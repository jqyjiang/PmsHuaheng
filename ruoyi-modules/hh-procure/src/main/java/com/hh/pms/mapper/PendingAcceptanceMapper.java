package com.hh.pms.mapper;

import com.ruoyi.system.api.domain.ProcurementTask;

import java.util.List;

public interface PendingAcceptanceMapper {
    List<ProcurementTask> selectTaskAccepted();
}
