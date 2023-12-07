package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.domain.ProcurementTask;
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
}
