package com.wy.shop.web.service;

import com.wy.shop.common.entity.User;

public interface UserService {
    void register(User user);
    User login(String username, String password);
}