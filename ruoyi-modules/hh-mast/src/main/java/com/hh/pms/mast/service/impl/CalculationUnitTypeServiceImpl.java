package com.hh.pms.mast.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.CalculationUnitTypeMapper;
import com.hh.pms.mast.domain.CalculationUnitType;
import com.hh.pms.mast.service.ICalculationUnitTypeService;

/**
 * 计量单位类型定义Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-28
 */
@Service
public class CalculationUnitTypeServiceImpl implements ICalculationUnitTypeService
{
    @Autowired
    private CalculationUnitTypeMapper calculationUnitTypeMapper;

    /**
     * 查询计量单位类型定义
     *
     * @param unitTypeId 计量单位类型定义主键
     * @return 计量单位类型定义
     */
    @Override
    public CalculationUnitType selectCalculationUnitTypeByUnitTypeId(Long unitTypeId)
    {
        return calculationUnitTypeMapper.selectCalculationUnitTypeByUnitTypeId(unitTypeId);
    }

    /**
     * 查询计量单位类型定义列表
     *
     * @param calculationUnitType 计量单位类型定义
     * @return 计量单位类型定义
     */
    @Override
    public List<CalculationUnitType> selectCalculationUnitTypeList(CalculationUnitType calculationUnitType)
    {
        return calculationUnitTypeMapper.selectCalculationUnitTypeList(calculationUnitType);
    }

    /**
     * 新增计量单位类型定义
     *
     * @param calculationUnitType 计量单位类型定义
     * @return 结果
     */
    @Override
    public int insertCalculationUnitType(CalculationUnitType calculationUnitType)
    {
        return calculationUnitTypeMapper.insertCalculationUnitType(calculationUnitType);
    }

    /**
     * 修改计量单位类型定义
     *
     * @param calculationUnitType 计量单位类型定义
     * @return 结果
     */
    @Override
    public int updateCalculationUnitType(CalculationUnitType calculationUnitType)
    {
        return calculationUnitTypeMapper.updateCalculationUnitType(calculationUnitType);
    }

    /**
     * 批量删除计量单位类型定义
     *
     * @param unitTypeIds 需要删除的计量单位类型定义主键
     * @return 结果
     */
    @Override
    public int deleteCalculationUnitTypeByUnitTypeIds(Long[] unitTypeIds)
    {
        return calculationUnitTypeMapper.deleteCalculationUnitTypeByUnitTypeIds(unitTypeIds);
    }

    /**
     * 删除计量单位类型定义信息
     *
     * @param unitTypeId 计量单位类型定义主键
     * @return 结果
     */
    @Override
    public int deleteCalculationUnitTypeByUnitTypeId(Long unitTypeId)
    {
        return calculationUnitTypeMapper.deleteCalculationUnitTypeByUnitTypeId(unitTypeId);
    }
}
