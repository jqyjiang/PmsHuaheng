package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.SampleSending;
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
import com.hh.pms.service.ISampleSendingService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 送样Controller
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
@RestController
@RequestMapping("/sending")
public class SampleSendingController extends BaseController
{
    @Autowired
    private ISampleSendingService sampleSendingService;

    /**
     * 查询送样列表
     */
    @RequiresPermissions("supplierpms:sending:list")
    @GetMapping("/list")
    public TableDataInfo list(SampleSending sampleSending)
    {
        startPage();
        List<SampleSending> list = sampleSendingService.selectSampleSendingList(sampleSending);
        return getDataTable(list);
    }

    /**
     * 导出送样列表
     */
    @RequiresPermissions("supplierpms:sending:export")
    @Log(title = "送样", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleSending sampleSending)
    {
        List<SampleSending> list = sampleSendingService.selectSampleSendingList(sampleSending);
        ExcelUtil<SampleSending> util = new ExcelUtil<SampleSending>(SampleSending.class);
        util.exportExcel(response, list, "送样数据");
    }

    /**
     * 获取送样详细信息
     */
    @RequiresPermissions("supplierpms:sending:query")
    @GetMapping(value = "/{sampleId}")
    public AjaxResult getInfo(@PathVariable("sampleId") Long sampleId)
    {
        return success(sampleSendingService.selectSampleSendingBySampleId(sampleId));
    }

    /**
     * 新增送样
     */
    @RequiresPermissions("supplierpms:sending:add")
    @Log(title = "送样", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleSending sampleSending)
    {
        return toAjax(sampleSendingService.insertSampleSending(sampleSending));
    }

    /**
     * 修改送样
     */
    @RequiresPermissions("supplierpms:sending:edit")
    @Log(title = "送样", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleSending sampleSending)
    {
        return toAjax(sampleSendingService.updateSampleSending(sampleSending));
    }
    /**
     * 修改送样状态
     */
    @RequiresPermissions("supplierpms:sending:edit")
    @Log(title = "送样", businessType = BusinessType.UPDATE)
    @PutMapping("/updataState")
    public AjaxResult updateSampleSendingState(@RequestBody SampleSending sampleSending)
    {
        return toAjax(sampleSendingService.updateSampleSendingState(sampleSending));
    }
    /**
     * 删除送样
     */
    @RequiresPermissions("supplierpms:sending:remove")
    @Log(title = "送样", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sampleIds}")
    public AjaxResult remove(@PathVariable Long[] sampleIds)
    {
        return toAjax(sampleSendingService.deleteSampleSendingBySampleIds(sampleIds));
    }
}
