package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.Indicators;
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
import com.hh.pms.service.IIndicatorsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 考评指标Controller
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
@RestController
@RequestMapping("/indicators")
public class IndicatorsController extends BaseController
{
    @Autowired
    private IIndicatorsService indicatorsService;

    /**
     * 查询考评指标列表
     */
    @RequiresPermissions("supplierpms:indicators:list")
    @GetMapping("/list")
    public TableDataInfo list(Indicators indicators)
    {
        startPage();
        List<Indicators> list = indicatorsService.selectIndicatorsList(indicators);
        return getDataTable(list);
    }

    /**
     * 导出考评指标列表
     */
    @RequiresPermissions("supplierpms:indicators:export")
    @Log(title = "考评指标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Indicators indicators)
    {
        List<Indicators> list = indicatorsService.selectIndicatorsList(indicators);
        ExcelUtil<Indicators> util = new ExcelUtil<Indicators>(Indicators.class);
        util.exportExcel(response, list, "考评指标数据");
    }

    /**
     * 获取考评指标详细信息
     */
    @RequiresPermissions("supplierpms:indicators:query")
    @GetMapping(value = "/{indicatorId}")
    public AjaxResult getInfo(@PathVariable("indicatorId") Long indicatorId)
    {
        return success(indicatorsService.selectIndicatorsByIndicatorId(indicatorId));
    }

    /**
     * 新增考评指标
     */
    @RequiresPermissions("supplierpms:indicators:add")
    @Log(title = "考评指标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Indicators indicators)
    {
        return toAjax(indicatorsService.insertIndicators(indicators));
    }

    /**
     * 修改考评指标
     */
    @RequiresPermissions("supplierpms:indicators:edit")
    @Log(title = "考评指标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Indicators indicators)
    {
        return toAjax(indicatorsService.updateIndicators(indicators));
    }

    /**
     * 删除考评指标
     */
    @RequiresPermissions("supplierpms:indicators:remove")
    @Log(title = "考评指标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{indicatorIds}")
    public AjaxResult remove(@PathVariable Long[] indicatorIds)
    {
        return toAjax(indicatorsService.deleteIndicatorsByIndicatorIds(indicatorIds));
    }
}
