package com.wy.shop.web.controller;

import com.wy.shop.common.entity.Result;
import com.wy.shop.common.entity.User;
import com.wy.shop.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/user")
public class UserController {

    @Autowired // Spring自动将UserServiceImpl对象注入到这里
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        try {
            userService.register(user);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody User loginUser) {
        try {
            User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
