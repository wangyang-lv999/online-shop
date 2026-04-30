package com.wy.shop.web.service;

import com.wy.shop.common.entity.User;
import java.util.List;

/**
 * 前台用户业务层接口：定义业务方法
 * 业务逻辑写在实现类里，接口只定义方法规范
 */
public interface WebUserService {
    // 根据ID查询用户
    User getUserById(Long userId);
    // 查询所有启用的用户列表
    List<User> getAllActiveUser();
    // 用户注册
    Boolean registerUser(User user);
}
