package com.wy.shop.admin.controller;

import com.wy.shop.common.entity.Result;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    // 查询用户列表
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> getUserList() {
        // 模拟返回用户列表
        List<Map<String, Object>> userList = new ArrayList<>();

        Map<String, Object> user1 = new HashMap<>();
        user1.put("userId", 1L);
        user1.put("username", "zhangsan");
        user1.put("nickname", "张三");
        userList.add(user1);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("userId", 2L);
        user2.put("username", "lisi");
        user2.put("nickname", "李四");
        userList.add(user2);

        return Result.success(userList);
    }

    // 新增用户
    @PostMapping("/add")
    public Result<Long> addUser(@RequestBody Map<String, Object> userMap) {
        // 模拟返回新增用户ID
        return Result.success(1L);
    }

    // 修改用户
    @PutMapping("/update")
    public Result<Boolean> updateUser(@RequestBody Map<String, Object> userMap) {
        // 模拟返回修改结果
        return Result.success(true);
    }

    // 删除用户
    @DeleteMapping("/del{userId}")
    public Result<Boolean> deleteUser(@PathVariable Long userId) {
        // 模拟返回删除结果
        return Result.success(true);
    }


    // 单个路径参数
    @GetMapping("/{userId}")
    public Result<Map<String, Object>> getUserById(@PathVariable("userId") Long userId) {
        // 模拟返回用户详情
        Map<String, Object> user = new HashMap<>();
        user.put("userId", userId);
        user.put("username", "zhangsan");
        user.put("nickname", "张三");
        user.put("phone", "138****8000");
        return Result.success(user);
    }

    // 多个路径参数
    @GetMapping("/{userId}/order/{orderId}")
    public Result<Map<String, Object>> getUserOrder(
            @PathVariable("userId") Long userId,
            @PathVariable("orderId") Long orderId
    ) {
        // 模拟返回订单详情
        Map<String, Object> order = new HashMap<>();
        order.put("userId", userId);
        order.put("orderId", orderId);
        order.put("orderNo", "ORD20240101001");
        order.put("totalAmount", 99.99);
        return Result.success(order);
    }

}