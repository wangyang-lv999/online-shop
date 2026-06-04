package com.wy.shop.web.controller.api;

import com.wy.shop.common.entity.Result;
import com.wy.shop.common.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadApiController {

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
}
