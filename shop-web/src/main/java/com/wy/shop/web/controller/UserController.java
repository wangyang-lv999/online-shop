package com.wy.shop.web.controller;

import com.wy.shop.common.entity.User;
import com.wy.shop.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String toLogin(Model model) {
        model.addAttribute("serverTime", java.time.LocalDateTime.now());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(String username, String password, Model model, HttpSession session) {
        try {
            User user = userService.login(username, password);
            session.setAttribute("loginUser", user);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("serverTime", java.time.LocalDateTime.now());
            return "login";
        }
    }

    @GetMapping("/register")
    public String toRegister(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("serverTime", java.time.LocalDateTime.now());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(User user, Model model) {
        try {
            userService.register(user);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("serverTime", java.time.LocalDateTime.now());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}