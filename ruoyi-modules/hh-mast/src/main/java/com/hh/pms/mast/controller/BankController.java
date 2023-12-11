package com.hh.pms.mast.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.Bank;
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
import com.hh.pms.mast.service.IBankService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 银行Controller
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@RestController
@RequestMapping("/bank")
public class BankController extends BaseController
{
    @Autowired
    private IBankService bankService;

    /**
     * 查询银行列表
     */
    @RequiresPermissions("mast:bank:list")
    @GetMapping("/list")
    public TableDataInfo list(Bank bank)
    {
        startPage();
        List<Bank> list = bankService.selectBankList(bank);
        return getDataTable(list);
    }
    @RequiresPermissions("mast:bank:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(Bank bank)
    {
        List<Bank> list = bankService.selectBankList(bank);
        return getDataTable(list);
    }

    /**
     * 导出银行列表
     */
    @RequiresPermissions("mast:bank:export")
    @Log(title = "银行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Bank bank)
    {
        List<Bank> list = bankService.selectBankList(bank);
        ExcelUtil<Bank> util = new ExcelUtil<Bank>(Bank.class);
        util.exportExcel(response, list, "银行数据");
    }

    /**
     * 获取银行详细信息
     */
    @RequiresPermissions("mast:bank:query")
    @GetMapping(value = "/{bankId}")
    public AjaxResult getInfo(@PathVariable("bankId") Long bankId)
    {
        return success(bankService.selectBankByBankId(bankId));
    }

    /**
     * 新增银行
     */
    @RequiresPermissions("mast:bank:add")
    @Log(title = "银行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Bank bank)
    {
        return toAjax(bankService.insertBank(bank));
    }

    /**
     * 修改银行
     */
    @RequiresPermissions("mast:bank:edit")
    @Log(title = "银行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Bank bank)
    {
        return toAjax(bankService.updateBank(bank));
    }

    /**
     * 删除银行
     */
    @RequiresPermissions("mast:bank:remove")
    @Log(title = "银行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bankIds}")
    public AjaxResult remove(@PathVariable Long[] bankIds)
    {
        return toAjax(bankService.deleteBankByBankIds(bankIds));
    }
}
