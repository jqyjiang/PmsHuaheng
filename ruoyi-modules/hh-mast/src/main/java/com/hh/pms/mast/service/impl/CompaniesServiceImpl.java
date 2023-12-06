package com.hh.pms.mast.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.CompaniesMapper;
import com.hh.pms.mast.domain.Companies;
import com.hh.pms.mast.service.ICompaniesService;

/**
 * 公司表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-05
 */
@Service
public class CompaniesServiceImpl implements ICompaniesService 
{
    @Autowired
    private CompaniesMapper companiesMapper;

    /**
     * 查询公司表
     * 
     * @param companiesId 公司表主键
     * @return 公司表
     */
    @Override
    public Companies selectCompaniesByCompaniesId(Long companiesId)
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
    public List<Companies> selectCompaniesList(Companies companies)
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
    public int insertCompanies(Companies companies)
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
    public int updateCompanies(Companies companies)
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
