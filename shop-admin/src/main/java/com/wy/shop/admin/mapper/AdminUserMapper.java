package com.wy.shop.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wy.shop.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapper<User> {
}