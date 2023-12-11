package com.hh.pms.mast.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.Material;
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
import com.hh.pms.mast.service.IMaterialService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 物料维护Controller
 *
 * @author ruoyi
 * @date 2023-11-29
 */
@RestController
@RequestMapping("/material")
public class MaterialController extends BaseController
{
    @Autowired
    private IMaterialService materialService;

    /**
     * 查询物料维护列表
     */
    @RequiresPermissions("mast:material:list")
    @GetMapping("/list")
    public TableDataInfo list(Material material)
    {
        System.out.println("传递的对象:"+material);
        startPage();
        List<Material> list = materialService.selectMaterialList(material);
        return getDataTable(list);
    }
    @RequiresPermissions("mast:material:list")
    @GetMapping("/listAll")
    public TableDataInfo lists(Material material)
    {
        List<Material> list = materialService.selectMaterialList(material);
//        System.out.println(list);
        return getDataTable(list);
    }

    /**
     * 导出物料维护列表
     */
    @RequiresPermissions("mast:material:export")
    @Log(title = "物料维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Material material)
    {
        List<Material> list = materialService.selectMaterialList(material);
        ExcelUtil<Material> util = new ExcelUtil<Material>(Material.class);
        util.exportExcel(response, list, "物料维护数据");
    }

    /**
     * 获取物料维护详细信息
     */
    @RequiresPermissions("mast:material:query")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return success(materialService.selectMaterialByMaterialId(materialId));
    }

    /**
     * 新增物料维护
     */
    @RequiresPermissions("mast:material:add")
    @Log(title = "物料维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Material material)
    {
        return toAjax(materialService.insertMaterial(material));
    }

    /**
     * 修改物料维护
     */
    @RequiresPermissions("mast:material:edit")
    @Log(title = "物料维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Material material)
    {
        return toAjax(materialService.updateMaterial(material));
    }

    /**
     * 删除物料维护
     */
    @RequiresPermissions("mast:material:remove")
    @Log(title = "物料维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(materialService.deleteMaterialByMaterialIds(materialIds));
    }
}
