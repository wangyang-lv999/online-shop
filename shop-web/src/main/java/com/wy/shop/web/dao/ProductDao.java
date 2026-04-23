package com.wy.shop.web.dao;

import com.wy.shop.common.entity.Product;
import java.util.List;

public interface ProductDao {
    List<Product> selectAllOnSale();
    Product selectById(Long id);
}