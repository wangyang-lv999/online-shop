package com.wy.shop.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户Web端启动类
 */
@SpringBootApplication
// 【关键】扫描整个项目的包，确保公共模块的代码能被加载
@ComponentScan(basePackages = "com.wy.shop")
public class ShopWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopWebApplication.class, args);
    }
}
