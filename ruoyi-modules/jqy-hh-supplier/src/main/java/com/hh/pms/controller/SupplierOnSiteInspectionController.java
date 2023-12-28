package com.hh.pms.controller;

import java.beans.Transient;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.hh.pms.service.ISupplierMaterialListService;
import com.ruoyi.system.api.domain.SupplierMaterialList;
import com.ruoyi.system.api.domain.SupplierOnSiteInspection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.hh.pms.service.ISupplierOnSiteInspectionService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 现场考察Controller
 * 
 * @author ruoyi
 * @date 2023-12-15
 */
@RestController
@RequestMapping("/inspection")
public class SupplierOnSiteInspectionController extends BaseController
{
    @Autowired
    private ISupplierOnSiteInspectionService supplierOnSiteInspectionService;

    @Autowired
    private ISupplierMaterialListService supplierMaterialListService;
    /**
     * 查询现场考察列表
     */
    @RequiresPermissions("supplierpms:inspection:list")
    @GetMapping("/list")
    public TableDataInfo list(SupplierOnSiteInspection supplierOnSiteInspection)
    {
        startPage();
        List<SupplierOnSiteInspection> list = supplierOnSiteInspectionService.selectSupplierOnSiteInspectionList(supplierOnSiteInspection);
        return getDataTable(list);
    }

    /**
     * 导出现场考察列表
     */
    @RequiresPermissions("supplierpms:inspection:export")
    @Log(title = "现场考察", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierOnSiteInspection supplierOnSiteInspection)
    {
        List<SupplierOnSiteInspection> list = supplierOnSiteInspectionService.selectSupplierOnSiteInspectionList(supplierOnSiteInspection);
        ExcelUtil<SupplierOnSiteInspection> util = new ExcelUtil<SupplierOnSiteInspection>(SupplierOnSiteInspection.class);
        util.exportExcel(response, list, "现场考察数据");
    }

    /**
     * 获取现场考察详细信息
     */
    @Transactional
    @RequiresPermissions("supplierpms:inspection:query")
    @GetMapping(value = "/{soiId}")
    public AjaxResult getInfo(@PathVariable("soiId") Long soiId)
    {
        SupplierOnSiteInspection supplierOnSiteInspection = supplierOnSiteInspectionService.selectSupplierOnSiteInspectionBySoiId(soiId);
        List<SupplierMaterialList> supplierMaterialLists = supplierMaterialListService.selectSupplierMaterialListBySoiId(soiId);
        supplierOnSiteInspection.setSupplierMaterialListList(supplierMaterialLists);
        return success(supplierOnSiteInspection);
    }

    /**
     * 新增现场考察
     */
    @RequiresPermissions("supplierpms:inspection:add")
    @Log(title = "现场考察", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierOnSiteInspection supplierOnSiteInspection)
    {
        return toAjax(supplierOnSiteInspectionService.insertSupplierOnSiteInspection(supplierOnSiteInspection));
    }

    /**
     * 修改现场考察
     */
    @Transactional
    @RequiresPermissions("supplierpms:inspection:edit")
    @Log(title = "现场考察", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierOnSiteInspection supplierOnSiteInspection)
    {
        for (SupplierMaterialList item : supplierOnSiteInspection.getSupplierMaterialListList()) {
            supplierMaterialListService.updateSupplierMaterialList(item);
        }
        return toAjax(supplierOnSiteInspectionService.updateSupplierOnSiteInspection(supplierOnSiteInspection));
    }

    /**
     * 删除现场考察
     */
    @RequiresPermissions("supplierpms:inspection:remove")
    @Log(title = "现场考察", businessType = BusinessType.DELETE)
	@DeleteMapping("/{soiIds}")
    public AjaxResult remove(@PathVariable Long[] soiIds)
    {
        return toAjax(supplierOnSiteInspectionService.deleteSupplierOnSiteInspectionBySoiIds(soiIds));
    }
}
