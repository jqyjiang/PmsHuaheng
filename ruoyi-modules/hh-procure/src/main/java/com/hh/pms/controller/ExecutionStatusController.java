package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ExecutionStatus;
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
import com.hh.pms.service.IExecutionStatusService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 签署执行状态表Controller
 *
 * @author ruoyi
 * @date 2023-12-18
 */
@RestController
@RequestMapping("/status")
public class ExecutionStatusController extends BaseController
{
    @Autowired
    private IExecutionStatusService executionStatusService;

    /**
     * 查询签署执行状态表列表
     */
    @RequiresPermissions("procure:status:list")
    @GetMapping("/list")
    public TableDataInfo list(ExecutionStatus executionStatus)
    {
        startPage();
        List<ExecutionStatus> list = executionStatusService.selectExecutionStatusList(executionStatus);
        return getDataTable(list);
    }

    /**
     * 导出签署执行状态表列表
     */
    @RequiresPermissions("procure:status:export")
    @Log(title = "签署执行状态表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExecutionStatus executionStatus)
    {
        List<ExecutionStatus> list = executionStatusService.selectExecutionStatusList(executionStatus);
        ExcelUtil<ExecutionStatus> util = new ExcelUtil<ExecutionStatus>(ExecutionStatus.class);
        util.exportExcel(response, list, "签署执行状态表数据");
    }

    /**
     * 获取签署执行状态表详细信息
     */
    @RequiresPermissions("procure:status:query")
    @GetMapping(value = "/{executionId}")
    public AjaxResult getInfo(@PathVariable("executionId") Long executionId)
    {
        return success(executionStatusService.selectExecutionStatusByExecutionId(executionId));
    }

    /**
     * 新增签署执行状态表
     */
    @RequiresPermissions("procure:status:add")
    @Log(title = "签署执行状态表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExecutionStatus executionStatus)
    {
        return toAjax(executionStatusService.insertExecutionStatus(executionStatus));
    }

    /**
     * 修改签署执行状态表
     */
    @RequiresPermissions("procure:status:edit")
    @Log(title = "签署执行状态表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExecutionStatus executionStatus)
    {
        return toAjax(executionStatusService.updateExecutionStatus(executionStatus));
    }

    /**
     * 删除签署执行状态表
     */
    @RequiresPermissions("procure:status:remove")
    @Log(title = "签署执行状态表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{executionIds}")
    public AjaxResult remove(@PathVariable Long[] executionIds)
    {
        return toAjax(executionStatusService.deleteExecutionStatusByExecutionIds(executionIds));
    }
}
