package com.hh.pms.model;

import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hh-mast")
public interface MaterialClient {
    @RequestMapping(method = RequestMethod.GET,value = "/material/list")
    public TableDataInfo list();
}
