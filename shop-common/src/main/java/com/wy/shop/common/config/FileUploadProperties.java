package com.wy.shop.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {
    private String basePath;     // 本地磁盘根存储路径
    private String urlPrefix;    // 浏览器访问前缀
    private long maxFileSize;    // 单个文件最大字节数
}
