package com.hh.demand.controller;

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
import com.hh.demand.domain.MaterialRequirement;
import com.hh.demand.service.IMaterialRequirementService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 采购需求申请Controller
 * 
 * @author ruoyi
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/requirement")
public class MaterialRequirementController extends BaseController
{
    @Autowired
    private IMaterialRequirementService materialRequirementService;

    /**
     * 查询采购需求申请列表
     */
    @RequiresPermissions("demand:requirement:list")
    @GetMapping("/list")
    public TableDataInfo list(MaterialRequirement materialRequirement)
    {
        startPage();
        List<MaterialRequirement> list = materialRequirementService.selectMaterialRequirementList(materialRequirement);
        return getDataTable(list);
    }

    /**
     * 导出采购需求申请列表
     */
    @RequiresPermissions("demand:requirement:export")
    @Log(title = "采购需求申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialRequirement materialRequirement)
    {
        List<MaterialRequirement> list = materialRequirementService.selectMaterialRequirementList(materialRequirement);
        ExcelUtil<MaterialRequirement> util = new ExcelUtil<MaterialRequirement>(MaterialRequirement.class);
        util.exportExcel(response, list, "采购需求申请数据");
    }

    /**
     * 获取采购需求申请详细信息
     */
    @RequiresPermissions("demand:requirement:query")
    @GetMapping(value = "/{requirementId}")
    public AjaxResult getInfo(@PathVariable("requirementId") Long requirementId)
    {
        return success(materialRequirementService.selectMaterialRequirementByRequirementId(requirementId));
    }

    /**
     * 新增采购需求申请
     */
    @RequiresPermissions("demand:requirement:add")
    @Log(title = "采购需求申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialRequirement materialRequirement)
    {
        return toAjax(materialRequirementService.insertMaterialRequirement(materialRequirement));
    }

    /**
     * 修改采购需求申请
     */
    @RequiresPermissions("demand:requirement:edit")
    @Log(title = "采购需求申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialRequirement materialRequirement)
    {
        return toAjax(materialRequirementService.updateMaterialRequirement(materialRequirement));
    }

    /**
     * 删除采购需求申请
     */
    @RequiresPermissions("demand:requirement:remove")
    @Log(title = "采购需求申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{requirementIds}")
    public AjaxResult remove(@PathVariable Long[] requirementIds)
    {
        return toAjax(materialRequirementService.deleteMaterialRequirementByRequirementIds(requirementIds));
    }
}
