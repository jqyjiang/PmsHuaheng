package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.SupplierDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.SupplierDetailsMapper;
import com.hh.pms.service.ISupplierDetailsService;

/**
 * 供应商列表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-24
 */
@Service
public class SupplierDetailsServiceImpl implements ISupplierDetailsService
{
    @Autowired
    private SupplierDetailsMapper supplierDetailsMapper;

    /**
     * 查询供应商列表
     *
     * @param sdId 供应商列表主键
     * @return 供应商列表
     */
    @Override
    public SupplierDetails selectSupplierDetailsBySdId(Long sdId)
    {
        return supplierDetailsMapper.selectSupplierDetailsBySdId(sdId);
    }

    /**
     * 查询供应商列表列表
     *
     * @param supplierDetails 供应商列表
     * @return 供应商列表
     */
    @Override
    public List<SupplierDetails> selectSupplierDetailsList(SupplierDetails supplierDetails)
    {
        return supplierDetailsMapper.selectSupplierDetailsList(supplierDetails);
    }
    /**
     * 查询供应商审核过了的列表
     * @param supplierDetails
     * @return
     */
    @Override
    public List<SupplierDetails> selectSupplierLifeStage(SupplierDetails supplierDetails) {
        return supplierDetailsMapper.selectSupplierLifeStage(supplierDetails);
    }

    /**
     * 新增供应商列表
     *
     * @param supplierDetails 供应商列表
     * @return 结果
     */
    @Override
    public int insertSupplierDetails(SupplierDetails supplierDetails)
    {
        return supplierDetailsMapper.insertSupplierDetails(supplierDetails);
    }

    /**
     * 修改供应商列表
     *
     * @param supplierDetails 供应商列表
     * @return 结果
     */
    @Override
    public int updateSupplierDetails(SupplierDetails supplierDetails)
    {
        return supplierDetailsMapper.updateSupplierDetails(supplierDetails);
    }

    /**
     * 批量删除供应商列表
     *
     * @param sdIds 需要删除的供应商列表主键
     * @return 结果
     */
    @Override
    public int deleteSupplierDetailsBySdIds(Long[] sdIds)
    {
        return supplierDetailsMapper.deleteSupplierDetailsBySdIds(sdIds);
    }

    /**
     * 删除供应商列表信息
     *
     * @param sdId 供应商列表主键
     * @return 结果
     */
    @Override
    public int deleteSupplierDetailsBySdId(Long sdId)
    {
        return supplierDetailsMapper.deleteSupplierDetailsBySdId(sdId);
    }

    //删除供应商类别
    @Override
    public int deleteSupplierClassBySdIds(Long[] sdIds) {
        return supplierDetailsMapper.deleteSupplierClassBySdIds(sdIds);
    }
    //查询注册好的供应商(一般能做供应商操作的数据)
    @Override
    public List<SupplierDetails> selectCanSupplier() {
        return supplierDetailsMapper.selectCanSupplier();
    }
    /**
     * 审核供应商生命周期
     */
    @Override
    public int updateSupplierExamine(SupplierDetails supplierDetails) {
        return supplierDetailsMapper.updateSupplierExamine(supplierDetails);
    }

    //统计分析供应商分类
    @Override
    public List<SupplierDetails> selectClass() {
        return supplierDetailsMapper.selectClass();
    }

    @Override
    public List<SupplierDetails> selectLife() {
        return supplierDetailsMapper.selectLife();
    }
}
