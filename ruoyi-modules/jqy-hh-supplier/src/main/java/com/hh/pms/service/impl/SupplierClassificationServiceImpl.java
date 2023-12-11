package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.SupplierClassification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.SupplierClassificationMapper;
import com.hh.pms.service.ISupplierClassificationService;

/**
 * 供应商分类定义Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
@Service
public class SupplierClassificationServiceImpl implements ISupplierClassificationService 
{
    @Autowired
    private SupplierClassificationMapper supplierClassificationMapper;

    /**
     * 查询供应商分类定义
     * 
     * @param classId 供应商分类定义主键
     * @return 供应商分类定义
     */
    @Override
    public SupplierClassification selectSupplierClassificationByClassId(Long classId)
    {
        return supplierClassificationMapper.selectSupplierClassificationByClassId(classId);
    }

    /**
     * 查询供应商分类定义列表
     * 
     * @param supplierClassification 供应商分类定义
     * @return 供应商分类定义
     */
    @Override
    public List<SupplierClassification> selectSupplierClassificationList(SupplierClassification supplierClassification)
    {
        return supplierClassificationMapper.selectSupplierClassificationList(supplierClassification);
    }

    /**
     * 新增供应商分类定义
     * 
     * @param supplierClassification 供应商分类定义
     * @return 结果
     */
    @Override
    public int insertSupplierClassification(SupplierClassification supplierClassification)
    {
        return supplierClassificationMapper.insertSupplierClassification(supplierClassification);
    }

    /**
     * 修改供应商分类定义
     * 
     * @param supplierClassification 供应商分类定义
     * @return 结果
     */
    @Override
    public int updateSupplierClassification(SupplierClassification supplierClassification)
    {
        return supplierClassificationMapper.updateSupplierClassification(supplierClassification);
    }

    /**
     * 批量删除供应商分类定义
     * 
     * @param classIds 需要删除的供应商分类定义主键
     * @return 结果
     */
    @Override
    public int deleteSupplierClassificationByClassIds(Long[] classIds)
    {
        return supplierClassificationMapper.deleteSupplierClassificationByClassIds(classIds);
    }

    /**
     * 删除供应商分类定义信息
     * 
     * @param classId 供应商分类定义主键
     * @return 结果
     */
    @Override
    public int deleteSupplierClassificationByClassId(Long classId)
    {
        return supplierClassificationMapper.deleteSupplierClassificationByClassId(classId);
    }
}
