package com.hh.pms.controller;

import com.hh.pms.domain.OrderMaterial;
import com.hh.pms.domain.SupplierDetails;
import com.hh.pms.mast.domain.Category;
import com.hh.pms.mast.domain.Currency;
import com.hh.pms.mast.domain.Material;
import com.hh.pms.mast.domain.TaxRate;
import com.hh.pms.model.MaterialClient;
import com.hh.pms.model.SupplierClient;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/OrderManager")
public class OrderManagerFeignController {
    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private MaterialClient materialClient;

    /**
     * 查询供应商列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/listSupplier")
    public TableDataInfo listSupplier(SupplierDetails supplierDetails, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return supplierClient.list(supplierDetails,pageNum,pageSize);
    }
    /**
     * 查询品类列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/listCategory")
    public TableDataInfo listCategory(Category category, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return materialClient.listCategory(category,pageNum,pageSize);
    }
    /**
     * 查询物料基础表
     * @param material
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/listMaterial")
    public TableDataInfo listMaterial(Material material, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        return materialClient.list(material,pageNum,pageSize);
    }

    /**
     * 查询币种定义表
     * @param currency
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/listCurrency")
    public TableDataInfo listCurrency(Currency currency, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return materialClient.list(currency, pageNum, pageSize);
    }
    /**
     * 查询税率表
     * @param taxRate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/listRate")
    public TableDataInfo listRate(TaxRate taxRate, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return materialClient.listRate(taxRate, pageNum, pageSize);
    }
}
