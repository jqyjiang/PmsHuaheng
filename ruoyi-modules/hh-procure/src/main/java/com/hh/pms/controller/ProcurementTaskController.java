package com.hh.pms.controller;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ProcurementTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.service.IProcurementTaskService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 我的需求任务Controller
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
@RestController
@RequestMapping("/task")
public class ProcurementTaskController extends BaseController
{
    @Autowired
    private IProcurementTaskService procurementTaskService;

    /**
     * 查询我的需求任务列表
     */
    @RequiresPermissions("procure:task:list")
    @GetMapping("/list")
    public TableDataInfo list(ProcurementTask procurementTask)
    {
        startPage();
        List<ProcurementTask> list = procurementTaskService.selectProcurementTaskList(procurementTask);
        return getDataTable(list);
    }

    /**
     * 导出我的需求任务列表
     */
    @RequiresPermissions("procure:task:export")
    @Log(title = "我的需求任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcurementTask procurementTask)
    {
        List<ProcurementTask> list = procurementTaskService.selectProcurementTaskList(procurementTask);
        ExcelUtil<ProcurementTask> util = new ExcelUtil<ProcurementTask>(ProcurementTask.class);
        util.exportExcel(response, list, "我的需求任务数据");
    }

    /**
     * 获取我的需求任务详细信息
     */
    @RequiresPermissions("procure:task:query")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return success(procurementTaskService.selectProcurementTaskByTaskId(taskId));
    }

    /**
     * 获取我的需求任务详细信息
     */
    @GetMapping(value = "/selectProcurementTaskByTaskId/{taskCode}")
    public ProcurementTask selectProcurementTaskByTaskId(@PathVariable("taskCode") String taskCode)
    {
        return procurementTaskService.selectProcurementTaskByTaskCode(taskCode);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/updateProcurement")
    public int updateProcurement(@RequestBody ProcurementTask procurementTask)
    {
        return procurementTaskService.updateProcurement(procurementTask);
    }

    /**
     * 新增我的需求任务
     */
    @RequiresPermissions("procure:task:add")
    @Log(title = "我的需求任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcurementTask procurementTask)
    {
        return toAjax(procurementTaskService.insertProcurementTask(procurementTask));
    }

    /**
     * 修改我的需求任务
     */
    @RequiresPermissions("procure:task:edit")
    @Log(title = "我的需求任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcurementTask procurementTask)
    {
        return toAjax(procurementTaskService.updateProcurementTask(procurementTask));
    }

    /**
     * 删除我的需求任务
     */
    @RequiresPermissions("procure:task:remove")
    @Log(title = "我的需求任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(procurementTaskService.deleteProcurementTaskByTaskIds(taskIds));
    }


    /**
     * 转办
     */
    @RequiresPermissions("procure:task:edit")
    @PutMapping("/zb/{purchaser}/{taskId}")
    public AjaxResult editProcurementTaskPurchaser(@PathVariable("purchaser") String purchaser,@PathVariable("taskId") Integer[] taskId)
    {
        System.out.println("fff:"+purchaser);
        return toAjax(procurementTaskService.updateProcurementTaskPurchaser(purchaser,taskId));
    }

}
