package com.hh.pms.service;

import java.util.List;
import com.ruoyi.system.api.domain.SampleSending;

/**
 * 送样Service接口
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
public interface ISampleSendingService 
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
     * 批量删除送样
     * 
     * @param sampleIds 需要删除的送样主键集合
     * @return 结果
     */
    public int deleteSampleSendingBySampleIds(Long[] sampleIds);

    /**
     * 删除送样信息
     * 
     * @param sampleId 送样主键
     * @return 结果
     */
    public int deleteSampleSendingBySampleId(Long sampleId);
    /**
     * 修改送样状态
     *
     * @param sampleSending 送样
     * @return 结果
     */
    public int updateSampleSendingState(SampleSending sampleSending);
}
