package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.Questionnaire;
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
import com.hh.pms.service.IQuestionnaireService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 调查表Controller
 * 
 * @author ruoyi
 * @date 2023-12-18
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController extends BaseController
{
    @Autowired
    private IQuestionnaireService questionnaireService;

    /**
     * 查询调查表列表
     */
    @RequiresPermissions("supplierpms:questionnaire:list")
    @GetMapping("/list")
    public TableDataInfo list(Questionnaire questionnaire)
    {
        startPage();
        List<Questionnaire> list = questionnaireService.selectQuestionnaireList(questionnaire);
        return getDataTable(list);
    }

    /**
     * 导出调查表列表
     */
    @RequiresPermissions("supplierpms:questionnaire:export")
    @Log(title = "调查表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Questionnaire questionnaire)
    {
        List<Questionnaire> list = questionnaireService.selectQuestionnaireList(questionnaire);
        ExcelUtil<Questionnaire> util = new ExcelUtil<Questionnaire>(Questionnaire.class);
        util.exportExcel(response, list, "调查表数据");
    }

    /**
     * 获取调查表详细信息
     */
    @RequiresPermissions("supplierpms:questionnaire:query")
    @GetMapping(value = "/{questionnaireId}")
    public AjaxResult getInfo(@PathVariable("questionnaireId") Long questionnaireId)
    {
        return success(questionnaireService.selectQuestionnaireByQuestionnaireId(questionnaireId));
    }

    /**
     * 新增调查表
     */
    @RequiresPermissions("supplierpms:questionnaire:add")
    @Log(title = "调查表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Questionnaire questionnaire)
    {
        return toAjax(questionnaireService.insertQuestionnaire(questionnaire));
    }

    /**
     * 修改调查表
     */
    @RequiresPermissions("supplierpms:questionnaire:edit")
    @Log(title = "调查表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Questionnaire questionnaire)
    {
        return toAjax(questionnaireService.updateQuestionnaire(questionnaire));
    }

    /**
     * 删除调查表
     */
    @RequiresPermissions("supplierpms:questionnaire:remove")
    @Log(title = "调查表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{questionnaireIds}")
    public AjaxResult remove(@PathVariable Long[] questionnaireIds)
    {
        return toAjax(questionnaireService.deleteQuestionnaireByQuestionnaireIds(questionnaireIds));
    }
}
