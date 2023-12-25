package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.QualityTaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.QualityTaskListMapper;

import com.hh.pms.service.IQualityTaskListService;

/**
 * 到货质检Service业务层处理
 * 
 * @author yangtao
 * @date 2023-12-21
 */
@Service
public class QualityTaskListServiceImpl implements IQualityTaskListService 
{
    @Autowired
    private QualityTaskListMapper qualityTaskListMapper;

    /**
     * 查询到货质检
     * 
     * @param qualityTaskListId 到货质检主键
     * @return 到货质检
     */
    @Override
    public QualityTaskList selectQualityTaskListByQualityTaskListId(Long qualityTaskListId)
    {
        return qualityTaskListMapper.selectQualityTaskListByQualityTaskListId(qualityTaskListId);
    }

    /**
     * 查询到货质检列表
     * 
     * @param qualityTaskList 到货质检
     * @return 到货质检
     */
    @Override
    public List<QualityTaskList> selectQualityTaskListList(QualityTaskList qualityTaskList)
    {
        return qualityTaskListMapper.selectQualityTaskListList(qualityTaskList);
    }

    /**
     * 新增到货质检
     * 
     * @param qualityTaskList 到货质检
     * @return 结果
     */
    @Override
    public int insertQualityTaskList(QualityTaskList qualityTaskList)
    {
        return qualityTaskListMapper.insertQualityTaskList(qualityTaskList);
    }

    /**
     * 修改到货质检
     * 
     * @param qualityTaskList 到货质检
     * @return 结果
     */
    @Override
    public int updateQualityTaskList(QualityTaskList qualityTaskList)
    {
        return qualityTaskListMapper.updateQualityTaskList(qualityTaskList);
    }

    /**
     * 批量删除到货质检
     * 
     * @param qualityTaskListIds 需要删除的到货质检主键
     * @return 结果
     */
    @Override
    public int deleteQualityTaskListByQualityTaskListIds(Long[] qualityTaskListIds)
    {
        return qualityTaskListMapper.deleteQualityTaskListByQualityTaskListIds(qualityTaskListIds);
    }

    /**
     * 删除到货质检信息
     * 
     * @param qualityTaskListId 到货质检主键
     * @return 结果
     */
    @Override
    public int deleteQualityTaskListByQualityTaskListId(Long qualityTaskListId)
    {
        return qualityTaskListMapper.deleteQualityTaskListByQualityTaskListId(qualityTaskListId);
    }
}
