package com.wy.shop.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.shop.common.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService extends IService<User> {

    // 登录
    User login(String username, String password);

    // 注册
    boolean register(User user);

    void updateProfile(User user, MultipartFile avatarFile) throws IOException;
}