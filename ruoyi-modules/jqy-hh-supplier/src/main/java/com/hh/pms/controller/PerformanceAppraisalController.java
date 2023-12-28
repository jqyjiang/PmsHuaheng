package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.PerformanceAppraisal;
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
import com.hh.pms.service.IPerformanceAppraisalService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 绩效考评Controller
 * 
 * @author ruoyi
 * @date 2023-12-22
 */
@RestController
@RequestMapping("/performance")
public class PerformanceAppraisalController extends BaseController
{
    @Autowired
    private IPerformanceAppraisalService performanceAppraisalService;

    /**
     * 查询绩效考评列表
     */
    @RequiresPermissions("supplierpms:performance:list")
    @GetMapping("/list")
    public TableDataInfo list(PerformanceAppraisal performanceAppraisal)
    {
        startPage();
        List<PerformanceAppraisal> list = performanceAppraisalService.selectPerformanceAppraisalList(performanceAppraisal);
        return getDataTable(list);
    }

    /**
     * 导出绩效考评列表
     */
    @RequiresPermissions("supplierpms:performance:export")
    @Log(title = "绩效考评", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerformanceAppraisal performanceAppraisal)
    {
        List<PerformanceAppraisal> list = performanceAppraisalService.selectPerformanceAppraisalList(performanceAppraisal);
        ExcelUtil<PerformanceAppraisal> util = new ExcelUtil<PerformanceAppraisal>(PerformanceAppraisal.class);
        util.exportExcel(response, list, "绩效考评数据");
    }

    /**
     * 获取绩效考评详细信息
     */
    @RequiresPermissions("supplierpms:performance:query")
    @GetMapping(value = "/{evaluationId}")
    public AjaxResult getInfo(@PathVariable("evaluationId") Long evaluationId)
    {
        return success(performanceAppraisalService.selectPerformanceAppraisalByEvaluationId(evaluationId));
    }

    /**
     * 新增绩效考评
     */
    @RequiresPermissions("supplierpms:performance:add")
    @Log(title = "绩效考评", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerformanceAppraisal performanceAppraisal) throws CloneNotSupportedException {
        System.out.println(performanceAppraisal);
        return toAjax(performanceAppraisalService.insertPerformanceAppraisal(performanceAppraisal));
    }

    /**
     * 修改绩效考评
     */
    @RequiresPermissions("supplierpms:performance:edit")
    @Log(title = "绩效考评", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerformanceAppraisal performanceAppraisal) throws CloneNotSupportedException {
        return toAjax(performanceAppraisalService.updatePerformanceAppraisal(performanceAppraisal));
    }

    /**
     * 删除绩效考评
     */
    @RequiresPermissions("supplierpms:performance:remove")
    @Log(title = "绩效考评", businessType = BusinessType.DELETE)
	@DeleteMapping("/{evaluationIds}")
    public AjaxResult remove(@PathVariable Long[] evaluationIds)
    {
        return toAjax(performanceAppraisalService.deletePerformanceAppraisalByEvaluationIds(evaluationIds));
    }
}
