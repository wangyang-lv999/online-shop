package com.wy.shop.web.service;

import com.wy.shop.common.entity.Product;
import java.util.List;

/**
 * 前台商品业务层接口：定义商品相关的业务方法
 */
public interface WebProductService {
    // 根据ID查询商品详情
    Product getProductById(Long productId);
    // 查询上架商品列表，支持模糊查询
    List<Product> getOnSaleProductList(String productName);
}
