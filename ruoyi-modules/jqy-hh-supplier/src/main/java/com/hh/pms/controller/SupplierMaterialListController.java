package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.hh.pms.service.ISupplierMaterialListService;
import com.ruoyi.system.api.domain.SupplierMaterialList;
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
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 供应商物料Controller
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@RestController
@RequestMapping("/list")
public class SupplierMaterialListController extends BaseController
{
    @Autowired
    private ISupplierMaterialListService supplierMaterialListService;

    /**
     * 查询供应商物料列表
     */
    @RequiresPermissions("system:list:list")
    @GetMapping("/list")
    public TableDataInfo list(SupplierMaterialList supplierMaterialList)
    {
        startPage();
        List<SupplierMaterialList> list = supplierMaterialListService.selectSupplierMaterialListList(supplierMaterialList);
        return getDataTable(list);
    }

    /**
     * 导出供应商物料列表
     */
    @RequiresPermissions("system:list:export")
    @Log(title = "供应商物料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierMaterialList supplierMaterialList)
    {
        List<SupplierMaterialList> list = supplierMaterialListService.selectSupplierMaterialListList(supplierMaterialList);
        ExcelUtil<SupplierMaterialList> util = new ExcelUtil<SupplierMaterialList>(SupplierMaterialList.class);
        util.exportExcel(response, list, "供应商物料数据");
    }

    /**
     * 获取供应商物料详细信息
     */
    @RequiresPermissions("system:list:query")
    @GetMapping(value = "/{supplierMaterialId}")
    public AjaxResult getInfo(@PathVariable("supplierMaterialId") Long supplierMaterialId)
    {
        return success(supplierMaterialListService.selectSupplierMaterialListBySupplierMaterialId(supplierMaterialId));
    }

    /**
     * 新增供应商物料
     */
    @RequiresPermissions("system:list:add")
    @Log(title = "供应商物料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierMaterialList supplierMaterialList)
    {
        return toAjax(supplierMaterialListService.insertSupplierMaterialList(supplierMaterialList));
    }

    /**
     * 修改供应商物料
     */
    @RequiresPermissions("system:list:edit")
    @Log(title = "供应商物料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierMaterialList supplierMaterialList)
    {
        return toAjax(supplierMaterialListService.updateSupplierMaterialList(supplierMaterialList));
    }

    /**
     * 删除供应商物料
     */
    @RequiresPermissions("system:list:remove")
    @Log(title = "供应商物料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{supplierMaterialIds}")
    public AjaxResult remove(@PathVariable Long[] supplierMaterialIds)
    {
        return toAjax(supplierMaterialListService.deleteSupplierMaterialListBySupplierMaterialIds(supplierMaterialIds));
    }
}
