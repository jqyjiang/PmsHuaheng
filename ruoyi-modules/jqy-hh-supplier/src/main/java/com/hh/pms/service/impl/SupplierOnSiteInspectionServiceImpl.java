package com.hh.pms.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.SupplierMaterialList;
import com.ruoyi.system.api.domain.SupplierOnSiteInspection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.hh.pms.mapper.SupplierOnSiteInspectionMapper;
import com.hh.pms.service.ISupplierOnSiteInspectionService;

/**
 * 现场考察Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-15
 */
@Service
public class SupplierOnSiteInspectionServiceImpl implements ISupplierOnSiteInspectionService 
{
    @Autowired
    private SupplierOnSiteInspectionMapper supplierOnSiteInspectionMapper;

    /**
     * 查询现场考察
     * 
     * @param soiId 现场考察主键
     * @return 现场考察
     */
    @Override
    public SupplierOnSiteInspection selectSupplierOnSiteInspectionBySoiId(Long soiId)
    {
        return supplierOnSiteInspectionMapper.selectSupplierOnSiteInspectionBySoiId(soiId);
    }

    /**
     * 查询现场考察列表
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 现场考察
     */
    @Override
    public List<SupplierOnSiteInspection> selectSupplierOnSiteInspectionList(SupplierOnSiteInspection supplierOnSiteInspection)
    {
        return supplierOnSiteInspectionMapper.selectSupplierOnSiteInspectionList(supplierOnSiteInspection);
    }

    /**
     * 新增现场考察
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSupplierOnSiteInspection(SupplierOnSiteInspection supplierOnSiteInspection)
    {
        Calendar c   =   Calendar.getInstance();//可以用set()对每个时间域单独修改
        int   year   =   c.get(Calendar.YEAR);
        //month都需要+1才表示当前月份
        int   month   =   c.get(Calendar.MONTH);
        int   date   =   c.get(Calendar.DATE);
        int   hour_of_day   =   c.get(Calendar.HOUR_OF_DAY);
        int   minute   =   c.get(Calendar.MINUTE);
        String nowTime = year+""+ (month+1)+""+date+""+hour_of_day+""+minute;
        supplierOnSiteInspection.setSoiCode("XCKC"+nowTime+Math.round(Math.random()*10000));
        supplierOnSiteInspection.setExamine(2L);
        int rows = supplierOnSiteInspectionMapper.insertSupplierOnSiteInspection(supplierOnSiteInspection);
        insertSupplierMaterialList(supplierOnSiteInspection);
        return rows;
    }

    /**
     * 修改现场考察
     * 
     * @param supplierOnSiteInspection 现场考察
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSupplierOnSiteInspection(SupplierOnSiteInspection supplierOnSiteInspection)
    {

        supplierOnSiteInspectionMapper.deleteSupplierMaterialListBySoiId(supplierOnSiteInspection.getSoiId());
        insertSupplierMaterialList(supplierOnSiteInspection);
        return supplierOnSiteInspectionMapper.updateSupplierOnSiteInspection(supplierOnSiteInspection);
    }

    /**
     * 批量删除现场考察
     * 
     * @param soiIds 需要删除的现场考察主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSupplierOnSiteInspectionBySoiIds(Long[] soiIds)
    {
        supplierOnSiteInspectionMapper.deleteSupplierMaterialListBySoiIds(soiIds);
        return supplierOnSiteInspectionMapper.deleteSupplierOnSiteInspectionBySoiIds(soiIds);
    }

    /**
     * 删除现场考察信息
     * 
     * @param soiId 现场考察主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSupplierOnSiteInspectionBySoiId(Long soiId)
    {
        supplierOnSiteInspectionMapper.deleteSupplierMaterialListBySoiId(soiId);
        return supplierOnSiteInspectionMapper.deleteSupplierOnSiteInspectionBySoiId(soiId);
    }

    /**
     * 新增供应商物料信息
     * 
     * @param supplierOnSiteInspection 现场考察对象
     */
    public void insertSupplierMaterialList(SupplierOnSiteInspection supplierOnSiteInspection)
    {
        List<SupplierMaterialList> supplierMaterialListList = supplierOnSiteInspection.getSupplierMaterialListList();
        Long soiId = supplierOnSiteInspection.getSoiId();
        if (StringUtils.isNotNull(supplierMaterialListList))
        {
            List<SupplierMaterialList> list = new ArrayList<SupplierMaterialList>();
            for (SupplierMaterialList supplierMaterialList : supplierMaterialListList)
            {
                supplierMaterialList.setSoiId(soiId);
                list.add(supplierMaterialList);
            }
            if (list.size() > 0)
            {
                supplierOnSiteInspectionMapper.batchSupplierMaterialList(list);
            }
        }
    }
}
