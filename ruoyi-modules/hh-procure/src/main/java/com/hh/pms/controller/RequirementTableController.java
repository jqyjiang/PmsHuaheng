package com.hh.pms.controller;

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
import com.hh.pms.domain.RequirementTable;
import com.hh.pms.service.IRequirementTableService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 需求类型Controller
 * 
 * @author ruoyi
 * @date 2023-12-01
 */
@RestController
@RequestMapping("/table")
public class RequirementTableController extends BaseController
{
    @Autowired
    private IRequirementTableService requirementTableService;

    /**
     * 查询需求类型列表
     */
    @RequiresPermissions("procure:table:list")
    @GetMapping("/list")
    public TableDataInfo list(RequirementTable requirementTable)
    {
        startPage();
        List<RequirementTable> list = requirementTableService.selectRequirementTableList(requirementTable);
        return getDataTable(list);
    }

    /**
     * 导出需求类型列表
     */
    @RequiresPermissions("procure:table:export")
    @Log(title = "需求类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RequirementTable requirementTable)
    {
        List<RequirementTable> list = requirementTableService.selectRequirementTableList(requirementTable);
        ExcelUtil<RequirementTable> util = new ExcelUtil<RequirementTable>(RequirementTable.class);
        util.exportExcel(response, list, "需求类型数据");
    }

    /**
     * 获取需求类型详细信息
     */
    @RequiresPermissions("procure:table:query")
    @GetMapping(value = "/{requirementId}")
    public AjaxResult getInfo(@PathVariable("requirementId") Long requirementId)
    {
        return success(requirementTableService.selectRequirementTableByRequirementId(requirementId));
    }

    /**
     * 新增需求类型
     */
    @RequiresPermissions("procure:table:add")
    @Log(title = "需求类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RequirementTable requirementTable)
    {
        return toAjax(requirementTableService.insertRequirementTable(requirementTable));
    }

    /**
     * 修改需求类型
     */
    @RequiresPermissions("procure:table:edit")
    @Log(title = "需求类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RequirementTable requirementTable)
    {
        return toAjax(requirementTableService.updateRequirementTable(requirementTable));
    }

    /**
     * 删除需求类型
     */
    @RequiresPermissions("procure:table:remove")
    @Log(title = "需求类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{requirementIds}")
    public AjaxResult remove(@PathVariable Long[] requirementIds)
    {
        return toAjax(requirementTableService.deleteRequirementTableByRequirementIds(requirementIds));
    }
}
