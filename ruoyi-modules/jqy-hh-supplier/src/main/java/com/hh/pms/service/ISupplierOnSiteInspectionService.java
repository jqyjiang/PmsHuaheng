package com.hh.pms.service;

import java.util.List;
import com.ruoyi.system.api.domain.SupplierOnSiteInspection;

/**
 * 现场考察Service接口
 * 
 * @author ruoyi
 * @date 2023-12-15
 */
public interface ISupplierOnSiteInspectionService 
{
    /**
     * 查询现场考察
     * 
     * @param soiId 现场考察主键
     * @return 现场考察
     */
    public SupplierOnSiteInspection selectSupplierOnSiteInspectionBySoiId(Long soiId);

    /**
     * 查询现场考察列表
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 现场考察集合
     */
    public List<SupplierOnSiteInspection> selectSupplierOnSiteInspectionList(SupplierOnSiteInspection supplierOnSiteInspection);

    /**
     * 新增现场考察
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 结果
     */
    public int insertSupplierOnSiteInspection(SupplierOnSiteInspection supplierOnSiteInspection);

    /**
     * 修改现场考察
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 结果
     */
    public int updateSupplierOnSiteInspection(SupplierOnSiteInspection supplierOnSiteInspection);

    /**
     * 批量删除现场考察
     * 
     * @param soiIds 需要删除的现场考察主键集合
     * @return 结果
     */
    public int deleteSupplierOnSiteInspectionBySoiIds(Long[] soiIds);

    /**
     * 删除现场考察信息
     * 
     * @param soiId 现场考察主键
     * @return 结果
     */
    public int deleteSupplierOnSiteInspectionBySoiId(Long soiId);
}
