package com.hh.pms.mast.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.BankType;
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
import com.hh.pms.mast.service.IBankTypeService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 银行类型Controller
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@RestController
@RequestMapping("/bank_type")
public class BankTypeController extends BaseController
{
    @Autowired
    private IBankTypeService bankTypeService;

    /**
     * 查询银行类型列表
     */
    @RequiresPermissions("mast:bank_type:list")
    @GetMapping("/list")
    public TableDataInfo list(BankType bankType)
    {
        startPage();
        List<BankType> list = bankTypeService.selectBankTypeList(bankType);
        return getDataTable(list);
    }
    @RequiresPermissions("mast:bank_type:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(BankType bankType)
    {
        List<BankType> list = bankTypeService.selectBankTypeList(bankType);
        return getDataTable(list);
    }

    /**
     * 导出银行类型列表
     */
    @RequiresPermissions("mast:bank_type:export")
    @Log(title = "银行类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankType bankType)
    {
        List<BankType> list = bankTypeService.selectBankTypeList(bankType);
        ExcelUtil<BankType> util = new ExcelUtil<BankType>(BankType.class);
        util.exportExcel(response, list, "银行类型数据");
    }

    /**
     * 获取银行类型详细信息
     */
    @RequiresPermissions("mast:bank_type:query")
    @GetMapping(value = "/{bankTypeId}")
    public AjaxResult getInfo(@PathVariable("bankTypeId") Long bankTypeId)
    {
        return success(bankTypeService.selectBankTypeByBankTypeId(bankTypeId));
    }

    /**
     * 新增银行类型
     */
    @RequiresPermissions("mast:bank_type:add")
    @Log(title = "银行类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BankType bankType)
    {
        return toAjax(bankTypeService.insertBankType(bankType));
    }

    /**
     * 修改银行类型
     */
    @RequiresPermissions("mast:bank_type:edit")
    @Log(title = "银行类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BankType bankType)
    {
        return toAjax(bankTypeService.updateBankType(bankType));
    }

    /**
     * 删除银行类型
     */
    @RequiresPermissions("mast:bank_type:remove")
    @Log(title = "银行类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bankTypeIds}")
    public AjaxResult remove(@PathVariable Long[] bankTypeIds)
    {
        return toAjax(bankTypeService.deleteBankTypeByBankTypeIds(bankTypeIds));
    }
}
