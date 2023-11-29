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
import com.hh.pms.mast.domain.Currency;
import com.hh.pms.mast.service.ICurrencyService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 币种Controller
 *
 * @author ruoyi
 * @date 2023-11-27
 */
@RestController
@RequestMapping("/currency")
public class CurrencyController extends BaseController
{
    @Autowired
    private ICurrencyService currencyService;

    /**
     * 查询币种列表
     */
    @RequiresPermissions("mast:currency:list")
    @GetMapping("/list")
    public TableDataInfo list(Currency currency)
    {
        startPage();
        List<Currency> list = currencyService.selectCurrencyList(currency);
        return getDataTable(list);
    }
    @RequiresPermissions("mast:currency:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(Currency currency)
    {
        List<Currency> list = currencyService.selectCurrencyList(currency);
        return getDataTable(list);
    }

    /**
     * 导出币种列表
     */
    @RequiresPermissions("mast:currency:export")
    @Log(title = "币种", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Currency currency)
    {
        List<Currency> list = currencyService.selectCurrencyList(currency);
        ExcelUtil<Currency> util = new ExcelUtil<Currency>(Currency.class);
        util.exportExcel(response, list, "币种数据");
    }

    /**
     * 获取币种详细信息
     */
    @RequiresPermissions("mast:currency:query")
    @GetMapping(value = "/{currencyId}")
    public AjaxResult getInfo(@PathVariable("currencyId") Long currencyId)
    {
        return success(currencyService.selectCurrencyByCurrencyId(currencyId));
    }

    /**
     * 新增币种
     */
    @RequiresPermissions("mast:currency:add")
    @Log(title = "币种", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Currency currency)
    {
        return toAjax(currencyService.insertCurrency(currency));
    }

    /**
     * 修改币种
     */
    @RequiresPermissions("mast:currency:edit")
    @Log(title = "币种", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Currency currency)
    {
        return toAjax(currencyService.updateCurrency(currency));
    }

    /**
     * 删除币种
     */
    @RequiresPermissions("mast:currency:remove")
    @Log(title = "币种", businessType = BusinessType.DELETE)
	@DeleteMapping("/{currencyIds}")
    public AjaxResult remove(@PathVariable Long[] currencyIds)
    {
        return toAjax(currencyService.deleteCurrencyByCurrencyIds(currencyIds));
    }
}
