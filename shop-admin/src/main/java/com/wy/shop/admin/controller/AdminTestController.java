package com.wy.shop.admin.controller;

import com.wy.shop.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminTestController {

    /**
     * 管理后台测试接口
     */

    @GetMapping("/test")
    public Result<String> hello() {
        return Result.success("Hello，管理后台！");
    }
}
