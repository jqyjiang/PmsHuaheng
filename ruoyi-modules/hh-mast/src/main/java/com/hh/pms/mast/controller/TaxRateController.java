package com.hh.pms.mast.controller;

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
import com.hh.pms.mast.domain.TaxRate;
import com.hh.pms.mast.service.ITaxRateService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 税率Controller
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@RestController
@RequestMapping("/rate")
public class TaxRateController extends BaseController
{
    @Autowired
    private ITaxRateService taxRateService;

    /**
     * 查询税率列表
     */
    @RequiresPermissions("mast:rate:list")
    @GetMapping("/list")
    public TableDataInfo list(TaxRate taxRate)
    {
        startPage();
        List<TaxRate> list = taxRateService.selectTaxRateList(taxRate);
        return getDataTable(list);
    }

    /**
     * 导出税率列表
     */
    @RequiresPermissions("mast:rate:export")
    @Log(title = "税率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaxRate taxRate)
    {
        List<TaxRate> list = taxRateService.selectTaxRateList(taxRate);
        ExcelUtil<TaxRate> util = new ExcelUtil<TaxRate>(TaxRate.class);
        util.exportExcel(response, list, "税率数据");
    }

    /**
     * 获取税率详细信息
     */
    @RequiresPermissions("mast:rate:query")
    @GetMapping(value = "/{taxTypeId}")
    public AjaxResult getInfo(@PathVariable("taxTypeId") Long taxTypeId)
    {
        return success(taxRateService.selectTaxRateByTaxTypeId(taxTypeId));
    }

    /**
     * 新增税率
     */
    @RequiresPermissions("mast:rate:add")
    @Log(title = "税率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaxRate taxRate)
    {
        return toAjax(taxRateService.insertTaxRate(taxRate));
    }

    /**
     * 修改税率
     */
    @RequiresPermissions("mast:rate:edit")
    @Log(title = "税率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaxRate taxRate)
    {
        return toAjax(taxRateService.updateTaxRate(taxRate));
    }

    /**
     * 删除税率
     */
    @RequiresPermissions("mast:rate:remove")
    @Log(title = "税率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taxTypeIds}")
    public AjaxResult remove(@PathVariable Long[] taxTypeIds)
    {
        return toAjax(taxRateService.deleteTaxRateByTaxTypeIds(taxTypeIds));
    }
}
