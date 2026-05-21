package com.wy.shop.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileUploadProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 核心映射规则：URL 前缀 → 本地磁盘路径
        registry.addResourceHandler(properties.getUrlPrefix() + "**")
                .addResourceLocations("file:" + properties.getBasePath());
    }
}
