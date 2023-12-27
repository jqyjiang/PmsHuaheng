package com.hh.pms.model;


import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.OrderManager;
import com.ruoyi.system.api.domain.OrderMaterial;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hh-pms")
public interface OrderMaterialClient {

    @RequestMapping(method = RequestMethod.POST,value = "/materials")
    AjaxResult insertOrderMaterial(@RequestBody OrderMaterial orderMaterial);



    @RequestMapping(method = RequestMethod.POST,value = "/manager")
    AjaxResult insertOrderManager(@RequestBody OrderManager orderManager);

}
