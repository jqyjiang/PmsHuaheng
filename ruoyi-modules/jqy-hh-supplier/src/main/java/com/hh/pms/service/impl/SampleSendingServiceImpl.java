package com.hh.pms.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hh.pms.mapper.SupplierMaterialListMapper;
import com.ruoyi.system.api.domain.SampleSending;
import com.ruoyi.system.api.domain.SupplierMaterialList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.hh.pms.mapper.SampleSendingMapper;
import com.hh.pms.service.ISampleSendingService;

/**
 * 送样Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
@Service
public class SampleSendingServiceImpl implements ISampleSendingService 
{
    @Autowired
    private SampleSendingMapper sampleSendingMapper;

    @Autowired
    private SupplierMaterialListMapper supplierMaterialListMapper;
    /**
     * 查询送样
     * 
     * @param sampleId 送样主键
     * @return 送样
     */
    @Override
    public SampleSending selectSampleSendingBySampleId(Long sampleId)
    {
        SampleSending sampleSending = sampleSendingMapper.selectSampleSendingBySampleId(sampleId);
        List<SupplierMaterialList> supplierMaterialLists = supplierMaterialListMapper.selectSupplierMaterialListBysampleId(sampleId);
        sampleSending.setSupplierMaterialListList(supplierMaterialLists);
        return sampleSending;
    }

    /**
     * 查询送样列表
     * 
     * @param sampleSending 送样
     * @return 送样
     */
    @Override
    public List<SampleSending> selectSampleSendingList(SampleSending sampleSending)
    {
        return sampleSendingMapper.selectSampleSendingList(sampleSending);
    }

    /**
     * 新增送样
     * 
     * @param sampleSending 送样
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSampleSending(SampleSending sampleSending)
    {
        sampleSending.setState(2L);
        Calendar c   =   Calendar.getInstance();//可以用set()对每个时间域单独修改
        int   year   =   c.get(Calendar.YEAR);
        //month都需要+1才表示当前月份
        int   month   =   c.get(Calendar.MONTH);
        int   date   =   c.get(Calendar.DATE);
        int   hour_of_day   =   c.get(Calendar.HOUR_OF_DAY);
        int   minute   =   c.get(Calendar.MINUTE);
        String nowTime = year+""+ (month+1)+""+date+""+hour_of_day+""+minute;
        sampleSending.setSampleCode("SYB"+nowTime+Math.round(Math.random()*10000));
        sampleSending.setReleaseTime(new Date());
        int rows = sampleSendingMapper.insertSampleSending(sampleSending);
        insertSupplierMaterialList(sampleSending);
        return rows;
    }

    /**
     * 修改送样
     * 
     * @param sampleSending 送样
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSampleSending(SampleSending sampleSending)
    {
        sampleSending.setReleaseTime(new Date());
        sampleSendingMapper.deleteSupplierMaterialListBySampleId(sampleSending.getSampleId());
        insertSupplierMaterialList(sampleSending);
        return sampleSendingMapper.updateSampleSending(sampleSending);
    }

    /**
     * 修改送样状态
     *
     * @param sampleSending 送样
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSampleSendingState(SampleSending sampleSending)
    {
        sampleSending.setReleaseTime(new Date());
        return sampleSendingMapper.updateSampleSending(sampleSending);
    }

    /**
     * 批量删除送样
     * 
     * @param sampleIds 需要删除的送样主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSampleSendingBySampleIds(Long[] sampleIds)
    {
        sampleSendingMapper.deleteSupplierMaterialListBySampleIds(sampleIds);
        return sampleSendingMapper.deleteSampleSendingBySampleIds(sampleIds);
    }

    /**
     * 删除送样信息
     * 
     * @param sampleId 送样主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSampleSendingBySampleId(Long sampleId)
    {
        sampleSendingMapper.deleteSupplierMaterialListBySampleId(sampleId);
        return sampleSendingMapper.deleteSampleSendingBySampleId(sampleId);
    }

    /**
     * 新增供应商物料信息
     * 
     * @param sampleSending 送样对象
     */
    public void insertSupplierMaterialList(SampleSending sampleSending)
    {
        List<SupplierMaterialList> supplierMaterialListList = sampleSending.getSupplierMaterialListList();
        Long sampleId = sampleSending.getSampleId();
        if (StringUtils.isNotNull(supplierMaterialListList))
        {
            List<SupplierMaterialList> list = new ArrayList<SupplierMaterialList>();
            for (SupplierMaterialList supplierMaterialList : supplierMaterialListList)
            {
                supplierMaterialList.setSampleId(sampleId);
                list.add(supplierMaterialList);
            }
            if (list.size() > 0)
            {
                sampleSendingMapper.batchSupplierMaterialList(list);
            }
        }
    }
}
