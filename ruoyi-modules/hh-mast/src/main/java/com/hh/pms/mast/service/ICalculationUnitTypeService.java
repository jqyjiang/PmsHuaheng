package com.hh.pms.mast.service;

import com.ruoyi.system.api.domain.CalculationUnitType;

import java.util.List;

/**
 * 计量单位类型定义Service接口
 *
 * @author ruoyi
 * @date 2023-11-28
 */
public interface ICalculationUnitTypeService
{
    /**
     * 查询计量单位类型定义
     *
     * @param unitTypeId 计量单位类型定义主键
     * @return 计量单位类型定义
     */
    public CalculationUnitType selectCalculationUnitTypeByUnitTypeId(Long unitTypeId);

    /**
     * 查询计量单位类型定义列表
     *
     * @param calculationUnitType 计量单位类型定义
     * @return 计量单位类型定义集合
     */
    public List<CalculationUnitType> selectCalculationUnitTypeList(CalculationUnitType calculationUnitType);

    /**
     * 新增计量单位类型定义
     *
     * @param calculationUnitType 计量单位类型定义
     * @return 结果
     */
    public int insertCalculationUnitType(CalculationUnitType calculationUnitType);

    /**
     * 修改计量单位类型定义
     *
     * @param calculationUnitType 计量单位类型定义
     * @return 结果
     */
    public int updateCalculationUnitType(CalculationUnitType calculationUnitType);

    /**
     * 批量删除计量单位类型定义
     *
     * @param unitTypeIds 需要删除的计量单位类型定义主键集合
     * @return 结果
     */
    public int deleteCalculationUnitTypeByUnitTypeIds(Long[] unitTypeIds);

    /**
     * 删除计量单位类型定义信息
     *
     * @param unitTypeId 计量单位类型定义主键
     * @return 结果
     */
    public int deleteCalculationUnitTypeByUnitTypeId(Long unitTypeId);
}
