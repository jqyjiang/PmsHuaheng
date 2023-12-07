package com.hh.pms.mast.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.hh.pms.mast.domain.Company;
import com.hh.pms.mast.service.ICompanyService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 公司表Controller
 *
 * @author ruoyi
 * @date 2023-12-05
 */
@RestController
@RequestMapping("/companies")
public class CompanyController extends BaseController
{
    @Autowired
    private ICompanyService companiesService;

    /**
     * 查询公司表列表
     */
    @RequiresPermissions("mast:companies:list")
    @GetMapping("/list")
    public TableDataInfo list(Company companies)
    {
        startPage();
        List<Company> list = companiesService.selectCompaniesList(companies);
        return getDataTable(list);
    }

    /**
     * 导出公司表列表
     */
    @RequiresPermissions("mast:companies:export")
    @Log(title = "公司表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Company companies)
    {
        List<Company> list = companiesService.selectCompaniesList(companies);
        ExcelUtil<Company> util = new ExcelUtil<Company>(Company.class);
        util.exportExcel(response, list, "公司表数据");
    }

    /**
     * 获取公司表详细信息
     */
    @RequiresPermissions("mast:companies:query")
    @GetMapping(value = "/{companiesId}")
    public AjaxResult getInfo(@PathVariable("companiesId") Long companiesId)
    {
        return success(companiesService.selectCompaniesByCompaniesId(companiesId));
    }

    /**
     * 新增公司表
     */
    @RequiresPermissions("mast:companies:add")
    @Log(title = "公司表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Company companies)
    {
        return toAjax(companiesService.insertCompanies(companies));
    }

    /**
     * 修改公司表
     */
    @RequiresPermissions("mast:companies:edit")
    @Log(title = "公司表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Company companies)
    {
        return toAjax(companiesService.updateCompanies(companies));
    }

    /**
     * 删除公司表
     */
    @RequiresPermissions("mast:companies:remove")
    @Log(title = "公司表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{companiesIds}")
    public AjaxResult remove(@PathVariable Long[] companiesIds)
    {
        return toAjax(companiesService.deleteCompaniesByCompaniesIds(companiesIds));
    }
}
