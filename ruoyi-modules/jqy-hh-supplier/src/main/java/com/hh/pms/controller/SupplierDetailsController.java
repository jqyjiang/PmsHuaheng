package com.hh.pms.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.hh.pms.domain.SupplierDetails;
import com.hh.pms.service.ISupplierDetailsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 供应商列表Controller
 * 
 * @author ruoyi
 * @date 2023-11-24
 */
@RestController
@RequestMapping("/details")
public class SupplierDetailsController extends BaseController
{
    @Autowired
    private ISupplierDetailsService supplierDetailsService;

    /**
     * 查询供应商列表列表
     */
    @RequiresPermissions("supplierpms:details:list")
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public TableDataInfo list(SupplierDetails supplierDetails)
    {
        startPage();
        List<SupplierDetails> list = supplierDetailsService.selectSupplierDetailsList(supplierDetails);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表列表
     */
    @RequiresPermissions("supplierpms:details:export")
    @Log(title = "供应商列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierDetails supplierDetails)
    {
        List<SupplierDetails> list = supplierDetailsService.selectSupplierDetailsList(supplierDetails);
        ExcelUtil<SupplierDetails> util = new ExcelUtil<SupplierDetails>(SupplierDetails.class);
        util.exportExcel(response, list, "供应商列表数据");
    }

    /**
     * 获取供应商列表详细信息
     */
    @RequiresPermissions("supplierpms:details:query")
    @GetMapping(value = "/{sdId}")
    public AjaxResult getInfo(@PathVariable("sdId") Long sdId)
    {
        return success(supplierDetailsService.selectSupplierDetailsBySdId(sdId));
    }

    /**
     * 新增供应商列表
     */
    @RequiresPermissions("supplierpms:details:add")
    @Log(title = "供应商列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierDetails supplierDetails)
    {
        Calendar c   =   Calendar.getInstance();//可以用set()对每个时间域单独修改
        int   year   =   c.get(Calendar.YEAR);
        //month都需要+1才表示当前月份
        int   month   =   c.get(Calendar.MONTH);
        int   date   =   c.get(Calendar.DATE);
        int   hour_of_day   =   c.get(Calendar.HOUR_OF_DAY);
        int   minute   =   c.get(Calendar.MINUTE);
        String nowTime = year+""+ (month+1)+""+date+""+hour_of_day+""+minute;
        //System.out.println(nowTime);
        //System.out.println("GYS"+nowTime+Math.round(Math.random()*10000));
        Date day = new Date();
        //System.out.println(day);
        supplierDetails.setSdTime(day);
        supplierDetails.setSdStatus("0");
        supplierDetails.setSdCode("GYS"+nowTime+Math.round(Math.random()*10000));
        return toAjax(supplierDetailsService.insertSupplierDetails(supplierDetails));
    }

    /**
     * 修改供应商列表
     */
    @RequiresPermissions("supplierpms:details:edit")
    @Log(title = "供应商列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierDetails supplierDetails)
    {
        return toAjax(supplierDetailsService.updateSupplierDetails(supplierDetails));
    }

    /**
     * 删除供应商列表
     */
    @RequiresPermissions("supplierpms:details:remove")
    @Log(title = "供应商列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sdIds}")
    public AjaxResult remove(@PathVariable Long[] sdIds)
    {
        return toAjax(supplierDetailsService.deleteSupplierDetailsBySdIds(sdIds));
    }
}
