package com.wy.shop.web.controller;

 // 引用公共模块的统一响应类
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
@RequestMapping("/web/user")
public class WebUserController {

    // 注入业务层对象
    @Autowired
    private WebUserService webUserService;

    /**
     * 根据ID查询用户详情
     * 接口地址：GET http://localhost:8080/web/user/1
     * @PathVariable：获取URL路径中的参数，和{userId}占位符对应
     */
    @GetMapping("/{userId}")
    public Result<User> getUserById(@PathVariable Long userId) {
        // 1. 先做参数合法性校验，拦截非法请求
        if (userId == null || userId <= 0) {
            return Result.error("用户ID不合法");
        }
        // 2. 调用业务层方法，获取数据
        User user = webUserService.getUserById(userId);
        // 3. 结果判断
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 4. 安全处理：隐藏密码，不返回给前端
        user.setPassword(null);
        // 5. 返回成功结果
        return Result.success(user);
    }

    /**
     * 查询所有启用的用户列表
     * 接口地址：GET http://localhost:8080/web/user/list
     */
    @GetMapping("/list")
    public Result<List<User>> getAllActiveUser() {
        List<User> userList = webUserService.getAllActiveUser();
        // 隐藏所有用户的密码
        userList.forEach(user -> user.setPassword(null));
        return Result.success(userList);
    }

    /**
     * 用户注册
     * 接口地址：POST http://localhost:8080/web/user/register
     * @RequestBody：接收前端传入的JSON数据，自动封装为User实体类对象
     */
    @PostMapping("/register")
    public Result<Boolean> registerUser(@RequestBody User user) {
        // 基础参数校验
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }
        try {
            // 调用业务层注册方法
            Boolean success = webUserService.registerUser(user);
            return Result.success(success);
        } catch (Exception e) {
            // 捕获业务异常，返回友好的错误提示
            return Result.error(e.getMessage());
        }
    }
}
