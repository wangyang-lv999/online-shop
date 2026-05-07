package com.wy.shop.common.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品公共实体类：和数据库sys_product表一一对应
 * 放在公共模块，前台和后台都能直接复用
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName
public class Product {
    @TableId(type = IdType.AUTO)
    /**
     * 商品ID，对应数据库的product_id字段，主键自增
     */
    private Long productId;

    /**
     * 商品名称，对应数据库的product_name字段
     */
    private String productName;

    /**
     * 商品价格，对应数据库的price字段
     */
    private BigDecimal price;

    /**
     * 商品库存，对应数据库的stock字段
     */
    private Integer stock;

    /**
     * 商品描述，对应数据库的description字段
     */
    private String description;

    /**
     * 商品状态：1=上架，0=下架，对应数据库的status字段
     */
    private Integer status;

    /**
     * 创建时间，对应数据库的create_time字段
     */
    private LocalDateTime createTime;

    /**
     * 更新时间，对应数据库的update_time字段
     */
    private LocalDateTime updateTime;
}