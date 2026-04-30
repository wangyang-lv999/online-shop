package com.wy.shop.web.service.impl;

import com.wy.shop.common.entity.Product;
import com.wy.shop.web.mapper.WebProductMapper;
import com.wy.shop.web.service.WebProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 前台商品业务层实现类：编写具体业务逻辑
 */
@Service // 标记为业务层组件，Spring自动管理
public class WebProductServiceImpl implements WebProductService {

    // 注入Mapper代理对象
    @Autowired
    private WebProductMapper webProductMapper;

    @Override
    public Product getProductById(Long productId) {
        // 直接调用Mapper方法执行查询
        return webProductMapper.selectProductById(productId);
    }

    @Override
    public List<Product> getOnSaleProductList(String productName) {
        // 调用Mapper方法，传入模糊查询的参数
        return webProductMapper.selectAllOnSaleProduct(productName);
    }
}
