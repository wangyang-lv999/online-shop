package com.wy.shop.web.service;

import com.wy.shop.common.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> getProductList();
    Product getProductById(Long id);
}
