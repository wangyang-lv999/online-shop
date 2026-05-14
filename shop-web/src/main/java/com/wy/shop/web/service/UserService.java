package com.wy.shop.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.shop.common.entity.User;

public interface UserService extends IService<User> {

    // 登录
    User login(String username, String password);

    // 注册
    boolean register(User user);
}