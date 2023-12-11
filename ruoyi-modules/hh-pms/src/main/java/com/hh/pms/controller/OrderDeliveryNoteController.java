package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.OrderDeliveryNote;
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
import com.hh.pms.service.IOrderDeliveryNoteService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 订单送货管理Controller
 * 
 * @author yt
 * @date 2023-12-08
 */
@RestController
@RequestMapping("/note")
public class OrderDeliveryNoteController extends BaseController
{
    @Autowired
    private IOrderDeliveryNoteService orderDeliveryNoteService;

    /**
     * 查询订单送货管理列表
     */
    @RequiresPermissions("pms:note:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderDeliveryNote orderDeliveryNote)
    {
        startPage();
        List<OrderDeliveryNote> list = orderDeliveryNoteService.selectOrderDeliveryNoteList(orderDeliveryNote);
        return getDataTable(list);
    }

    /**
     * 导出订单送货管理列表
     */
    @RequiresPermissions("pms:note:export")
    @Log(title = "订单送货管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderDeliveryNote orderDeliveryNote)
    {
        List<OrderDeliveryNote> list = orderDeliveryNoteService.selectOrderDeliveryNoteList(orderDeliveryNote);
        ExcelUtil<OrderDeliveryNote> util = new ExcelUtil<OrderDeliveryNote>(OrderDeliveryNote.class);
        util.exportExcel(response, list, "订单送货管理数据");
    }

    /**
     * 获取订单送货管理详细信息
     */
    @RequiresPermissions("pms:note:query")
    @GetMapping(value = "/{orderDeliveryNoteId}")
    public AjaxResult getInfo(@PathVariable("orderDeliveryNoteId") Long orderDeliveryNoteId)
    {
        return success(orderDeliveryNoteService.selectOrderDeliveryNoteByOrderDeliveryNoteId(orderDeliveryNoteId));
    }

    /**
     * 新增订单送货管理
     */
    @RequiresPermissions("pms:note:add")
    @Log(title = "订单送货管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderDeliveryNote orderDeliveryNote)
    {
        return toAjax(orderDeliveryNoteService.insertOrderDeliveryNote(orderDeliveryNote));
    }

    /**
     * 修改订单送货管理
     */
    @RequiresPermissions("pms:note:edit")
    @Log(title = "订单送货管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderDeliveryNote orderDeliveryNote)
    {
        return toAjax(orderDeliveryNoteService.updateOrderDeliveryNote(orderDeliveryNote));
    }

    /**
     * 删除订单送货管理
     */
    @RequiresPermissions("pms:note:remove")
    @Log(title = "订单送货管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderDeliveryNoteIds}")
    public AjaxResult remove(@PathVariable Long[] orderDeliveryNoteIds)
    {
        return toAjax(orderDeliveryNoteService.deleteOrderDeliveryNoteByOrderDeliveryNoteIds(orderDeliveryNoteIds));
    }
}
