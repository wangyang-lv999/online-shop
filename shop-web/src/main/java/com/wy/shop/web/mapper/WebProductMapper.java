package com.wy.shop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wy.shop.common.entity.Product; // 引用公共模块的商品实体类
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 前台商品DAO层接口：定义商品相关的数据库操作方法
 */
@Mapper // 标记为MyBatis的Mapper接口，自动生成动态代理对象
public interface WebProductMapper extends BaseMapper<Product> {

    /**
     * 根据商品ID查询商品详情
     * @Param 给参数起别名，XML中通过#{productId}获取参数值
     */
    Product selectProductById(@Param("productId") Long productId);

    /**
     * 查询所有上架的商品，支持按商品名称模糊查询
     * @Param productName 商品名称（模糊查询，非必传）
     */
    List<Product> selectAllOnSaleProduct(@Param("productName") String productName);
}