package com.wy.shop.web.controller;

 // 引用公共模块的统一响应类
import com.wy.shop.common.entity.Product;
import com.wy.shop.common.entity.Result;
import com.wy.shop.web.service.WebProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台商品接口：对外暴露的HTTP接口
 */
@RestController // 标记为Controller层，返回JSON格式数据
@RequestMapping("/product") // 统一接口前缀/product
public class WebProductController {

    // 注入业务层对象
    @Autowired
    private WebProductService webProductService;

    /**
     * 根据ID查询商品详情
     * 接口地址：GET http://localhost:8080/product/1
     * @PathVariable 获取URL路径中的商品ID
     */
    @GetMapping("/{productId}")
    public Result<Product> getProductById(@PathVariable Long productId) {
        // 1. 参数合法性校验
        if (productId == null || productId <= 0) {
            return Result.error("商品ID不合法");
        }
        // 2. 调用业务层获取数据
        Product product = webProductService.getProductById(productId);
        // 3. 结果判断
        if (product == null) {
            return Result.error("商品不存在或已下架");
        }
        // 4. 返回成功结果
        return Result.success(product);
    }

    /**
     * 查询上架商品列表，支持名称模糊查询
     * 接口地址：GET http://localhost:8080/product/list?productName=手机
     * @RequestParam 获取URL中的查询参数，非必传
     */
    @GetMapping("/list")
    public Result<List<Product>> getOnSaleProductList(
            @RequestParam(value = "productName", required = false) String productName
    ) {
        List<Product> productList = webProductService.getOnSaleProductList(productName);
        return Result.success(productList);
    }
}
