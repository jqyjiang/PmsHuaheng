package com.hh.pms.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hh.pms.mapper.ProductsMapper;

import com.hh.pms.service.IProductsService;

/**
 *  产品信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class ProductsServiceImpl implements IProductsService 
{
    @Autowired
    private ProductsMapper productsMapper;

    /**
     * 查询 产品信息
     * 
     * @param productId  产品信息主键
     * @return  产品信息
     */
    @Override
    public Products selectProductsByProductId(Long productId)
    {
        return productsMapper.selectProductsByProductId(productId);
    }

    /**
     * 查询 产品信息列表
     * 
     * @param products  产品信息
     * @return  产品信息
     */
    @Override
    public List<Products> selectProductsList(Products products)
    {
        return productsMapper.selectProductsList(products);
    }

    /**
     * 新增 产品信息
     * 
     * @param products  产品信息
     * @return 结果
     */
    @Override
    public int insertProducts(Products products)
    {
        return productsMapper.insertProducts(products);
    }

    /**
     * 修改 产品信息
     * 
     * @param products  产品信息
     * @return 结果
     */
    @Override
    public int updateProducts(Products products)
    {
        return productsMapper.updateProducts(products);
    }

    /**
     * 批量删除 产品信息
     * 
     * @param productIds 需要删除的 产品信息主键
     * @return 结果
     */
    @Override
    public int deleteProductsByProductIds(Long[] productIds)
    {
        return productsMapper.deleteProductsByProductIds(productIds);
    }

    /**
     * 删除 产品信息信息
     * 
     * @param productId  产品信息主键
     * @return 结果
     */
    @Override
    public int deleteProductsByProductId(Long productId)
    {
        return productsMapper.deleteProductsByProductId(productId);
    }
}
