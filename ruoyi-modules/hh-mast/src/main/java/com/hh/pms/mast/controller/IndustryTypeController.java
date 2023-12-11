package com.hh.pms.mast.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.IndustryType;
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
import com.hh.pms.mast.service.IIndustryTypeService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 行业类型Controller
 *
 * @author ruoyi
 * @date 2023-11-29
 */
@RestController
@RequestMapping("/industry_type")
public class IndustryTypeController extends BaseController
{
    @Autowired
    private IIndustryTypeService industryTypeService;

    /**
     * 查询行业类型列表
     */
    @RequiresPermissions("mast:industry_type:list")
    @GetMapping("/list")
    public TableDataInfo list(IndustryType industryType)
    {
        startPage();
        List<IndustryType> list = industryTypeService.selectIndustryTypeList(industryType);
        return getDataTable(list);
    }
    @RequiresPermissions("mast:industry_type:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(IndustryType industryType)
    {
        List<IndustryType> list = industryTypeService.selectIndustryTypeList(industryType);
        return getDataTable(list);
    }

    /**
     * 导出行业类型列表
     */
    @RequiresPermissions("mast:industry_type:export")
    @Log(title = "行业类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IndustryType industryType)
    {
        List<IndustryType> list = industryTypeService.selectIndustryTypeList(industryType);
        ExcelUtil<IndustryType> util = new ExcelUtil<IndustryType>(IndustryType.class);
        util.exportExcel(response, list, "行业类型数据");
    }

    /**
     * 获取行业类型详细信息
     */
    @RequiresPermissions("mast:industry_type:query")
    @GetMapping(value = "/{industryTypeId}")
    public AjaxResult getInfo(@PathVariable("industryTypeId") Long industryTypeId)
    {
        return success(industryTypeService.selectIndustryTypeByIndustryTypeId(industryTypeId));
    }

    /**
     * 新增行业类型
     */
    @RequiresPermissions("mast:industry_type:add")
    @Log(title = "行业类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IndustryType industryType)
    {
        return toAjax(industryTypeService.insertIndustryType(industryType));
    }

    /**
     * 修改行业类型
     */
    @RequiresPermissions("mast:industry_type:edit")
    @Log(title = "行业类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IndustryType industryType)
    {
        return toAjax(industryTypeService.updateIndustryType(industryType));
    }

    /**
     * 删除行业类型
     */
    @RequiresPermissions("mast:industry_type:remove")
    @Log(title = "行业类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{industryTypeIds}")
    public AjaxResult remove(@PathVariable Long[] industryTypeIds)
    {
        return toAjax(industryTypeService.deleteIndustryTypeByIndustryTypeIds(industryTypeIds));
    }
}
