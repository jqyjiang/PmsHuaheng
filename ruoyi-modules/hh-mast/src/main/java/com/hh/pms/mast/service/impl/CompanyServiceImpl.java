package com.hh.pms.mast.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.CompanyMapper;
import com.hh.pms.mast.service.ICompanyService;

/**
 * 公司表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-05
 */
@Service
public class CompanyServiceImpl implements ICompanyService
{
    @Autowired
    private CompanyMapper companiesMapper;

    /**
     * 查询公司表
     *
     * @param companiesId 公司表主键
     * @return 公司表
     */
    @Override
    public Company selectCompaniesByCompaniesId(Long companiesId)
    {
        return companiesMapper.selectCompaniesByCompaniesId(companiesId);
    }

    /**
     * 查询公司表列表
     *
     * @param companies 公司表
     * @return 公司表
     */
    @Override
    public List<Company> selectCompaniesList(Company companies)
    {
        return companiesMapper.selectCompaniesList(companies);
    }

    /**
     * 新增公司表
     *
     * @param companies 公司表
     * @return 结果
     */
    @Override
    public int insertCompanies(Company companies)
    {
        return companiesMapper.insertCompanies(companies);
    }

    /**
     * 修改公司表
     *
     * @param companies 公司表
     * @return 结果
     */
    @Override
    public int updateCompanies(Company companies)
    {
        return companiesMapper.updateCompanies(companies);
    }

    /**
     * 批量删除公司表
     *
     * @param companiesIds 需要删除的公司表主键
     * @return 结果
     */
    @Override
    public int deleteCompaniesByCompaniesIds(Long[] companiesIds)
    {
        return companiesMapper.deleteCompaniesByCompaniesIds(companiesIds);
    }

    /**
     * 删除公司表信息
     *
     * @param companiesId 公司表主键
     * @return 结果
     */
    @Override
    public int deleteCompaniesByCompaniesId(Long companiesId)
    {
        return companiesMapper.deleteCompaniesByCompaniesId(companiesId);
    }
}
