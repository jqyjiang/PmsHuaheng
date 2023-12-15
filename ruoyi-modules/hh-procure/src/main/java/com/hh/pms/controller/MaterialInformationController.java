package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.MaterialInformation;
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
import com.hh.pms.service.IMaterialInformationService;
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
@RequestMapping("/information")
public class MaterialInformationController extends BaseController
{
    @Autowired
    private IMaterialInformationService materialInformationService;

    /**
     * 查询采购需求池列表
     */
    @RequiresPermissions("procure:information:list")
    @GetMapping("/list")
    public TableDataInfo list(MaterialInformation materialInformation)
    {
        startPage();
        List<MaterialInformation> list = materialInformationService.selectMaterialInformationList(materialInformation);
        return getDataTable(list);
    }

    /**
     * 导出采购需求池列表
     */
    @RequiresPermissions("procure:information:export")
    @Log(title = "采购需求池", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialInformation materialInformation)
    {
        List<MaterialInformation> list = materialInformationService.selectMaterialInformationList(materialInformation);
        ExcelUtil<MaterialInformation> util = new ExcelUtil<MaterialInformation>(MaterialInformation.class);
        util.exportExcel(response, list, "采购需求池数据");
    }

    /**
     * 获取采购需求池详细信息
     */
    @RequiresPermissions("procure:information:query")
    @GetMapping(value = "/{miId}")
    public AjaxResult getInfo(@PathVariable("miId") Integer miId)
    {
        return success(materialInformationService.selectMaterialInformationByMiId(miId));
    }

    /**
     * 新增采购需求池
     */
    @RequiresPermissions("procure:information:add")
    @Log(title = "采购需求池", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialInformation materialInformation)
    {
        return toAjax(materialInformationService.insertMaterialInformation(materialInformation));
    }

    /**
     * 修改采购需求池
     */
    @RequiresPermissions("procure:information:edit")
    @Log(title = "采购需求池", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialInformation materialInformation)
    {
        return toAjax(materialInformationService.updateMaterialInformation(materialInformation));
    }

    /**
     * 删除采购需求池
     */
    @RequiresPermissions("procure:information:remove")
    @Log(title = "采购需求池", businessType = BusinessType.DELETE)
    @DeleteMapping("/{miIds}")
    public AjaxResult remove(@PathVariable Integer[] miIds)
    {
        return toAjax(materialInformationService.deleteMaterialInformationByMiIds(miIds));
    }



    /**
     * 修改采购需求池状态
     */
    @RequiresPermissions("procure:information:edit")
    @PutMapping("zg/{miId}")
    public AjaxResult editStatus(@PathVariable("miId") Integer[] miId)
    {
        return toAjax(materialInformationService.updateRequirementStatus(miId));
    }
    @RequiresPermissions("procure:information:edit")
    @PutMapping("zf/{miId}")
    public AjaxResult editStatusCancel(@PathVariable("miId") Integer[] miId)
    {
        return toAjax(materialInformationService.updateRequirementStatusCancel(miId));
    }

}
