package com.wy.shop.common.util;

import com.wy.shop.common.config.FileUploadProperties;
import com.wy.shop.common.entity.FileRecord;
import com.wy.shop.common.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class FileSecurityUtil {

    @Autowired
    private FileUploadProperties properties;

    private static final List<String> ALLOWED_EXTENSIONS =
            Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final List<String> ALLOWED_MIME_TYPES =
            Arrays.asList("image/jpeg", "image/png", "image/gif");

    /**
     * 三层图片验证：大小 → 后缀 → MIME → 文件头二进制
     */
    public void validateImage(MultipartFile file) {
        // 1. 空文件验证
        if (file.isEmpty()) {
            throw new RuntimeException("请选择要上传的文件");
        }
        // 2. 大小验证
        if (file.getSize() > properties.getMaxFileSize()) {
            throw new RuntimeException("文件大小不能超过5MB");
        }
        // 3. 后缀验证
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("文件格式不合法");
        }
        String extension = originalFilename
                .substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new RuntimeException("仅支持 jpg、jpeg、png、gif 格式");
        }
        // 4. MIME 类型验证
        if (!ALLOWED_MIME_TYPES.contains(file.getContentType())) {
            throw new RuntimeException("文件类型不合法");
        }
        // 5. 文件头二进制验证（最可靠，防止文件伪装）
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[4];
            int read = is.read(header);
            if (read < 4) throw new RuntimeException("文件内容不完整");

            StringBuilder hex = new StringBuilder();
            for (byte b : header) hex.append(String.format("%02X", b));
            String headerStr = hex.toString();

            boolean valid = headerStr.startsWith("FFD8FF")       // JPG
                    || headerStr.startsWith("89504E47")          // PNG
                    || headerStr.startsWith("47494638");         // GIF
            if (!valid) throw new RuntimeException("文件内容不是有效的图片");

        } catch (IOException e) {
            throw new RuntimeException("文件验证失败");
        }
    }

    /**
     * 去除文件名中的危险字符（只保留字母、数字、下划线、点、中划线）
     */
    public String cleanFileName(String filename) {
        if (filename == null) return "";
        return filename.replaceAll("[^a-zA-Z0-9._\\-\u4e00-\u9fa5]", "_");
    }

    /**
     * 生成按日期划分的子目录：yyyy/MM/dd/
     */
    public String generateDatePath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
    }

    // FileSecurityUtil.java 中新增以下两个方法

    @Autowired
    private FileRecordService fileRecordService;  // 新增注入
    /**
     * 计算文件 SHA-256 哈希值
     */
    public String getFileHash(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
            byte[] digest = md.digest();
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
    /**
     * 检查是否存在相同文件（同时验证物理文件存在）
     */
    public String checkDuplicateFile(String fileHash) {
        FileRecord record = fileRecordService.getByHash(fileHash);
        if (record == null) return null;

        // 验证物理文件是否真实存在，不存在则清除过期记录
        String relativePath = record.getFileUrl().substring(properties.getUrlPrefix().length());
        File physicalFile = new File(properties.getBasePath() + relativePath);
        if (!physicalFile.exists()) {
            fileRecordService.removeById(record.getId());
            return null;
        }
        return record.getFileUrl();
    }


    /** 保存文件记录 */
    public void saveFileRecord(String fileHash, String fileUrl, long fileSize, String fileType) {
        FileRecord record = new FileRecord();
        record.setFileHash(fileHash);
        record.setFileUrl(fileUrl);
        record.setFileSize(fileSize);
        record.setFileType(fileType);
        fileRecordService.save(record);
    }
}
