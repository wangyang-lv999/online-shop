package com.wy.shop.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.shop.common.entity.User;
import com.wy.shop.web.mapper.UserMapper;
import com.wy.shop.web.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 登录
    @Override
    public User login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);

        User user = baseMapper.selectOne(wrapper);

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new RuntimeException("账号已禁用");
        }
        return user;
    }

    // 注册
    @Override
    public boolean register(User user) {
        // 判断用户名是否存在
        Long count = baseMapper.selectCount(
                new QueryWrapper<User>().eq("username", user.getUsername())
        );

        if (count > 0) {
            throw new RuntimeException("用户名已存在");
        }

        user.setStatus(1);
        return baseMapper.insert(user) > 0;
    }
}
