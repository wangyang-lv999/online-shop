package com.wy.shop.web.controller;

import com.wy.shop.common.entity.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    // 必传参数
    @GetMapping("/captcha")
    public Result<String> getCaptcha(@RequestParam("phone") String phone) {
        // 手动校验手机号格式
        if (phone == null || phone.length() != 11) {
            return Result.error("手机号格式不正确");
        }
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            return Result.error("手机号格式不正确");
        }
        return Result.success("验证码已发送到" + phone);
    }

    // 非必传参数+默认值
    @GetMapping("/page")
    public Result<Map<String, Object>> getUserPage(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        // 1. 封装接收到的查询参数（方便测试时查看是否正确接收）
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("username", username); // 可能为null（因为required=false）
        queryParams.put("pageNum", pageNum);
        queryParams.put("pageSize", pageSize);

        // 2. 模拟用户列表（根据pageSize动态生成，最多5条方便测试）
        List<Map<String, Object>> userList = new ArrayList<>();
        int maxCount = Math.min(pageSize, 5);
        for (long i = 1; i <= maxCount; i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("userId", i);
            user.put("username", "user" + i);
            user.put("nickname", "用户" + i);
            userList.add(user);
        }

        // 3. 封装最终返回数据：包含「查询参数」和「用户列表」
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("queryParams", queryParams);
        resultData.put("userList", userList);

        return Result.success(resultData);
    }

    // 用Map接收JSON参数
    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, Object> loginMap) {
        String username = (String) loginMap.get("username");
        String password = (String) loginMap.get("password");
        // 模拟返回token
        return Result.success("mock-token-" + username + "-123456");
    }

    // 用户注册
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody Map<String, Object> registerMap) {
        String username = (String) registerMap.get("username");
        String phone = (String) registerMap.get("phone");

        // 模拟返回注册结果
        Map<String, Object> result = new HashMap<>();
        result.put("userId", 1L);
        result.put("username", username);
        result.put("phone", phone);
        return Result.success(result);
    }
}
