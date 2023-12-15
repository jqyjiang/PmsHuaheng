package com.hh.pms.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ContractManagement;
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
import com.hh.pms.service.IContractManagementService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 采购合同管理Controller
 *
 * @author ruoyi
 * @date 2023-12-14
 */
@RestController
@RequestMapping("/management")
public class ContractManagementController extends BaseController
{
    @Autowired
    private IContractManagementService contractManagementService;

    /**
     * 查询采购合同管理列表
     */
    @RequiresPermissions("procure:management:list")
    @GetMapping("/list")
    public TableDataInfo list(ContractManagement contractManagement)
    {
        startPage();
        List<ContractManagement> list = contractManagementService.selectContractManagementList(contractManagement);
        return getDataTable(list);
    }

    /**
     * 导出采购合同管理列表
     */
    @RequiresPermissions("procure:management:export")
    @Log(title = "采购合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContractManagement contractManagement)
    {
        List<ContractManagement> list = contractManagementService.selectContractManagementList(contractManagement);
        ExcelUtil<ContractManagement> util = new ExcelUtil<ContractManagement>(ContractManagement.class);
        util.exportExcel(response, list, "采购合同管理数据");
    }

    /**
     * 获取采购合同管理详细信息
     */
    @RequiresPermissions("procure:management:query")
    @GetMapping(value = "/{contractManagementId}")
    public AjaxResult getInfo(@PathVariable("contractManagementId") Long contractManagementId)
    {
        return success(contractManagementService.selectContractManagementByContractManagementId(contractManagementId));
    }

    /**
     * 新增采购合同管理
     */
    @RequiresPermissions("procure:management:add")
    @Log(title = "采购合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractManagement contractManagement)
    {
        return toAjax(contractManagementService.insertContractManagement(contractManagement));
    }

    /**
     * 修改采购合同管理
     */
    @RequiresPermissions("procure:management:edit")
    @Log(title = "采购合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractManagement contractManagement)
    {
        return toAjax(contractManagementService.updateContractManagement(contractManagement));
    }

    /**
     * 删除采购合同管理
     */
    @RequiresPermissions("procure:management:remove")
    @Log(title = "采购合同管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contractManagementIds}")
    public AjaxResult remove(@PathVariable Long[] contractManagementIds)
    {
        return toAjax(contractManagementService.deleteContractManagementByContractManagementIds(contractManagementIds));
    }
}
