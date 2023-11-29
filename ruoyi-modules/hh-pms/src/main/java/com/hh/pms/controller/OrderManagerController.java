package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hh.pms.domain.SupplierDetails;
import com.hh.pms.mast.domain.Material;
import com.hh.pms.model.MaterialClient;
import com.hh.pms.model.SupplierClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.domain.OrderManager;
import com.hh.pms.service.IOrderManagerService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 采购订单管理Controller
 * 
 * @author ruoyi
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/manager")

public class OrderManagerController extends BaseController
{
    @Autowired
    private IOrderManagerService orderManagerService;

    @Autowired
    private  SupplierClient supplierClient;

    @Autowired
    private  MaterialClient materialClient;

//    @Autowired
//    public OrderManagerController(SupplierClient supplierClient) {
//        this.supplierClient = supplierClient;
//    }
//
//    @Autowired
//    public OrderManagerController(MaterialClient materialClient) {
//        this.materialClient = materialClient;
//    }

    /**
     * 查询供应商列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/listSupplier")
    public TableDataInfo listSupplier(SupplierDetails supplierDetails, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return supplierClient.list(supplierDetails,pageNum,pageSize);
    }

    @RequiresPermissions("mast:material:list")
    @RequestMapping(method = RequestMethod.GET,value = "/listMaterial")
    public TableDataInfo listMaterial(Material material,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        return materialClient.list(material,pageNum,pageSize);
    }

    /**
     * 查询采购订单管理列表
     */
    @RequiresPermissions("pms:manager:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderManager orderManager)
    {
        startPage();
        List<OrderManager> list = orderManagerService.selectOrderManagerList(orderManager);
        return getDataTable(list);
    }

    /**
     * 导出采购订单管理列表
     */
    @RequiresPermissions("pms:manager:export")
    @Log(title = "采购订单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderManager orderManager)
    {
        List<OrderManager> list = orderManagerService.selectOrderManagerList(orderManager);
        ExcelUtil<OrderManager> util = new ExcelUtil<OrderManager>(OrderManager.class);
        util.exportExcel(response, list, "采购订单管理数据");
    }

    /**
     * 获取采购订单管理详细信息
     */
    @RequiresPermissions("pms:manager:query")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(orderManagerService.selectOrderManagerByOrderId(orderId));
    }

    /**
     * 新增采购订单管理
     */
    @RequiresPermissions("pms:manager:add")
    @Log(title = "采购订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderManager orderManager)
    {
        return toAjax(orderManagerService.insertOrderManager(orderManager));
    }

    /**
     * 修改采购订单管理
     */
    @RequiresPermissions("pms:manager:edit")
    @Log(title = "采购订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderManager orderManager)
    {
        return toAjax(orderManagerService.updateOrderManager(orderManager));
    }

    /**
     * 删除采购订单管理
     */
    @RequiresPermissions("pms:manager:remove")
    @Log(title = "采购订单管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderManagerService.deleteOrderManagerByOrderIds(orderIds));
    }
}
