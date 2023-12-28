package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.SupplierLifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.SupplierLifecycleMapper;
import com.hh.pms.service.ISupplierLifecycleService;

/**
 * 生命周期维护Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@Service
public class SupplierLifecycleServiceImpl implements ISupplierLifecycleService 
{
    @Autowired
    private SupplierLifecycleMapper supplierLifecycleMapper;

    /**
     * 查询生命周期维护
     * 
     * @param lifecycleId 生命周期维护主键
     * @return 生命周期维护
     */
    @Override
    public SupplierLifecycle selectSupplierLifecycleByLifecycleId(Long lifecycleId)
    {
        return supplierLifecycleMapper.selectSupplierLifecycleByLifecycleId(lifecycleId);
    }

    /**
     * 查询生命周期维护列表
     * 
     * @param supplierLifecycle 生命周期维护
     * @return 生命周期维护
     */
    @Override
    public List<SupplierLifecycle> selectSupplierLifecycleList(SupplierLifecycle supplierLifecycle)
    {
        return supplierLifecycleMapper.selectSupplierLifecycleList(supplierLifecycle);
    }

    /**
     * 新增生命周期维护
     * 
     * @param supplierLifecycle 生命周期维护
     * @return 结果
     */
    @Override
    public int insertSupplierLifecycle(SupplierLifecycle supplierLifecycle)
    {
        return supplierLifecycleMapper.insertSupplierLifecycle(supplierLifecycle);
    }

    /**
     * 修改生命周期维护
     * 
     * @param supplierLifecycle 生命周期维护
     * @return 结果
     */
    @Override
    public int updateSupplierLifecycle(SupplierLifecycle supplierLifecycle)
    {
        return supplierLifecycleMapper.updateSupplierLifecycle(supplierLifecycle);
    }

    /**
     * 批量删除生命周期维护
     * 
     * @param lifecycleIds 需要删除的生命周期维护主键
     * @return 结果
     */
    @Override
    public int deleteSupplierLifecycleByLifecycleIds(Long[] lifecycleIds)
    {
        return supplierLifecycleMapper.deleteSupplierLifecycleByLifecycleIds(lifecycleIds);
    }

    /**
     * 删除生命周期维护信息
     * 
     * @param lifecycleId 生命周期维护主键
     * @return 结果
     */
    @Override
    public int deleteSupplierLifecycleByLifecycleId(Long lifecycleId)
    {
        return supplierLifecycleMapper.deleteSupplierLifecycleByLifecycleId(lifecycleId);
    }
}
