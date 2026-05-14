package com.wy.shop.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.shop.admin.service.AdminUserService;
import com.wy.shop.common.entity.User;
import com.wy.shop.admin.mapper.AdminUserMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, User> implements AdminUserService {

    // 继承MP自带的 list、getById、updateById、removeById 直接用，不用写任何代码
}