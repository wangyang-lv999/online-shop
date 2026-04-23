package com.wy.shop.web.controller;

import com.wy.shop.common.entity.Product;
import com.wy.shop.common.entity.Result;
import com.wy.shop.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public Result<List<Product>> getProductList() {
        try {
            List<Product> productList = productService.getProductList();
            return Result.success(productList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return Result.success(product);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}