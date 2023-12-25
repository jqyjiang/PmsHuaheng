package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.QualityTaskList;
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

import com.hh.pms.service.IQualityTaskListService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 到货质检Controller
 * 
 * @author yangtao
 * @date 2023-12-21
 */
@RestController
@RequestMapping("/orderQuality")
public class QualityTaskListController extends BaseController
{
    @Autowired
    private IQualityTaskListService qualityTaskListService;

    /**
     * 查询到货质检列表
     */
    @RequiresPermissions("pms:orderQuality:list")
    @GetMapping("/list")
    public TableDataInfo list(QualityTaskList qualityTaskList)
    {
        startPage();
        List<QualityTaskList> list = qualityTaskListService.selectQualityTaskListList(qualityTaskList);
        return getDataTable(list);
    }

    /**
     * 导出到货质检列表
     */
    @RequiresPermissions("pms:orderQuality:export")
    @Log(title = "到货质检", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QualityTaskList qualityTaskList)
    {
        List<QualityTaskList> list = qualityTaskListService.selectQualityTaskListList(qualityTaskList);
        ExcelUtil<QualityTaskList> util = new ExcelUtil<QualityTaskList>(QualityTaskList.class);
        util.exportExcel(response, list, "到货质检数据");
    }

    /**
     * 获取到货质检详细信息
     */
    @RequiresPermissions("pms:orderQuality:query")
    @GetMapping(value = "/{qualityTaskListId}")
    public AjaxResult getInfo(@PathVariable("qualityTaskListId") Long qualityTaskListId)
    {
        return success(qualityTaskListService.selectQualityTaskListByQualityTaskListId(qualityTaskListId));
    }

    /**
     * 新增到货质检
     */
    @RequiresPermissions("pms:orderQuality:add")
    @Log(title = "到货质检", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QualityTaskList qualityTaskList)
    {
        return toAjax(qualityTaskListService.insertQualityTaskList(qualityTaskList));
    }

    /**
     * 修改到货质检
     */
    @RequiresPermissions("pms:orderQuality:edit")
    @Log(title = "到货质检", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QualityTaskList qualityTaskList)
    {
        return toAjax(qualityTaskListService.updateQualityTaskList(qualityTaskList));
    }

    /**
     * 删除到货质检
     */
    @RequiresPermissions("pms:orderQuality:remove")
    @Log(title = "到货质检", businessType = BusinessType.DELETE)
	@DeleteMapping("/{qualityTaskListIds}")
    public AjaxResult remove(@PathVariable Long[] qualityTaskListIds)
    {
        return toAjax(qualityTaskListService.deleteQualityTaskListByQualityTaskListIds(qualityTaskListIds));
    }
}
