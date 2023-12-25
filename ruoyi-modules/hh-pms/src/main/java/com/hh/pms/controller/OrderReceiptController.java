package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.hh.pms.service.IReceiptDetailsService;
import com.ruoyi.system.api.domain.OrderReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;

import com.hh.pms.service.IOrderReceiptService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 订单收货管理Controller
 *
 * @author ruoyi
 * @date 2023-12-20
 */
@RestController
@RequestMapping("/receipt")
public class OrderReceiptController extends BaseController {
    @Autowired
    private IOrderReceiptService orderReceiptService;
    @Autowired
    private IReceiptDetailsService receiptDetailsService;

    /**
     * 查询订单收货管理列表
     */
    @RequiresPermissions("pms:receipt:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderReceipt orderReceipt) {
        startPage();
        List<OrderReceipt> list = orderReceiptService.selectOrderReceiptList(orderReceipt);
        return getDataTable(list);
    }

    /**
     * 导出订单收货管理列表
     */
    @RequiresPermissions("pms:receipt:export")
    @Log(title = "订单收货管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderReceipt orderReceipt) {
        List<OrderReceipt> list = orderReceiptService.selectOrderReceiptList(orderReceipt);
        ExcelUtil<OrderReceipt> util = new ExcelUtil<OrderReceipt>(OrderReceipt.class);
        util.exportExcel(response, list, "订单收货管理数据");
    }

    /**
     * 获取订单收货管理详细信息
     */
    @RequiresPermissions("pms:receipt:query")
    @GetMapping(value = "/{receiptId}")
    public AjaxResult getInfo(@PathVariable("receiptId") Long receiptId) {
        return success(orderReceiptService.selectOrderReceiptByReceiptId(receiptId));
    }

    /**
     * 获取订单收货管理详细信息
     */
    @RequiresPermissions("pms:receipt:query")
    @GetMapping(value = "/getInfo")
    public AjaxResult getInfoByOrderCodeAndDeliveryNo(@RequestParam("orderCode") String orderCode, @RequestParam("deliveryNo") String deliveryNo) {
        return success(receiptDetailsService.selectOrderExecutionDetailsByDeliveryNo(orderCode, deliveryNo));
    }

    /**
     * 新增订单收货管理
     */
    @RequiresPermissions("pms:receipt:add")
    @Log(title = "订单收货管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderReceipt orderReceipt) {
        return toAjax(orderReceiptService.insertOrderReceipt(orderReceipt));
    }

    /**
     * 修改订单收货管理
     */
    @RequiresPermissions("pms:receipt:edit")
    @Log(title = "订单收货管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderReceipt orderReceipt) {
        return toAjax(orderReceiptService.updateOrderReceipt(orderReceipt));
    }

    /**
     * 删除订单收货管理
     */
    @RequiresPermissions("pms:receipt:remove")
    @Log(title = "订单收货管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{receiptIds}")
    public AjaxResult remove(@PathVariable Long[] receiptIds) {
        return toAjax(orderReceiptService.deleteOrderReceiptByReceiptIds(receiptIds));
    }
}
