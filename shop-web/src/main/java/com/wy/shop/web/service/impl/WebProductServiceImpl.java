package com.wy.shop.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.shop.common.entity.Product;
import com.wy.shop.web.mapper.WebProductMapper;
import com.wy.shop.web.service.WebProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 前台商品业务层实现类：编写具体业务逻辑
 */
@Service // 标记为业务层组件，Spring自动管理
public class WebProductServiceImpl extends ServiceImpl<WebProductMapper, Product> implements WebProductService {

//    // 注入Mapper代理对象
//    @Autowired
//    private WebProductMapper webProductMapper;
//
//    @Override
//    public Product getProductById(Long productId) {
//        // 直接调用Mapper方法执行查询
//        return webProductMapper.selectProductById(productId);
//    }
//
//    @Override
//    public List<Product> getOnSaleProductList(String productName) {
//        // 调用Mapper方法，传入模糊查询的参数
//        return webProductMapper.selectAllOnSaleProduct(productName);
//    }
    @Override
    public Product getOnSaleProductById(Long productId) {
    LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
    // 等价于SQL：WHERE product_id = ? AND status = 1
    queryWrapper.eq(Product::getProductId, productId)
            .eq(Product::getStatus, 1); // 只查询上架的商品
    // getOne()：查询单条数据
    return this.getOne(queryWrapper);
}

    @Override
    public List<Product> getOnSaleProductList(String productName) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        // 固定条件：只查询上架的商品
        queryWrapper.eq(Product::getStatus, 1);
        // 动态条件：如果商品名称不为空，拼接模糊查询
        // 等价于XML的<if>标签动态SQL
        queryWrapper.like(StringUtils.hasText(productName), Product::getProductName, productName);
        // 按创建时间倒序
        queryWrapper.orderByDesc(Product::getCreateTime);
        // list()：查询列表数据
        return this.list(queryWrapper);
    }
}
