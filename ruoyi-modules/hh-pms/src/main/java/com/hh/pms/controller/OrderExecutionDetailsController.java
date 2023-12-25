package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.OrderExecutionDetails;
import com.ruoyi.system.api.domain.OrderManager;
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
import com.hh.pms.service.IOrderExecutionDetailsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * mingxiController
 *
 * @author yt
 * @date 2023-12-14
 */
@RestController
@RequestMapping("/orderDetail")
public class OrderExecutionDetailsController extends BaseController
{
    @Autowired
    private IOrderExecutionDetailsService orderExecutionDetailsService;

    /**
     * 查询待收货列表
     */
    @RequiresPermissions("pms:orderDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderExecutionDetails orderExecutionDetails)
    {
        startPage();
        List<OrderExecutionDetails> list = orderExecutionDetailsService.selectOrderExecutionDetailsList(orderExecutionDetails);
        return getDataTable(list);
    }

    /**
     * 查询收货单列表
     * @param orderExecutionDetails
     * @return
     */
    @RequiresPermissions("pms:orderDetail:list")
    @GetMapping("/receiptList")
    public TableDataInfo receiptList(OrderExecutionDetails orderExecutionDetails)
    {
        startPage();
        List<OrderExecutionDetails> list = orderExecutionDetailsService.selectOrderReceiptList(orderExecutionDetails);
        return getDataTable(list);
    }
    /**
     * 导出mingxi列表
     */
    @RequiresPermissions("pms:orderDetail:export")
    @Log(title = "mingxi", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderExecutionDetails orderExecutionDetails)
    {
        List<OrderExecutionDetails> list = orderExecutionDetailsService.selectOrderExecutionDetailsList(orderExecutionDetails);
        ExcelUtil<OrderExecutionDetails> util = new ExcelUtil<OrderExecutionDetails>(OrderExecutionDetails.class);
        util.exportExcel(response, list, "mingxi数据");
    }

    /**
     * 获取mingxi详细信息
     */
    @RequiresPermissions("pms:orderDetail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderExecutionDetailsService.selectOrderExecutionDetailsById(id));
    }

    /**
     * 新增mingxi
     */
    @RequiresPermissions("pms:orderDetail:add")
    @Log(title = "mingxi", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderExecutionDetails orderExecutionDetails)
    {
        return toAjax(orderExecutionDetailsService.insertOrderExecutionDetails(orderExecutionDetails));
    }

    @RequiresPermissions("pms:orderDetail:add")
    @Log(title = "mingxi", businessType = BusinessType.INSERT)
    @PostMapping("/orderExecution")
    public AjaxResult add(@RequestBody OrderManager orderManager)
    {
        OrderExecutionDetails orderExecutionDetails = new OrderExecutionDetails();
        orderExecutionDetails.setOrderCode(orderManager.getOrderCode());
        orderExecutionDetails.setTaxTotal(orderManager.getTaxTotal());
        orderExecutionDetails.setTotalDemand(orderManager.getTotalDemand());
        orderExecutionDetails.setAwaitingDeliveryQuantity(orderManager.getTotalDemand());
        orderExecutionDetails.setAwaitingDeliveryAmount(orderManager.getTaxTotal());
        orderExecutionDetails.setOrderStatus("1");
//        orderExecutionDetails.setSupplier(orderManager.getSupplierDetails().getSbiName());
        return toAjax(orderExecutionDetailsService.insertOrderExecutionDetails(orderExecutionDetails));
    }

    /**
     * 修改mingxi
     */
    @RequiresPermissions("pms:orderDetail:edit")
    @Log(title = "mingxi", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderExecutionDetails orderExecutionDetails)
    {
        return toAjax(orderExecutionDetailsService.updateOrderExecutionDetails(orderExecutionDetails));
    }

    /**
     * 修改mingxi
     */
    @RequiresPermissions("pms:orderDetail:edit")
    @Log(title = "mingxi", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateEecel")
    public AjaxResult edit1(@RequestBody OrderExecutionDetails orderExecutionDetails) throws Exception {
        int rows = 0;
        try {
            rows=orderExecutionDetailsService.updateOrderExecutionDetailsandDelivery(orderExecutionDetails);
        } catch (Exception e) {
            // 发货数量大于需求数量时抛出异常
            return AjaxResult.error(e.getMessage());
        }
        return toAjax(rows);
    }

    /**
     * 删除mingxi
     */
    @RequiresPermissions("pms:orderDetail:remove")
    @Log(title = "mingxi", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderExecutionDetailsService.deleteOrderExecutionDetailsByIds(ids));
    }
}
