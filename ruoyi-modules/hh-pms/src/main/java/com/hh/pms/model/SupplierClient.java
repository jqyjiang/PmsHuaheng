package com.hh.pms.model;

import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.domain.SupplierDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "jqy-hh-supplier")
@Component("supplierClientBean")
public interface SupplierClient {
    @RequestMapping(method = RequestMethod.GET, value = "/details/list")
    public TableDataInfo list(@RequestParam("supplierDetails") SupplierDetails supplierDetails, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

}
