package com.hh.pms.mast.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.system.api.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mast.mapper.CategoryMapper;
import com.hh.pms.mast.service.ICategoryService;

/**
 * 品类Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-29
 */
@Service
public class CategoryServiceImpl implements ICategoryService
{
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询品类
     *
     * @param categoryid 品类主键
     * @return 品类
     */
    @Override
    public Category selectCategoryByCategoryid(Long categoryid)
    {
        return categoryMapper.selectCategoryByCategoryid(categoryid);
    }

    /**
     * 查询品类列表
     *
     * @param category 品类
     * @return 品类
     */
    @Override
    public List<Category> selectCategoryList(Category category)
    {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增品类
     *
     * @param category 品类
     * @return 结果
     */
    @Override
    public int insertCategory(Category category)
    {
        category.setlUpdateTime(new Date());
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改品类
     *
     * @param category 品类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category)
    {
        category.setlUpdateTime(new Date());
        return categoryMapper.updateCategory(category);
    }

    /**
     * 批量删除品类
     *
     * @param categoryids 需要删除的品类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByCategoryids(Long[] categoryids)
    {
        return categoryMapper.deleteCategoryByCategoryids(categoryids);
    }

    /**
     * 删除品类信息
     *
     * @param categoryid 品类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByCategoryid(Long categoryid)
    {
        return categoryMapper.deleteCategoryByCategoryid(categoryid);
    }

    @Override
    public int batchCategory(List<Category> categoryList) {
        Date currentTime = new Date(); // 获取当前时间
        for (Category category : categoryList) {
            category.setlUpdateTime(currentTime);
        }
        return categoryMapper.batchCategory(categoryList);
    }
}
