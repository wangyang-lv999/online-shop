package com.wy.shop.admin.controller;

import com.wy.shop.common.entity.Result;
import com.wy.shop.common.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = fileUploadService.uploadProductImage(file);
            return Result.success(url);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @PostMapping("/admin/upload")
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

    // 富文本编辑器专用上传接口（返回 JSON）
    @PostMapping("/api/upload")
    @ResponseBody
    public Result<String> richTextUpload(@RequestParam("file") MultipartFile file) {
        try {
            String url   = fileUploadService.uploadProductImage(file);
            String pcUrl = url.substring(0, url.lastIndexOf("."))
                    + "_pc"
                    + url.substring(url.lastIndexOf("."));
            return Result.success(pcUrl);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
