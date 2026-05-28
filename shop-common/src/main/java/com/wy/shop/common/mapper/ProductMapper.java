package com.wy.shop.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wy.shop.common.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
