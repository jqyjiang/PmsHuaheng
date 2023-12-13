package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.SupplierLifecycle;
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
import com.hh.pms.service.ISupplierLifecycleService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 生命周期维护Controller
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@RestController
@RequestMapping("/lifecycle")
public class SupplierLifecycleController extends BaseController
{
    @Autowired
    private ISupplierLifecycleService supplierLifecycleService;

    /**
     * 查询生命周期维护列表
     */
    @RequiresPermissions("supplierpms:lifecycle:list")
    @GetMapping("/list")
    public TableDataInfo list(SupplierLifecycle supplierLifecycle)
    {
        startPage();
        List<SupplierLifecycle> list = supplierLifecycleService.selectSupplierLifecycleList(supplierLifecycle);
        return getDataTable(list);
    }

    /**
     * 导出生命周期维护列表
     */
    @RequiresPermissions("supplierpms:lifecycle:export")
    @Log(title = "生命周期维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierLifecycle supplierLifecycle)
    {
        List<SupplierLifecycle> list = supplierLifecycleService.selectSupplierLifecycleList(supplierLifecycle);
        ExcelUtil<SupplierLifecycle> util = new ExcelUtil<SupplierLifecycle>(SupplierLifecycle.class);
        util.exportExcel(response, list, "生命周期维护数据");
    }

    /**
     * 获取生命周期维护详细信息
     */
    @RequiresPermissions("supplierpms:lifecycle:query")
    @GetMapping(value = "/{lifecycleId}")
    public AjaxResult getInfo(@PathVariable("lifecycleId") Long lifecycleId)
    {
        return success(supplierLifecycleService.selectSupplierLifecycleByLifecycleId(lifecycleId));
    }

    /**
     * 新增生命周期维护
     */
    @RequiresPermissions("supplierpms:lifecycle:add")
    @Log(title = "生命周期维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierLifecycle supplierLifecycle)
    {
        return toAjax(supplierLifecycleService.insertSupplierLifecycle(supplierLifecycle));
    }

    /**
     * 修改生命周期维护
     */
    @RequiresPermissions("supplierpms:lifecycle:edit")
    @Log(title = "生命周期维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierLifecycle supplierLifecycle)
    {
        return toAjax(supplierLifecycleService.updateSupplierLifecycle(supplierLifecycle));
    }

    /**
     * 删除生命周期维护
     */
    @RequiresPermissions("supplierpms:lifecycle:remove")
    @Log(title = "生命周期维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lifecycleIds}")
    public AjaxResult remove(@PathVariable Long[] lifecycleIds)
    {
        return toAjax(supplierLifecycleService.deleteSupplierLifecycleByLifecycleIds(lifecycleIds));
    }
}
