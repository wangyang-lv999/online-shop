package com.wy.shop.web.service.impl;

import com.wy.shop.common.entity.Product;
import com.wy.shop.web.dao.ProductDao;
import com.wy.shop.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProductList() {
        // 直接调用Dao层方法
        return productDao.selectAllOnSale();
    }

    @Override
    public Product getProductById(Long id) {
        // 业务逻辑：参数校验
        if (id == null || id <= 0) {
            throw new RuntimeException("商品ID不合法");
        }

        // 调用Dao层查询商品
        Product product = productDao.selectById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在或已下架");
        }

        return product;
    }
}