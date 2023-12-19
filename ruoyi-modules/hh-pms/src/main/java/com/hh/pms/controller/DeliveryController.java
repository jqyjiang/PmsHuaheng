package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.Delivery;
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

import com.hh.pms.service.IDeliveryService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 产品明细Controller
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController extends BaseController
{
    @Autowired
    private IDeliveryService deliveryService;

    /**
     * 查询产品明细列表
     */
    @RequiresPermissions("pms:delivery:list")
    @GetMapping("/list")
    public TableDataInfo list(Delivery delivery)
    {
        startPage();
        List<Delivery> list = deliveryService.selectDeliveryList(delivery);
        return getDataTable(list);
    }

    /**
     * 导出产品明细列表
     */
    @RequiresPermissions("pms:delivery:export")
    @Log(title = "产品明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Delivery delivery)
    {
        List<Delivery> list = deliveryService.selectDeliveryList(delivery);
        ExcelUtil<Delivery> util = new ExcelUtil<Delivery>(Delivery.class);
        util.exportExcel(response, list, "产品明细数据");
    }

    /**
     * 获取产品明细详细信息
     */
    @RequiresPermissions("pms:delivery:query")
    @GetMapping(value = "/{deliveryId}")
    public AjaxResult getInfo(@PathVariable("deliveryId") Long deliveryId)
    {
        return success(deliveryService.selectDeliveryByDeliveryId(deliveryId));
    }

    /**
     * 新增产品明细
     */
    @RequiresPermissions("pms:delivery:add")
    @Log(title = "产品明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Delivery delivery)
    {
        return toAjax(deliveryService.insertDelivery(delivery));
    }

    /**
     * 修改产品明细
     */
    @RequiresPermissions("pms:delivery:edit")
    @Log(title = "产品明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Delivery delivery)
    {
        return toAjax(deliveryService.updateDelivery(delivery));
    }

    /**
     * 删除产品明细
     */
    @RequiresPermissions("pms:delivery:remove")
    @Log(title = "产品明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deliveryIds}")
    public AjaxResult remove(@PathVariable Long[] deliveryIds)
    {
        return toAjax(deliveryService.deleteDeliveryByDeliveryIds(deliveryIds));
    }
}
