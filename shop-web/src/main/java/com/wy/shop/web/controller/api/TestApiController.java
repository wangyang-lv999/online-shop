package com.wy.shop.web.controller.api;

import com.wy.shop.common.entity.Result;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestApiController {

    // GET /api/test/hello?name=xxx
    @GetMapping("/hello")
    public Result<String> hello(@RequestParam String name) {
        return Result.success("你好，" + name + "！");
    }

    // POST /api/test/user  请求体为 JSON
    @PostMapping("/user")
    public Result<UserDto> addUser(@RequestBody UserDto user) {
        System.out.println("收到用户：" + user);
        return Result.success(user);
    }

    @Data
    static class UserDto {
        private String username;
        private Integer age;
    }
}
