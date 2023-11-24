package com.hh.pms.mast.mapper;

import java.util.List;
import com.hh.pms.mast.domain.Category;

/**
 * 品类Mapper接口
 *
 * @author ruoyi
 * @date 2023-11-24
 */
public interface CategoryMapper
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
     * 删除品类
     *
     * @param categoryid 品类主键
     * @return 结果
     */
    public int deleteCategoryByCategoryid(Long categoryid);

    /**
     * 批量删除品类
     *
     * @param categoryids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryByCategoryids(Long[] categoryids);
}
