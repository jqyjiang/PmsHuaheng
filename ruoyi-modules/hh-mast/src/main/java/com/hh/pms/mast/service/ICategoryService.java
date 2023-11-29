package com.hh.pms.mast.service;

import java.util.List;
import com.hh.pms.mast.domain.Category;

/**
 * 品类Service接口
 * 
 * @author ruoyi
 * @date 2023-11-29
 */
public interface ICategoryService 
{
    /**
     * 查询品类
     * 
     * @param categoryid 品类主键
     * @return 品类
     */
    public Category selectCategoryByCategoryid(Long categoryid);

    /**
     * 查询品类列表
     * 
     * @param category 品类
     * @return 品类集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增品类
     * 
     * @param category 品类
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 修改品类
     * 
     * @param category 品类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 批量删除品类
     * 
     * @param categoryids 需要删除的品类主键集合
     * @return 结果
     */
    public int deleteCategoryByCategoryids(Long[] categoryids);

    /**
     * 删除品类信息
     * 
     * @param categoryid 品类主键
     * @return 结果
     */
    public int deleteCategoryByCategoryid(Long categoryid);
}
