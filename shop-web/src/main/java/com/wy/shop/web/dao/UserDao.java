package com.wy.shop.web.dao;

import com.wy.shop.common.entity.User;

import java.util.List;

public interface UserDao {
    User selectByUsername(String username);
    boolean insert(User user);
    List<User> selectAll();
}
