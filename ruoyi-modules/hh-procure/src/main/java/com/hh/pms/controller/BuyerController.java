package com.hh.pms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.Buyer;
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
import com.hh.pms.service.IBuyerService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 采购员Controller
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@RestController
@RequestMapping("/buyer")
public class BuyerController extends BaseController
{
    @Autowired
    private IBuyerService buyerService;

    /**
     * 查询采购员列表
     */
    @RequiresPermissions("procure:buyer:list")
    @GetMapping("/list")
    public TableDataInfo list(Buyer buyer)
    {
        startPage();
        List<Buyer> list = buyerService.selectBuyerList(buyer);
        return getDataTable(list);
    }

    /**
     * 导出采购员列表
     */
    @RequiresPermissions("procure:buyer:export")
    @Log(title = "采购员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Buyer buyer)
    {
        List<Buyer> list = buyerService.selectBuyerList(buyer);
        ExcelUtil<Buyer> util = new ExcelUtil<Buyer>(Buyer.class);
        util.exportExcel(response, list, "采购员数据");
    }

    /**
     * 获取采购员详细信息
     */
    @RequiresPermissions("procure:buyer:query")
    @GetMapping(value = "/{purchaserId}")
    public AjaxResult getInfo(@PathVariable("purchaserId") Long purchaserId)
    {
        return success(buyerService.selectBuyerByPurchaserId(purchaserId));
    }

    /**
     * 新增采购员
     */
    @RequiresPermissions("procure:buyer:add")
    @Log(title = "采购员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Buyer buyer)
    {
        return toAjax(buyerService.insertBuyer(buyer));
    }

    /**
     * 修改采购员
     */
    @RequiresPermissions("procure:buyer:edit")
    @Log(title = "采购员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Buyer buyer)
    {
        return toAjax(buyerService.updateBuyer(buyer));
    }

    /**
     * 删除采购员
     */
    @RequiresPermissions("procure:buyer:remove")
    @Log(title = "采购员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{purchaserIds}")
    public AjaxResult remove(@PathVariable Long[] purchaserIds)
    {
        return toAjax(buyerService.deleteBuyerByPurchaserIds(purchaserIds));
    }
}
