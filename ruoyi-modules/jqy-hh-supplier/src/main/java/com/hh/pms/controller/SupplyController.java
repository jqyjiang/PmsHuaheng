package com.hh.pms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hh.pms.service.ISupplierMaterialListService;
import com.ruoyi.system.api.domain.MaterialInformation;
import com.ruoyi.system.api.domain.SupplierMaterialList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.ruoyi.system.api.domain.Supply;
import com.hh.pms.service.ISupplyService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 供货管理Controller
 *
 * @author ruoyi
 * @date 2023-12-06
 */
@RestController
@RequestMapping("/supply")
public class SupplyController extends BaseController
{
    @Autowired
    private ISupplyService supplyService;

    @Autowired
    private ISupplierMaterialListService supplierMaterialListService;
    /**
     * 查询供货管理列表
     */
    @RequiresPermissions("supplierpms:supply:list")
    @GetMapping("/list")
    public TableDataInfo list(Supply supply)
    {
        startPage();
        List<Supply> list = supplyService.selectSupplyList(supply);

        System.out.println(list);
        return getDataTable(list);
    }

    /**
     * 导出供货管理列表
     */
    @RequiresPermissions("supplierpms:supply:export")
    @Log(title = "供货管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Supply supply)
    {
        List<Supply> list = supplyService.selectSupplyList(supply);
        ExcelUtil<Supply> util = new ExcelUtil<Supply>(Supply.class);
        util.exportExcel(response, list, "供货管理数据");
    }

    /**
     * 获取供货管理详细信息
     */
    @Transactional
    @RequiresPermissions("supplierpms:supply:query")
    @GetMapping(value = "/{supplyId}")
    public AjaxResult getInfo(@PathVariable("supplyId") Long supplyId)
    {
        Supply supply = supplyService.selectSupplyBySupplyId(supplyId);
        List<SupplierMaterialList> supplierMaterialLists = supplierMaterialListService.selectSupplierMaterialListBySupplyId(supplyId);
        System.out.println(supplierMaterialLists);
        supply.setSupplierMaterialList(supplierMaterialLists);
        return success(supply);
    }

    /**
     * 新增供货管理
     */
    @Transactional
    @RequiresPermissions("supplierpms:supply:add")
    @Log(title = "供货管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Supply supply)
    {
        Date day = new Date();
        supply.setCreationTime(day);
        int i = supplyService.insertSupply(supply);
        for (SupplierMaterialList item : supply.getSupplierMaterialList()) {
            item.setSupplyId(supply.getSupplyId());
            supplierMaterialListService.insertSupplierMaterialList(item);
        }
        return toAjax(i);
    }

    /**
     * 修改供货管理
     */
    @Transactional
    @RequiresPermissions("supplierpms:supply:edit")
    @Log(title = "供货管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Supply supply)
    {
        Date day = new Date();
        supply.setLastUpdateTime(day);
        int i = supplyService.updateSupply(supply);

        for (SupplierMaterialList item : supply.getSupplierMaterialList()) {
            supplierMaterialListService.updateSupplierMaterialList(item);
        }
        return toAjax(i);
    }

    /**
     * 删除供货管理
     */
    @Transactional
    @RequiresPermissions("supplierpms:supply:remove")
    @Log(title = "供货管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{supplyIds}")
    public AjaxResult remove(@PathVariable Long[] supplyIds)
    {
        int i = supplyService.deleteSupplyBySupplyIds(supplyIds);
        supplierMaterialListService.deleteSupplierMaterialListBySupplyIds(supplyIds);
        return toAjax(i);
    }

    /**
     * 简单查询公司
     */
    @RequiresPermissions("supplierpms:supply:list")
    @GetMapping("/query")
    public TableDataInfo Companies(){
        List<Supply> list = supplyService.selectCompanies();
        return getDataTable(list);
    }
}
