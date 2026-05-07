package com.wy.shop.web.controller;

 // 引用公共模块的统一响应类
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wy.shop.common.entity.Result;
import com.wy.shop.common.entity.User;
import com.wy.shop.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台用户接口：对外暴露的HTTP接口
 */
// @RestController：标记这个类是Controller层，所有方法的返回值都会自动转为JSON格式
@RestController
// @RequestMapping：给当前类的所有接口统一添加前缀/user
@RequestMapping("/user")
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    /**
     * 根据ID查询用户详情
     * 接口地址：GET http://localhost:8080/user/1
     * 【接口完全兼容之前的版本】
     */
    @GetMapping("/{userId}")
    public Result<User> getUserById(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return Result.error("用户ID不合法");
        }
        // 调用MP通用的getById方法，无需写Service和Mapper的自定义方法
        User user = webUserService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 查询所有启用的用户列表
     * 接口地址：GET http://localhost:8080/user/list
     * 【接口完全兼容之前的版本】
     */
    @GetMapping("/list")
    public Result<List<User>> getAllActiveUser() {
        // 用条件构造器查询状态为1的用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getStatus, 1);
        List<User> userList = webUserService.list(queryWrapper);
        userList.forEach(user -> user.setPassword(null));
        return Result.success(userList);
    }

    /**
     * 用户注册
     * 接口地址：POST http://localhost:8080/user/register
     * 【接口完全兼容之前的版本】
     */
    @PostMapping("/register")
    public Result<Boolean> registerUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }
        try {
            Boolean success = webUserService.registerUser(user);
            return Result.success(success);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
