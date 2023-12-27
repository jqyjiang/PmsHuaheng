package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.InboundNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.service.IInboundNoteService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 订单入库管理Controller
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
@RestController
@RequestMapping("/stockIn")
public class InboundNoteController extends BaseController
{
    @Autowired
    private IInboundNoteService inboundNoteService;

    /**
     * 查询订单入库管理列表
     */
    @RequiresPermissions("pms:stockIn:list")
    @GetMapping("/list")
    public TableDataInfo list(InboundNote inboundNote)
    {
        startPage();
        List<InboundNote> list = inboundNoteService.selectInboundNoteList(inboundNote);
        return getDataTable(list);
    }

    /**
     * 导出订单入库管理列表
     */
    @RequiresPermissions("pms:stockIn:export")
    @Log(title = "订单入库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InboundNote inboundNote)
    {
        List<InboundNote> list = inboundNoteService.selectInboundNoteList(inboundNote);
        ExcelUtil<InboundNote> util = new ExcelUtil<InboundNote>(InboundNote.class);
        util.exportExcel(response, list, "订单入库管理数据");
    }

    /**
     * 获取订单入库管理详细信息
     */
    @RequiresPermissions("pms:stockIn:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(inboundNoteService.selectInboundNoteById(id));
    }
    /**
     * 获取订单入库管理详细信息
     */
    @RequiresPermissions("pms:stockIn:query")
    @GetMapping(value = "/getInboundMaterial")
    public AjaxResult getInfoMaterial(@RequestParam("orderCode") String orderCode,@RequestParam("orName")String orName)
    {
        return success(inboundNoteService.selectInboundNoteByOrderCode(orderCode,orName));
    }

    /**
     * 新增订单入库管理
     */
    @RequiresPermissions("pms:stockIn:add")
    @Log(title = "订单入库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InboundNote inboundNote)
    {
        return toAjax(inboundNoteService.insertInboundNote(inboundNote));
    }

    /**
     * 修改订单入库管理
     */
    @RequiresPermissions("pms:stockIn:edit")
    @Log(title = "订单入库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InboundNote inboundNote)
    {
        return toAjax(inboundNoteService.updateInboundNote(inboundNote));
    }

    /**
     * 删除订单入库管理
     */
    @RequiresPermissions("pms:stockIn:remove")
    @Log(title = "订单入库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(inboundNoteService.deleteInboundNoteByIds(ids));
    }
}
