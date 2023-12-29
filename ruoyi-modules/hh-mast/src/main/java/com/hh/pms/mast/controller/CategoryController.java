package com.hh.pms.mast.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.mast.service.ICategoryService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 品类Controller
 *
 * @author ruoyi
 * @date 2023-11-29
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController
{
    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询品类列表
     */
    @RequiresPermissions("mast:category:list")
    @GetMapping("/list")
    public TableDataInfo list(Category category)
    {
        startPage();//分页
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }

    //有分页弹框
    @RequiresPermissions("mast:category:list")
    @GetMapping("/lists")
    public TableDataInfo lists1(Category category)
    {
        startPage();//分页
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }
    //无分页
    @RequiresPermissions("mast:category:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(Category category)
    {
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }
    /**
     * 导出品类列表
     */
    @RequiresPermissions("mast:category:export")
    @Log(title = "品类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Category category)
    {
        List<Category> list = categoryService.selectCategoryList(category);
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category.class);
        util.exportExcel(response, list, "品类数据");
    }

    /**
     * 获取品类详细信息
     */
    @RequiresPermissions("mast:category:query")
    @GetMapping(value = "/{categoryid}")
    public AjaxResult getInfo(@PathVariable("categoryid") Long categoryid)
    {
        return success(categoryService.selectCategoryByCategoryid(categoryid));
    }

    /**
     * 新增品类
     */
    @RequiresPermissions("mast:category:add")
    @Log(title = "品类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Category category)
    {
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改品类
     */
    @RequiresPermissions("mast:category:edit")
    @Log(title = "品类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Category category)
    {
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除品类
     */
    @RequiresPermissions("mast:category:remove")
    @Log(title = "品类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryids}")
    public AjaxResult remove(@PathVariable Long[] categoryids)
    {
        return toAjax(categoryService.deleteCategoryByCategoryids(categoryids));
    }

    /**
     * 批量新增
     */
    @RequiresPermissions("mast:category:batchCategory")
    @Log(title = "品类", businessType = BusinessType.INSERT)
    @PostMapping("/batchCategory")
    public AjaxResult batchCategory(@RequestBody List<Category> categoryList) // 添加 @RequestBody 注解
    {
        return toAjax(categoryService.batchCategory(categoryList));
    }

}
