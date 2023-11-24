package com.hh.pms.model;

import com.hh.pms.domain.SupplierDetails;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "jqy-hh-supplier")

public interface SupplierClient {

    @RequestMapping(method = RequestMethod.POST,value = "/supplier/list")
    public TableDataInfo list(SupplierDetails supplierDetails);

}
