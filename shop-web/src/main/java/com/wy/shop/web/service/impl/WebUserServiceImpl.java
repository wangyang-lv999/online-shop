package com.wy.shop.web.service.impl;

import com.wy.shop.common.entity.User;
import com.wy.shop.web.mapper.WebUserMapper;
import com.wy.shop.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 前台用户业务层实现类：写具体的业务逻辑
 */
// @Service：Spring核心注解，必须加
// 作用：标记这个类是业务层实现类，Spring会自动管理这个对象，Controller中可以注入使用
@Service
public class WebUserServiceImpl implements WebUserService {

    // @Autowired：自动注入Mapper接口的动态代理对象，不用手动new
    @Autowired
    private WebUserMapper webUserMapper;

    @Override
    public User getUserById(Long userId) {
        // 直接调用Mapper的方法，执行SQL查询
        return webUserMapper.selectUserById(userId);
    }

    @Override
    public List<User> getAllActiveUser() {
        return webUserMapper.selectAllActiveUser();
    }

    @Override
    public Boolean registerUser(User user) {
        // 【业务逻辑1】判断用户名是否已存在
        User existUser = webUserMapper.selectUserByUsername(user.getUsername());
        if (existUser != null) {
            // 用户名已存在，抛出异常，Controller中捕获后返回错误提示
            throw new RuntimeException("用户名已存在，无法注册");
        }
        // 【业务逻辑2】调用Mapper执行新增操作
        int rows = webUserMapper.insertUser(user);
        // 受影响行数>0表示新增成功，返回true；否则返回false
        return rows > 0;
    }
}
