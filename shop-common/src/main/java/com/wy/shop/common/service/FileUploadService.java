package com.wy.shop.common.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileUploadService {

    /**
     * 上传商品图片（第九节完整版：自动生成多尺寸）
     */
    String uploadProductImage(MultipartFile file) throws IOException;

    /**
     * 上传用户头像（只保存原图）
     */
    String uploadAvatar(MultipartFile file) throws IOException;

    /**
     * 删除文件
     */
    void deleteFile(String fileUrl);
}
