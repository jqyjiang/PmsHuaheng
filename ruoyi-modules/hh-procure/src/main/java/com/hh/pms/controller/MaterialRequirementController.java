package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hh.pms.model.MaterialClient;
import com.ruoyi.system.api.domain.Currency;
import com.ruoyi.system.api.domain.MaterialRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.service.IMaterialRequirementService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 采购需求申请Controller
 *
 * @author ruoyi
 * @date 2023-11-28
 */
@RestController
@RequestMapping("/requirement")
public class MaterialRequirementController extends BaseController
{
    @Autowired
    private IMaterialRequirementService materialRequirementService;

    @Autowired
    private MaterialClient materialClient;


    /**
     * 查询采购需求申请列表
     */
    @RequiresPermissions("procure:requirement:list")
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
    @RequiresPermissions("procure:requirement:export")
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
    @RequiresPermissions("procure:requirement:query")
    @GetMapping(value = "/{requirementId}")
    public AjaxResult getInfo(@PathVariable("requirementId") Long requirementId)
    {
        return success(materialRequirementService.selectMaterialRequirementByRequirementId(requirementId));
    }

    /**
     * 新增采购需求申请
     */
    @RequiresPermissions("procure:requirement:add")
    @Log(title = "采购需求申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialRequirement materialRequirement)
    {
        System.out.println("fffff:"+materialRequirement.getMaterialInformations());
        return toAjax(materialRequirementService.insertMaterialRequirement(materialRequirement));
    }

    /**
     * 修改采购需求申请
     *
     */
    @RequiresPermissions("procure:requirement:edit")
    @Log(title = "采购需求申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialRequirement materialRequirement)
    {
        return toAjax(materialRequirementService.updateMaterialRequirement(materialRequirement));
    }

    /**
     * 删除采购需求申请
     */
    @RequiresPermissions("procure:requirement:remove")
    @Log(title = "采购需求申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{requirementIds}")
    public AjaxResult remove(@PathVariable Long[] requirementIds)
    {
        return toAjax(materialRequirementService.deleteMaterialRequirementByRequirementIds(requirementIds));
    }

    /**
     * 查询币种定义表
     * @param currency
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/listCurrency")
    public TableDataInfo listCurrency(Currency currency, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return materialClient.list(currency, pageNum, pageSize);
    }

    /**采购需求类型统计分析*/
    @RequiresPermissions("procure:requirement:listDemandType")
    @GetMapping("/listDemandType")
    public TableDataInfo listDemandType()
    {
        List<MaterialRequirement> list = materialRequirementService.selectDemandType();
        System.out.println(list);
        return getDataTable(list);
    }

}
