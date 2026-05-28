package com.wy.shop.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.shop.common.entity.Product;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ProductService extends IService<Product> {

    /** 新增商品（含封面图上传） */
    void saveProduct(Product product, MultipartFile[] coverFiles) throws IOException;

    /** 更新商品（有新封面图时替换） */
    void updateProduct(Product product, MultipartFile[] coverFiles) throws IOException;

    /** 修改商品状态（上架/下架） */
    void changeStatus(Long productId, Integer status);

    /** 逻辑删除（status = -1） */
    void logicDelete(Long productId);

    /**
     * 查询首页最新上架的前10条商品（只显示上架状态）
     */
    List<Product> getHomeTop10Products();

    /**
     * 根据ID获取商品详情（用户端）
     */
    Product getProductDetail(Long id);
}
