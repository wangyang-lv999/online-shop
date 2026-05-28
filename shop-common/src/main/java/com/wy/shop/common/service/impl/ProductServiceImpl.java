package com.wy.shop.common.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.shop.common.service.ProductService;
import com.wy.shop.common.entity.Product;
import com.wy.shop.common.mapper.ProductMapper;
import com.wy.shop.common.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl
        extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    @Autowired
    private FileUploadService fileUploadService;

    // ---- 上传封面图，返回 URL 列表 ----
    private List<String> uploadCovers(MultipartFile[] coverFiles) throws IOException {
        List<String> urls = new ArrayList<>();
        if (coverFiles == null) return urls;
        for (MultipartFile f : coverFiles) {
            if (f != null && !f.isEmpty()) {
                urls.add(fileUploadService.uploadProductImage(f));
            }
        }
        return urls;
    }

    @Override
    public void saveProduct(Product product, MultipartFile[] coverFiles) throws IOException {
        List<String> urls = uploadCovers(coverFiles);
        if (!urls.isEmpty()) {
            product.setCoverImages(JSON.toJSONString(urls));
        }
        product.setStatus(1);   // 新增默认上架
        save(product);
    }

    @Override
    public void updateProduct(Product product, MultipartFile[] coverFiles) throws IOException {
        List<String> newUrls = uploadCovers(coverFiles);
        if (!newUrls.isEmpty()) {
            // 有新封面图才覆盖，否则保留原有封面
            product.setCoverImages(JSON.toJSONString(newUrls));
        } else {
            // 不传文件时清空 coverImages 字段，避免 updateById 用 null 覆盖
            product.setCoverImages(null);
        }
        updateById(product);
    }

    @Override
    public void changeStatus(Long productId, Integer status) {
        Product p = new Product();
        p.setProductId(productId);
        p.setStatus(status);
        updateById(p);
    }

    @Override
    public void logicDelete(Long productId) {
        Product p = new Product();
        p.setProductId(productId);
        p.setStatus(-1);
        updateById(p);
    }

    @Override
    public List<Product> getHomeTop10Products() {
        // 查询条件：
        // 1. 状态为上架（status = 1）
        // 2. 按创建时间倒序（最新的在前）
        // 3. 只取前10条
        return list(new QueryWrapper<Product>()
                .eq("status", 1)
                .orderByDesc("create_time")
                .last("LIMIT 10"));
    }

    @Override
    public Product getProductDetail(Long id) {
        // 纯单表查询，直接使用MyBatis-Plus自带方法
        return lambdaQuery()
                .eq(Product::getProductId, id)
                .eq(Product::getStatus, 1) // 只允许查询上架商品
                .one();
    }
}