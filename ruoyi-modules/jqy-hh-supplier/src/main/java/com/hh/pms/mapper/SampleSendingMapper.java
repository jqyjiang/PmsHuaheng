package com.hh.pms.mapper;

import java.util.List;
import com.ruoyi.system.api.domain.SampleSending;
import com.ruoyi.system.api.domain.SupplierMaterialList;

/**
 * 送样Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
public interface SampleSendingMapper 
{
    /**
     * 查询送样
     * 
     * @param sampleId 送样主键
     * @return 送样
     */
    public SampleSending selectSampleSendingBySampleId(Long sampleId);

    /**
     * 查询送样列表
     * 
     * @param sampleSending 送样
     * @return 送样集合
     */
    public List<SampleSending> selectSampleSendingList(SampleSending sampleSending);

    /**
     * 新增送样
     * 
     * @param sampleSending 送样
     * @return 结果
     */
    public int insertSampleSending(SampleSending sampleSending);

    /**
     * 修改送样
     * 
     * @param sampleSending 送样
     * @return 结果
     */
    public int updateSampleSending(SampleSending sampleSending);

    /**
     * 删除送样
     * 
     * @param sampleId 送样主键
     * @return 结果
     */
    public int deleteSampleSendingBySampleId(Long sampleId);

    /**
     * 批量删除送样
     * 
     * @param sampleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSampleSendingBySampleIds(Long[] sampleIds);

    /**
     * 批量删除供应商物料
     * 
     * @param sampleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierMaterialListBySampleIds(Long[] sampleIds);
    
    /**
     * 批量新增供应商物料
     * 
     * @param supplierMaterialListList 供应商物料列表
     * @return 结果
     */
    public int batchSupplierMaterialList(List<SupplierMaterialList> supplierMaterialListList);
    

    /**
     * 通过送样主键删除供应商物料信息
     * 
     * @param sampleId 送样ID
     * @return 结果
     */
    public int deleteSupplierMaterialListBySampleId(Long sampleId);

    /**
     * 修改送样状态
     *
     * @param sampleSending 送样
     * @return 结果
     */
    public int updateSampleSendingState(SampleSending sampleSending);
}
