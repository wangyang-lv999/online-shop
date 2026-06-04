package com.wy.shop.web.controller.api;

import com.alibaba.fastjson2.JSON;         // 项目用 fastjson2，注意包名末尾有 "2"
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wy.shop.common.entity.Product;
import com.wy.shop.common.entity.Result;
import com.wy.shop.common.service.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;   // common 模块中已有，直接注入

    @GetMapping("/list")
    public Result<PageResult> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer size
    ) {
        Page<Product> productPage = productService.page(
                new Page<>(page, size),
                new QueryWrapper<Product>().eq("status", 1).orderByDesc("create_time")
        );

        // 取第一张封面图的 _pc 缩略图
        for (Product p : productPage.getRecords()) {
            String coverJson = p.getCoverImages();
            if (coverJson != null && !coverJson.isEmpty()) {
                List<String> urls = JSON.parseArray(coverJson, String.class);
                if (!urls.isEmpty()) {
                    String first = urls.get(0);
                    int dot = first.lastIndexOf(".");
                    p.setCoverImages(first.substring(0, dot) + "_pc" + first.substring(dot));
                }
            }
        }

        PageResult result = new PageResult();
        result.setList(productPage.getRecords());
        result.setTotal(productPage.getTotal());
        result.setHasNext(productPage.hasNext());
        return Result.success(result);
    }

    @Data
    static class PageResult {
        private List<Product> list;
        private long total;
        private boolean hasNext;
    }
}