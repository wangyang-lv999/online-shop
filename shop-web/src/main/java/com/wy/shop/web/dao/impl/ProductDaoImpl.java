package com.wy.shop.web.dao.impl;


import com.wy.shop.common.entity.Product;
import com.wy.shop.web.dao.ProductDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductDaoImpl implements ProductDao {

    // 模拟数据库商品表
    private final List<Product> productTable = new ArrayList<>();

//    public ProductDaoImpl() {
//        productTable.add(new Product(1L, "华为Mate 60 Pro", new BigDecimal("6999"), "huawei.jpg", "华为最新旗舰手机", 100, 1));
//        productTable.add(new Product(2L, "苹果iPhone 15", new BigDecimal("5999"), "iphone.jpg", "苹果最新手机", 200, 1));
//        productTable.add(new Product(3L, "小米14", new BigDecimal("3999"), "xiaomi.jpg", "小米最新旗舰手机", 150, 1));
//        productTable.add(new Product(4L, "下架商品", new BigDecimal("999"), "test.jpg", "测试下架商品", 0, 0));
//    }

    // 在ProductDaoImpl中添加
    public void initData() {
        productTable.clear();
        productTable.add(new Product(1L, "华为Mate 60 Pro", new BigDecimal("6999"), "huawei.jpg", "华为最新旗舰手机", 100, 1));
        productTable.add(new Product(2L, "苹果iPhone 15", new BigDecimal("5999"), "iphone.jpg", "苹果最新手机", 200, 1));
        productTable.add(new Product(3L, "小米14", new BigDecimal("3999"), "xiaomi.jpg", "小米最新旗舰手机", 150, 1));
        productTable.add(new Product(4L, "下架商品", new BigDecimal("999"), "test.jpg", "测试下架商品", 0, 0));
    }

    @Override
    public List<Product> selectAllOnSale() {
        // 只返回上架状态的商品
        return productTable.stream()
                .filter(p -> p.getStatus() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public Product selectById(Long id) {
        for (Product product : productTable) {
            if (product.getId().equals(id) && product.getStatus() == 1) {
                return new Product(product.getId(), product.getName(), product.getPrice(),
                        product.getImage(), product.getDescription(), product.getStock(), product.getStatus());
            }
        }
        return null;
    }
}
