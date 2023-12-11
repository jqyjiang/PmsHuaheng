package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.OrderMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.service.IOrderMaterialService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 订单物料明细Controller
 *
 * @author ruoyi
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/materials")
public class OrderMaterialController extends BaseController
{
    @Autowired
    private IOrderMaterialService orderMaterialService;

    /**
     * 查询订单物料明细列表
     */
    @RequiresPermissions("pms:materials:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderMaterial orderMaterial)
    {
        startPage();
        List<OrderMaterial> list = orderMaterialService.selectOrderMaterialList(orderMaterial);
        return getDataTable(list);
    }
    @RequiresPermissions("pms:materials:query")
    @GetMapping(value = "/findTaskMaterial/{taskCode}")
    public AjaxResult findTaskMaterial(@PathVariable("taskCode") String taskCode){
        return success(orderMaterialService.findTaskMaterial(taskCode));
    }
    /**
     * 导出订单物料明细列表
     */
    @RequiresPermissions("pms:materials:export")
    @Log(title = "订单物料明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderMaterial orderMaterial)
    {
        List<OrderMaterial> list = orderMaterialService.selectOrderMaterialList(orderMaterial);
        ExcelUtil<OrderMaterial> util = new ExcelUtil<OrderMaterial>(OrderMaterial.class);
        util.exportExcel(response, list, "订单物料明细数据");
    }

    /**
     * 获取订单物料明细详细信息
     */
    @RequiresPermissions("pms:materials:query")
    @GetMapping(value = "/{orId}")
    public AjaxResult getInfo(@PathVariable("orId") Long orId)
    {
        return success(orderMaterialService.selectOrderMaterialByOrId(orId));
    }

    /**
     * 新增订单物料明细
     */
    @RequiresPermissions("pms:materials:add")
    @Log(title = "订单物料明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderMaterial orderMaterial)
    {
        return toAjax(orderMaterialService.insertOrderMaterial(orderMaterial));
    }

    /**
     * 修改订单物料明细
     */
    @RequiresPermissions("pms:materials:edit")
    @Log(title = "订单物料明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderMaterial orderMaterial)
    {
        return toAjax(orderMaterialService.updateOrderMaterial(orderMaterial));
    }

    /**
     * 删除订单物料明细
     */
    @RequiresPermissions("pms:materials:remove")
    @Log(title = "订单物料明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orIds}")
    public AjaxResult remove(@PathVariable Long[] orIds)
    {
        return toAjax(orderMaterialService.deleteOrderMaterialByOrIds(orIds));
    }
}
