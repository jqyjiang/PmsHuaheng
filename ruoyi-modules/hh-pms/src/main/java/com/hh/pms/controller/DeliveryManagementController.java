package com.hh.pms.controller;

import com.hh.pms.service.IOrderExecutionDetailsService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.OrderExecutionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/orderDelivery")
public class DeliveryManagementController extends BaseController {
    @Autowired
    private IOrderExecutionDetailsService orderExecutionDetailsService;

    /**
     * 查询收货管理列表
     */
    @RequiresPermissions("pms:orderDelivery:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderExecutionDetails orderExecutionDetails)
    {
        startPage();
        List<OrderExecutionDetails> list = orderExecutionDetailsService.selectOrderExecutionDeliveryList(orderExecutionDetails);
        return getDataTable(list);
    }

    /**
     * 导出收货管理列表
     */
    @RequiresPermissions("pms:orderDelivery:export")
    @Log(title = "收货管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderExecutionDetails orderExecutionDetails)
    {
        List<OrderExecutionDetails> list = orderExecutionDetailsService.selectOrderExecutionDetailsList(orderExecutionDetails);
        ExcelUtil<OrderExecutionDetails> util = new ExcelUtil<OrderExecutionDetails>(OrderExecutionDetails.class);
        util.exportExcel(response, list, "mingxi数据");
    }

    /**
     * 获取收货管理详细信息
     */
    @RequiresPermissions("pms:orderDelivery:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderExecutionDetailsService.selectOrderExecutionDetailsById(id));
    }

    /**
     * 新增收货管理
     */
    @RequiresPermissions("pms:orderDelivery:add")
    @Log(title = "收货管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderExecutionDetails orderExecutionDetails)
    {
        return toAjax(orderExecutionDetailsService.insertOrderExecutionDetails(orderExecutionDetails));
    }

    /**
     * 修改收货管理
     */
    @RequiresPermissions("pms:orderDelivery:edit")
    @Log(title = "收货管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderExecutionDetails orderExecutionDetails)
    {
        return toAjax(orderExecutionDetailsService.updateOrderExecutionDetails(orderExecutionDetails));
    }


    /**
     * 删除收货管理
     */
    @RequiresPermissions("pms:orderDelivery:remove")
    @Log(title = "收货管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderExecutionDetailsService.deleteOrderExecutionDetailsByIds(ids));
    }
}
