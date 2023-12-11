package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.system.api.domain.Products;
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
import com.hh.pms.service.IProductsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 *  产品信息Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/products")
public class ProductsController extends BaseController
{
    @Autowired
    private IProductsService productsService;

    /**
     * 查询 产品信息列表
     */
    @RequiresPermissions("pms:products:list")
    @GetMapping("/list")
    public TableDataInfo list(Products products)
    {
        startPage();
        List<Products> list = productsService.selectProductsList(products);
        return getDataTable(list);
    }

    /**
     * 导出 产品信息列表
     */
    @RequiresPermissions("pms:products:export")
    @Log(title = " 产品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Products products)
    {
        List<Products> list = productsService.selectProductsList(products);
        ExcelUtil<Products> util = new ExcelUtil<Products>(Products.class);
        util.exportExcel(response, list, " 产品信息数据");
    }

    /**
     * 获取 产品信息详细信息
     */
    @RequiresPermissions("pms:products:query")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(productsService.selectProductsByProductId(productId));
    }

    /**
     * 新增 产品信息
     */
    @RequiresPermissions("pms:products:add")
    @Log(title = " 产品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Products products)
    {
        return toAjax(productsService.insertProducts(products));
    }

    /**
     * 修改 产品信息
     */
    @RequiresPermissions("pms:products:edit")
    @Log(title = " 产品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Products products)
    {
        return toAjax(productsService.updateProducts(products));
    }

    /**
     * 删除 产品信息
     */
    @RequiresPermissions("pms:products:remove")
    @Log(title = " 产品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(productsService.deleteProductsByProductIds(productIds));
    }
}
