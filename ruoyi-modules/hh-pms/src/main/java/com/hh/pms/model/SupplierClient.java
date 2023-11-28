package com.hh.pms.model;

import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "jqy-hh-supplier")
public interface SupplierClient {
    @RequestMapping(method = RequestMethod.GET,value = "/details/list")
    public TableDataInfo list();

}
