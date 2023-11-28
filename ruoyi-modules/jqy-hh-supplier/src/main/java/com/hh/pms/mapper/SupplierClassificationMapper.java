package com.hh.pms.mapper;

import java.util.List;
import com.hh.pms.domain.SupplierClassification;

/**
 * 供应商分类定义Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-28
 */
public interface SupplierClassificationMapper 
{
    /**
     * 查询供应商分类定义
     * 
     * @param classId 供应商分类定义主键
     * @return 供应商分类定义
     */
    public SupplierClassification selectSupplierClassificationByClassId(Long classId);

    /**
     * 查询供应商分类定义列表
     * 
     * @param supplierClassification 供应商分类定义
     * @return 供应商分类定义集合
     */
    public List<SupplierClassification> selectSupplierClassificationList(SupplierClassification supplierClassification);

    /**
     * 新增供应商分类定义
     * 
     * @param supplierClassification 供应商分类定义
     * @return 结果
     */
    public int insertSupplierClassification(SupplierClassification supplierClassification);

    /**
     * 修改供应商分类定义
     * 
     * @param supplierClassification 供应商分类定义
     * @return 结果
     */
    public int updateSupplierClassification(SupplierClassification supplierClassification);

    /**
     * 删除供应商分类定义
     * 
     * @param classId 供应商分类定义主键
     * @return 结果
     */
    public int deleteSupplierClassificationByClassId(Long classId);

    /**
     * 批量删除供应商分类定义
     * 
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierClassificationByClassIds(Long[] classIds);
}
