package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.SupplierClassification;
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
import com.hh.pms.service.ISupplierClassificationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 供应商分类定义Controller
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
@RestController
@RequestMapping("/classification")
public class SupplierClassificationController extends BaseController
{
    @Autowired
    private ISupplierClassificationService supplierClassificationService;

    /**
     * 查询供应商分类定义列表
     */
    @RequiresPermissions("supplierpms:classification:list")
    @GetMapping("/list")
    public TableDataInfo list(SupplierClassification supplierClassification)
    {
        startPage();
        List<SupplierClassification> list = supplierClassificationService.selectSupplierClassificationList(supplierClassification);
        return getDataTable(list);
    }

    /**
     * 导出供应商分类定义列表
     */
    @RequiresPermissions("supplierpms:classification:export")
    @Log(title = "供应商分类定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierClassification supplierClassification)
    {
        List<SupplierClassification> list = supplierClassificationService.selectSupplierClassificationList(supplierClassification);
        ExcelUtil<SupplierClassification> util = new ExcelUtil<SupplierClassification>(SupplierClassification.class);
        util.exportExcel(response, list, "供应商分类定义数据");
    }

    /**
     * 获取供应商分类定义详细信息
     */
    @RequiresPermissions("supplierpms:classification:query")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") Long classId)
    {
        return success(supplierClassificationService.selectSupplierClassificationByClassId(classId));
    }

    /**
     * 新增供应商分类定义
     */
    @RequiresPermissions("supplierpms:classification:add")
    @Log(title = "供应商分类定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierClassification supplierClassification)
    {
        return toAjax(supplierClassificationService.insertSupplierClassification(supplierClassification));
    }

    /**
     * 修改供应商分类定义
     */
    @RequiresPermissions("supplierpms:classification:edit")
    @Log(title = "供应商分类定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierClassification supplierClassification)
    {
        return toAjax(supplierClassificationService.updateSupplierClassification(supplierClassification));
    }

    /**
     * 删除供应商分类定义
     */
    @RequiresPermissions("supplierpms:classification:remove")
    @Log(title = "供应商分类定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable Long[] classIds)
    {
        return toAjax(supplierClassificationService.deleteSupplierClassificationByClassIds(classIds));
    }
}
