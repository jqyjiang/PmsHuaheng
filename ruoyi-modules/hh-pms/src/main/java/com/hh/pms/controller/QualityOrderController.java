package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.QualityOrder;
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

import com.hh.pms.service.IQualityOrderService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 质检单Controller
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
@RestController
@RequestMapping("/order")
public class QualityOrderController extends BaseController
{
    @Autowired
    private IQualityOrderService qualityOrderService;

    /**
     * 查询质检单列表
     */
    @RequiresPermissions("pms:order:list")
    @GetMapping("/list")
    public TableDataInfo list(QualityOrder qualityOrder)
    {
        startPage();
        List<QualityOrder> list = qualityOrderService.selectQualityOrderList(qualityOrder);
        return getDataTable(list);
    }

    /**
     * 导出质检单列表
     */
    @RequiresPermissions("pms:order:export")
    @Log(title = "质检单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QualityOrder qualityOrder)
    {
        List<QualityOrder> list = qualityOrderService.selectQualityOrderList(qualityOrder);
        ExcelUtil<QualityOrder> util = new ExcelUtil<QualityOrder>(QualityOrder.class);
        util.exportExcel(response, list, "质检单数据");
    }

    /**
     * 获取质检单详细信息
     */
    @RequiresPermissions("pms:order:query")
    @GetMapping(value = "/{qualityId}")
    public AjaxResult getInfo(@PathVariable("qualityId") Long qualityId)
    {
        return success(qualityOrderService.selectQualityOrderByQualityId(qualityId));
    }

    /**
     * 新增质检单
     */
    @RequiresPermissions("pms:order:add")
    @Log(title = "质检单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QualityOrder qualityOrder)
    {
        return toAjax(qualityOrderService.insertQualityOrder(qualityOrder));
    }

    /**
     * 修改质检单
     */
    @RequiresPermissions("pms:order:edit")
    @Log(title = "质检单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QualityOrder qualityOrder)
    {
        return toAjax(qualityOrderService.updateQualityOrder(qualityOrder));
    }

    /**
     * 删除质检单
     */
    @RequiresPermissions("pms:order:remove")
    @Log(title = "质检单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{qualityIds}")
    public AjaxResult remove(@PathVariable Long[] qualityIds)
    {
        return toAjax(qualityOrderService.deleteQualityOrderByQualityIds(qualityIds));
    }
}
