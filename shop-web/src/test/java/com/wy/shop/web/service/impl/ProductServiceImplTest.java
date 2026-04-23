package com.wy.shop.web.service.impl;

import com.wy.shop.common.entity.Product;
import com.wy.shop.web.dao.ProductDao;
import com.wy.shop.web.dao.impl.ProductDaoImpl;
import com.wy.shop.web.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        ((ProductDaoImpl) productDao).initData();
    }

    // 测试：获取商品列表成功
    @Test
    void getProductList_Success() {
        // 执行
        List<Product> result = productService.getProductList();

        // 验证
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("华为Mate 60 Pro", result.get(0).getName());
    }

    // 测试：根据ID获取商品成功
    @Test
    void getProductById_Success() {
        // 执行
        Product result = productService.getProductById(1L);

        // 验证
        assertNotNull(result);
        assertEquals("华为Mate 60 Pro", result.getName());
        assertEquals(new BigDecimal("6999"), result.getPrice());
    }

    // 测试：根据ID获取商品失败-ID不合法
    @Test
    void getProductById_InvalidId() {
        // 执行并验证抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(-1L);
        });

        // 验证异常信息
        assertEquals("商品ID不合法", exception.getMessage());
    }

    // 测试：根据ID获取商品失败-商品不存在
    @Test
    void getProductById_NotExist() {
        // 执行并验证抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(999L);
        });

        // 验证异常信息
        assertEquals("商品不存在或已下架", exception.getMessage());
    }
}