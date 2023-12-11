package com.hh.pms.service;

import java.util.List;

import com.ruoyi.system.api.domain.Products;

/**
 *  产品信息Service接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface IProductsService 
{
    /**
     * 查询 产品信息
     * 
     * @param productId  产品信息主键
     * @return  产品信息
     */
    public Products selectProductsByProductId(Long productId);

    /**
     * 查询 产品信息列表
     * 
     * @param products  产品信息
     * @return  产品信息集合
     */
    public List<Products> selectProductsList(Products products);

    /**
     * 新增 产品信息
     * 
     * @param products  产品信息
     * @return 结果
     */
    public int insertProducts(Products products);

    /**
     * 修改 产品信息
     * 
     * @param products  产品信息
     * @return 结果
     */
    public int updateProducts(Products products);

    /**
     * 批量删除 产品信息
     * 
     * @param productIds 需要删除的 产品信息主键集合
     * @return 结果
     */
    public int deleteProductsByProductIds(Long[] productIds);

    /**
     * 删除 产品信息信息
     * 
     * @param productId  产品信息主键
     * @return 结果
     */
    public int deleteProductsByProductId(Long productId);
}
