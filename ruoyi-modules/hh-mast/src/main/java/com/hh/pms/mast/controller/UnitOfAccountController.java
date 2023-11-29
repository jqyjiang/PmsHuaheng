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
import com.hh.pms.mast.domain.UnitOfAccount;
import com.hh.pms.mast.service.IUnitOfAccountService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 计算单位定义Controller
 *
 * @author ruoyi
 * @date 2023-11-28
 */
@RestController
@RequestMapping("/account")
public class UnitOfAccountController extends BaseController
{
    @Autowired
    private IUnitOfAccountService unitOfAccountService;

    /**
     * 查询计算单位定义列表
     */
    @RequiresPermissions("mast:account:list")
    @GetMapping("/list")
    public TableDataInfo list(UnitOfAccount unitOfAccount)
    {
        startPage();
        List<UnitOfAccount> list = unitOfAccountService.selectUnitOfAccountList(unitOfAccount);
        return getDataTable(list);
    }
    @RequiresPermissions("mast:account:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(UnitOfAccount unitOfAccount)
    {
        List<UnitOfAccount> list = unitOfAccountService.selectUnitOfAccountList(unitOfAccount);
        return getDataTable(list);
    }

    /**
     * 导出计算单位定义列表
     */
    @RequiresPermissions("mast:account:export")
    @Log(title = "计算单位定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UnitOfAccount unitOfAccount)
    {
        List<UnitOfAccount> list = unitOfAccountService.selectUnitOfAccountList(unitOfAccount);
        ExcelUtil<UnitOfAccount> util = new ExcelUtil<UnitOfAccount>(UnitOfAccount.class);
        util.exportExcel(response, list, "计算单位定义数据");
    }

    /**
     * 获取计算单位定义详细信息
     */
    @RequiresPermissions("mast:account:query")
    @GetMapping(value = "/{unitId}")
    public AjaxResult getInfo(@PathVariable("unitId") Long unitId)
    {
        return success(unitOfAccountService.selectUnitOfAccountByUnitId(unitId));
    }

    /**
     * 新增计算单位定义
     */
    @RequiresPermissions("mast:account:add")
    @Log(title = "计算单位定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UnitOfAccount unitOfAccount)
    {
        return toAjax(unitOfAccountService.insertUnitOfAccount(unitOfAccount));
    }

    /**
     * 修改计算单位定义
     */
    @RequiresPermissions("mast:account:edit")
    @Log(title = "计算单位定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UnitOfAccount unitOfAccount)
    {
        return toAjax(unitOfAccountService.updateUnitOfAccount(unitOfAccount));
    }

    /**
     * 删除计算单位定义
     */
    @RequiresPermissions("mast:account:remove")
    @Log(title = "计算单位定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{unitIds}")
    public AjaxResult remove(@PathVariable Long[] unitIds)
    {
        return toAjax(unitOfAccountService.deleteUnitOfAccountByUnitIds(unitIds));
    }
}
