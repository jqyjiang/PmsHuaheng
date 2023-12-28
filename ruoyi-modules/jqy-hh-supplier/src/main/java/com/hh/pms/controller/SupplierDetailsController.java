package com.hh.pms.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.SupplierDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
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

    //查询已分类供应商
    @RequiresPermissions("supplierpms:details:list")
    @RequestMapping(method = RequestMethod.GET,value = "/filter")
    public TableDataInfo listFilter(SupplierDetails supplierDetails)
    {
        startPage();
        List<SupplierDetails> list = supplierDetailsService.selectSupplierDetailsList(supplierDetails);
        List<SupplierDetails> filteredList = new ArrayList<>();
        for (SupplierDetails detail : list) {
            if (detail.getSdClass() != null && !detail.getSdClass().isEmpty()) {
                filteredList.add(detail);
            }
        }
        return getDataTable(filteredList);
    }
    @RequiresPermissions("supplierpms:details:list")
    @RequestMapping(method = RequestMethod.GET,value = "/lifeStage")
    public TableDataInfo lifeStage(SupplierDetails supplierDetails)
    {
        startPage();
        List<SupplierDetails> list = supplierDetailsService.selectSupplierLifeStage(supplierDetails);
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
        supplierDetails.setDataTatus("0");
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
        AjaxResult ajaxResult = toAjax(supplierDetailsService.updateSupplierDetails(supplierDetails));
        return ajaxResult;
    }

    /**
     * 修改供应商列表
     */
    @RequiresPermissions("supplierpms:details:edit")
    @Log(title = "供应商列表", businessType = BusinessType.UPDATE)
    @PutMapping("/updateExamine")
    public AjaxResult SupplierExamine(@RequestBody SupplierDetails supplierDetails)
    {
        AjaxResult ajaxResult = toAjax(supplierDetailsService.updateSupplierExamine(supplierDetails));
        return ajaxResult;
    }
    /**
     * 修改供应商列表
     */
    @RequiresPermissions("supplierpms:details:edit")
    @Log(title = "供应商列表", businessType = BusinessType.UPDATE)
    @PutMapping("/updatelifecycle")
    public AjaxResult Supplierlifecycle(@RequestBody SupplierDetails supplierDetails) throws Throwable {
        SupplierDetails supplierDetails1 = supplierDetailsService.selectSupplierDetailsBySdId(supplierDetails.getSdId());
        if (supplierDetails.getOperate() == 1){
            supplierDetails.setSlStatus(2L);
            primaryDetermine(supplierDetails1.getPriorityLevel(),supplierDetails.getPriorityLevel());
        }else{
            supplierDetails.setSlStatus(3L);
            determine(supplierDetails1.getPriorityLevel(),supplierDetails.getPriorityLevel());
        }
        AjaxResult ajaxResult = toAjax(supplierDetailsService.updateSupplierDetails(supplierDetails));
        return ajaxResult;
    }
    //升级判断
    private void primaryDetermine(Long primaryLevel,Long Level) throws Throwable {
        if(primaryLevel.equals(Level)){
            throw new Exception("目标类型不能与原类型相等");
        }
        if (primaryLevel < Level){
            throw new Exception("升级操作:目标类型不能小于原类型");
        }
    }
    //降级判断
    private void determine(Long primaryLevel,Long Level) throws Throwable {
        if(primaryLevel.equals(Level)){
            throw new Exception("目标类型不能与原类型相等");
        }
        if (primaryLevel > Level){
            throw new Exception("降级操作:目标类型不能大于原类型");
        }
    }
    /**
     * 批量删除供应商的供应商分类
     */
    @RequiresPermissions("supplierpms:details:edit")
    @Log(title = "供应商列表", businessType = BusinessType.UPDATE)
//    @RequestMapping(method = RequestMethod.GET,value = "/updateDetails/{sdId}")
    @DeleteMapping("/updateDetails/{sdId}")
    public AjaxResult updateDetails(@PathVariable("sdId") Long[] sdId)
    {
        AjaxResult ajaxResult = toAjax(supplierDetailsService.deleteSupplierClassBySdIds(sdId));
        return ajaxResult;
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

    /**
     * 查询注册好的供应商
     * (一般能做供应商操作的数据)
     */
    @RequiresPermissions("supplierpms:details:list")
    @RequestMapping(method = RequestMethod.GET,value = "/canSupplier")
    public TableDataInfo canSupplierlist()
    {
        List<SupplierDetails> list = supplierDetailsService.selectCanSupplier();
        return getDataTable(list);
    }

    /** 统计分析供应商分类*/
    @RequiresPermissions("supplierpms:details:list")
    @RequestMapping(method = RequestMethod.GET,value = "/listClass")
    public TableDataInfo list1()
    {
        List<SupplierDetails> list = supplierDetailsService.selectClass();
//        System.out.println(list);
        return getDataTable(list);
    }

    /** 统计分析供应商生命周期*/
    @RequiresPermissions("supplierpms:details:list")
    @RequestMapping(method = RequestMethod.GET,value = "/listLife")
    public TableDataInfo list2()
    {
        List<SupplierDetails> list = supplierDetailsService.selectLife();
        System.out.println(list);
        return getDataTable(list);
    }

}
