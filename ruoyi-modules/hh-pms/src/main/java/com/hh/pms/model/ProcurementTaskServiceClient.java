package com.hh.pms.model;


import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.domain.ProcurementTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "hh-procure")
public interface ProcurementTaskServiceClient{

    @RequestMapping(method = RequestMethod.GET,value = "/task/selectProcurementTaskByTaskId/{taskCode}")
    ProcurementTask selectProcurementTaskByTaskId(@PathVariable("taskCode") String taskCode);

    @RequestMapping(method = RequestMethod.POST,value = "/task/updateProcurement")
    int updateProcurement(@RequestBody ProcurementTask procurementTask);

    @RequestMapping(method = RequestMethod.GET,value = "/management/list")
    public TableDataInfo listOrderList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
