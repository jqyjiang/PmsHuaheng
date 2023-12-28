package com.hh.pms.service;

import java.util.List;
import com.ruoyi.system.api.domain.SupplierLifecycle;

/**
 * 生命周期维护Service接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface ISupplierLifecycleService 
{
    /**
     * 查询生命周期维护
     * 
     * @param lifecycleId 生命周期维护主键
     * @return 生命周期维护
     */
    public SupplierLifecycle selectSupplierLifecycleByLifecycleId(Long lifecycleId);

    /**
     * 查询生命周期维护列表
     * 
     * @param supplierLifecycle 生命周期维护
     * @return 生命周期维护集合
     */
    public List<SupplierLifecycle> selectSupplierLifecycleList(SupplierLifecycle supplierLifecycle);

    /**
     * 新增生命周期维护
     * 
     * @param supplierLifecycle 生命周期维护
     * @return 结果
     */
    public int insertSupplierLifecycle(SupplierLifecycle supplierLifecycle);

    /**
     * 修改生命周期维护
     * 
     * @param supplierLifecycle 生命周期维护
     * @return 结果
     */
    public int updateSupplierLifecycle(SupplierLifecycle supplierLifecycle);

    /**
     * 批量删除生命周期维护
     * 
     * @param lifecycleIds 需要删除的生命周期维护主键集合
     * @return 结果
     */
    public int deleteSupplierLifecycleByLifecycleIds(Long[] lifecycleIds);

    /**
     * 删除生命周期维护信息
     * 
     * @param lifecycleId 生命周期维护主键
     * @return 结果
     */
    public int deleteSupplierLifecycleByLifecycleId(Long lifecycleId);
}
