package com.wy.shop.common.service.impl;

import com.wy.shop.common.config.FileUploadProperties;
import com.wy.shop.common.service.FileUploadService;
import com.wy.shop.common.util.FileSecurityUtil;
import com.wy.shop.common.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class LocalFileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadProperties properties;
    @Autowired
    private FileSecurityUtil securityUtil;
    @Autowired
    private ImageUtil imageUtil;

    @Override
    public String uploadProductImage(MultipartFile file) throws IOException {
        // 1. 安全验证（后缀 + MIME + 文件头）
        securityUtil.validateImage(file);

        // 2. 相同文件检测
        String fileHash = securityUtil.getFileHash(file);
        String existingUrl = securityUtil.checkDuplicateFile(fileHash);
        if (existingUrl != null) {
            return existingUrl;   // 已存在相同文件，直接返回原 URL
        }

        // 3. 生成唯一文件名 + 按日期划分目录
        String cleanName  = securityUtil.cleanFileName(file.getOriginalFilename());
        String extension  = cleanName.substring(cleanName.lastIndexOf(".")).toLowerCase();
        String uniqueName = UUID.randomUUID().toString() + extension;
        String datePath   = securityUtil.generateDatePath();

        // 4. 创建目录
        String fullDir = properties.getBasePath() + "product/" + datePath;
        File uploadDir = new File(fullDir);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // 5. 保存原图
        File destFile = new File(uploadDir, uniqueName);
        file.transferTo(destFile);

        // 6. 自动生成多尺寸（_pc、_mobile）
        imageUtil.generateProductSizes(destFile.getAbsolutePath());

        // 7. 保存文件记录（用于去重）
        String fileUrl = properties.getUrlPrefix() + "product/" + datePath + uniqueName;
        securityUtil.saveFileRecord(fileHash, fileUrl, file.getSize(), "product");

        return fileUrl;
    }

    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        securityUtil.validateImage(file);

        String fileHash = securityUtil.getFileHash(file);
        String existingUrl = securityUtil.checkDuplicateFile(fileHash);
        if (existingUrl != null) return existingUrl;

        String cleanName  = securityUtil.cleanFileName(file.getOriginalFilename());
        String extension  = cleanName.substring(cleanName.lastIndexOf(".")).toLowerCase();
        String uniqueName = UUID.randomUUID().toString() + extension;
        String datePath   = securityUtil.generateDatePath();

        String fullDir = properties.getBasePath() + "avatar/" + datePath;
        File uploadDir = new File(fullDir);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File destFile = new File(uploadDir, uniqueName);
        file.transferTo(destFile);
        // 头像不生成多尺寸

        String fileUrl = properties.getUrlPrefix() + "avatar/" + datePath + uniqueName;
        securityUtil.saveFileRecord(fileHash, fileUrl, file.getSize(), "avatar");

        return fileUrl;
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) return;

        String relativePath = fileUrl.substring(properties.getUrlPrefix().length());
        File file = new File(properties.getBasePath() + relativePath);
        if (file.exists()) file.delete();

        // 如果是商品图片，同时删除多尺寸版本
        if (fileUrl.contains("/product/")) {
            String basePath = file.getAbsolutePath();
            String base     = basePath.substring(0, basePath.lastIndexOf("."));
            String ext      = basePath.substring(basePath.lastIndexOf("."));
            new File(base + "_pc" + ext).delete();
            new File(base + "_mobile" + ext).delete();
        }
    }
}