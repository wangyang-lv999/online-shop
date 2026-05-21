package com.wy.shop.web.controller;

import com.wy.shop.common.entity.Result;
import com.wy.shop.common.entity.User;
import com.wy.shop.common.service.FileUploadService;
import com.wy.shop.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserService userService;

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String imageUrl = fileUploadService.uploadProductImage(file);
            // 展示时显示电脑版图片
            String pcUrl = imageUrl.substring(0, imageUrl.lastIndexOf("."))
                    + "_pc"
                    + imageUrl.substring(imageUrl.lastIndexOf("."));
            model.addAttribute("message", "文件上传成功！");
            model.addAttribute("success", true);
            model.addAttribute("imageUrl", pcUrl);
        } catch (Exception e) {
            model.addAttribute("message", "上传失败：" + e.getMessage());
            model.addAttribute("success", false);
        }
        return "upload";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";  // 未登录跳回登录页
        }
        model.addAttribute("user", loginUser);
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute User user,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            HttpSession session,
            Model model
    ) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        try {
            userService.updateProfile(user, avatarFile);
            session.setAttribute("loginUser", userService.getById(user.getUserId()));
            model.addAttribute("message", "信息更新成功");
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("message", "更新失败：" + e.getMessage());
            model.addAttribute("success", false);
        }
        model.addAttribute("user", userService.getById(user.getUserId()));
        return "user/profile";
    }
}
