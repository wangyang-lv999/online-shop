package com.wy.shop.web.dao.impl;

import com.wy.shop.common.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDaoImplTest {

    @Autowired
    private ProductDaoImpl productDao;

    @BeforeEach
    void setUp() {
        productDao.initData(); // 记得给ProductDaoImpl也加上initData()方法
    }

    // 测试：查询所有上架商品
    @Test
    void selectAllOnSale() {
        // 执行
        List<Product> productList = productDao.selectAllOnSale();

        // 验证：只返回上架的3个商品，不包含下架的第4个
        assertNotNull(productList);
        assertEquals(3, productList.size());
    }

    // 测试：根据ID查询存在的上架商品
    @Test
    void selectById_ExistOnSale() {
        // 执行
        Product product = productDao.selectById(1L);

        // 验证
        assertNotNull(product);
        assertEquals("华为Mate 60 Pro", product.getName());
        assertEquals(new BigDecimal("6999"), product.getPrice());
        assertEquals(1, product.getStatus());
    }

    // 测试：根据ID查询下架商品
    @Test
    void selectById_OffSale() {
        // 执行
        Product product = productDao.selectById(4L);

        // 验证：下架商品查询不到
        assertNull(product);
    }

    // 测试：根据ID查询不存在的商品
    @Test
    void selectById_NotExist() {
        // 执行
        Product product = productDao.selectById(999L);

        // 验证
        assertNull(product);
    }
}