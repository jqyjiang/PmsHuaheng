package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hh.pms.service.IMaterialPoolService;
import com.ruoyi.system.api.domain.MaterialPool;
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
 * 采购需求池Controller
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
@RestController
@RequestMapping("/pool")
public class MaterialPoolController extends BaseController
{
    @Autowired
    private IMaterialPoolService materialService;

    /**
     * 查询采购需求池列表
     */
    @RequiresPermissions("procure:pool:list")
    @GetMapping("/list")
    public TableDataInfo list(MaterialPool materialPool)
    {
        startPage();
        List<MaterialPool> list = materialService.selectMaterialList(materialPool);
        return getDataTable(list);
    }

    /**
     * 导出采购需求池列表
     */
    @RequiresPermissions("procure:pool:export")
    @Log(title = "采购需求池", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialPool materialPool)
    {
        List<MaterialPool> list = materialService.selectMaterialList(materialPool);
        ExcelUtil<MaterialPool> util = new ExcelUtil<MaterialPool>(MaterialPool.class);
        util.exportExcel(response, list, "采购需求池数据");
    }

    /**
     * 获取采购需求池详细信息
     */
    @RequiresPermissions("procure:pool:query")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return success(materialService.selectMaterialByMaterialId(materialId));
    }

    /**
     * 新增采购需求池
     */
    @RequiresPermissions("procure:pool:add")
    @Log(title = "采购需求池", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialPool materialPool)
    {
        return toAjax(materialService.insertMaterial(materialPool));
    }

    /**
     * 修改采购需求池
     */
    @RequiresPermissions("procure:pool:edit")
    @Log(title = "采购需求池", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialPool materialPool)
    {
        return toAjax(materialService.updateMaterial(materialPool));
    }

    /**
     * 删除采购需求池
     */
    @RequiresPermissions("procure:pool:remove")
    @Log(title = "采购需求池", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(materialService.deleteMaterialByMaterialIds(materialIds));
    }
}
