package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ScoringTemplate;
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
import com.hh.pms.service.IScoringTemplateService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 考评模板Controller
 * 
 * @author ruoyi
 * @date 2023-12-26
 */
@RestController
@RequestMapping("/template")
public class ScoringTemplateController extends BaseController
{
    @Autowired
    private IScoringTemplateService scoringTemplateService;

    /**
     * 查询考评模板列表
     */
    @RequiresPermissions("supplierpms:template:list")
    @GetMapping("/list")
    public TableDataInfo list(ScoringTemplate scoringTemplate)
    {
        startPage();
        List<ScoringTemplate> list = scoringTemplateService.selectScoringTemplateList(scoringTemplate);
        return getDataTable(list);
    }

    /**
     * 导出考评模板列表
     */
    @RequiresPermissions("supplierpms:template:export")
    @Log(title = "考评模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScoringTemplate scoringTemplate)
    {
        List<ScoringTemplate> list = scoringTemplateService.selectScoringTemplateList(scoringTemplate);
        ExcelUtil<ScoringTemplate> util = new ExcelUtil<ScoringTemplate>(ScoringTemplate.class);
        util.exportExcel(response, list, "考评模板数据");
    }

    /**
     * 获取考评模板详细信息
     */
    @RequiresPermissions("supplierpms:template:query")
    @GetMapping(value = "/{scoringTemplateId}")
    public AjaxResult getInfo(@PathVariable("scoringTemplateId") Long scoringTemplateId)
    {
        return success(scoringTemplateService.selectScoringTemplateByScoringTemplateId(scoringTemplateId));
    }

    /**
     * 新增考评模板
     */
    @RequiresPermissions("supplierpms:template:add")
    @Log(title = "考评模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScoringTemplate scoringTemplate)
    {
        return toAjax(scoringTemplateService.insertScoringTemplate(scoringTemplate));
    }

    /**
     * 修改考评模板
     */
    @RequiresPermissions("supplierpms:template:edit")
    @Log(title = "考评模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScoringTemplate scoringTemplate)
    {
        return toAjax(scoringTemplateService.updateScoringTemplate(scoringTemplate));
    }

    /**
     * 删除考评模板
     */
    @RequiresPermissions("supplierpms:template:remove")
    @Log(title = "考评模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{scoringTemplateIds}")
    public AjaxResult remove(@PathVariable Long[] scoringTemplateIds)
    {
        return toAjax(scoringTemplateService.deleteScoringTemplateByScoringTemplateIds(scoringTemplateIds));
    }
}
