package com.hh.pms.mast.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.CalculationUnitType;
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
import com.hh.pms.mast.service.ICalculationUnitTypeService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 计量单位类型定义Controller
 *
 * @author ruoyi
 * @date 2023-11-28
 */
@RestController
@RequestMapping("/unit_type")
public class CalculationUnitTypeController extends BaseController
{
    @Autowired
    private ICalculationUnitTypeService calculationUnitTypeService;

    /**
     * 查询计量单位类型定义列表
     */
    @RequiresPermissions("mast:unit_type:list")
    @GetMapping("/list")
    public TableDataInfo list(CalculationUnitType calculationUnitType)
    {
        startPage();
        List<CalculationUnitType> list = calculationUnitTypeService.selectCalculationUnitTypeList(calculationUnitType);
        return getDataTable(list);
    }
    @RequiresPermissions("mast:unit_type:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(CalculationUnitType calculationUnitType)
    {
        List<CalculationUnitType> list = calculationUnitTypeService.selectCalculationUnitTypeList(calculationUnitType);
        return getDataTable(list);
    }

    /**
     * 导出计量单位类型定义列表
     */
    @RequiresPermissions("mast:unit_type:export")
    @Log(title = "计量单位类型定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CalculationUnitType calculationUnitType)
    {
        List<CalculationUnitType> list = calculationUnitTypeService.selectCalculationUnitTypeList(calculationUnitType);
        ExcelUtil<CalculationUnitType> util = new ExcelUtil<CalculationUnitType>(CalculationUnitType.class);
        util.exportExcel(response, list, "计量单位类型定义数据");
    }

    /**
     * 获取计量单位类型定义详细信息
     */
    @RequiresPermissions("mast:unit_type:query")
    @GetMapping(value = "/{unitTypeId}")
    public AjaxResult getInfo(@PathVariable("unitTypeId") Long unitTypeId)
    {
        return success(calculationUnitTypeService.selectCalculationUnitTypeByUnitTypeId(unitTypeId));
    }

    /**
     * 新增计量单位类型定义
     */
    @RequiresPermissions("mast:unit_type:add")
    @Log(title = "计量单位类型定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CalculationUnitType calculationUnitType)
    {
        return toAjax(calculationUnitTypeService.insertCalculationUnitType(calculationUnitType));
    }

    /**
     * 修改计量单位类型定义
     */
    @RequiresPermissions("mast:unit_type:edit")
    @Log(title = "计量单位类型定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CalculationUnitType calculationUnitType)
    {
        return toAjax(calculationUnitTypeService.updateCalculationUnitType(calculationUnitType));
    }

    /**
     * 删除计量单位类型定义
     */
    @RequiresPermissions("mast:unit_type:remove")
    @Log(title = "计量单位类型定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{unitTypeIds}")
    public AjaxResult remove(@PathVariable Long[] unitTypeIds)
    {
        return toAjax(calculationUnitTypeService.deleteCalculationUnitTypeByUnitTypeIds(unitTypeIds));
    }
}
