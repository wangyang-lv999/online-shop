package com.wy.shop.web.controller.api;

import com.wy.shop.common.entity.Result;
import com.wy.shop.common.entity.User;
import com.wy.shop.web.service.UserService;
import jakarta.servlet.http.HttpSession;      // SpringBoot 3 必须用 jakarta
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    // 登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginRequest request, HttpSession session) {
        // userService.login() 失败时抛出 RuntimeException，必须用 try-catch
        // 不能用 if (user != null) 判断，因为失败时不返回 null
        try {
            User user = userService.login(request.getUsername(), request.getPassword());
            session.setAttribute("loginUser", user);
            user.setPassword(null);    // 不把密码返回给前端
            return Result.success(user);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    // 退出
    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    // 获取当前登录用户
    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("未登录");
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }
}