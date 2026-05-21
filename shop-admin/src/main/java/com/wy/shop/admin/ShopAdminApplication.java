package com.wy.shop.admin;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 管理后台启动类
 */
@SpringBootApplication
// 【关键】扫描整个项目的包，确保公共模块的代码能被加载
@ComponentScan(basePackages = "com.wy.shop")
@MapperScan(basePackages = "com.wy.shop", annotationClass = Mapper.class)
public class ShopAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopAdminApplication.class, args);
    }
}
