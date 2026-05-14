package com.wy.shop.admin.controller;


import com.wy.shop.common.entity.User;
import com.wy.shop.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    // 直接跳用户列表，无需登录
    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> userList = adminUserService.list();
        model.addAttribute("userList", userList);
        return "admin/user/list";
    }

    // 编辑用户页面
    @GetMapping("/user/edit/{userId}")
    public String toEdit(@PathVariable Long userId, Model model) {
        User user = adminUserService.getById(userId);
        model.addAttribute("user", user);
        return "admin/user/form";
    }

    // 保存修改
    @PostMapping("/user/save")
    public String saveUser(User user) {
        adminUserService.updateById(user);
        return "redirect:/admin/user/list";
    }

    // 删除用户
    @GetMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        adminUserService.removeById(userId);
        return "redirect:/admin/user/list";
    }
}