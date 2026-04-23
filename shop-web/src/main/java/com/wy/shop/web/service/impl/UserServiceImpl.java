package com.wy.shop.web.service.impl;

import com.wy.shop.common.entity.User;
import com.wy.shop.web.dao.UserDao;
import com.wy.shop.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 关键：告诉Spring这是Service层组件，会被自动创建为Bean
public class UserServiceImpl implements UserService {

    @Autowired // 关键：Spring自动将UserDaoImpl对象注入到这里
    private UserDao userDao;

    @Override
    public void register(User user) {
        // 业务逻辑1：参数基础校验
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("密码长度不能少于6位");
        }

        // 业务逻辑2：校验用户名是否已存在
        User existUser = userDao.selectByUsername(user.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 业务逻辑3：调用Dao层保存用户
        boolean success = userDao.insert(user);
        if (!success) {
            throw new RuntimeException("注册失败，请稍后重试");
        }
    }

    @Override
    public User login(String username, String password) {
        // 业务逻辑1：参数基础校验
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }

        // 业务逻辑2：查询用户
        User user = userDao.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 业务逻辑3：校验密码
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }

        // 业务逻辑4：校验用户状态
        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }

        // 登录成功，返回用户信息（注意：实际项目中不要返回密码）
        user.setPassword(null);
        return user;
    }
}
