package com.wy.shop.web.controller;

import com.wy.shop.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WebTestController {

    /**
     * 用户端测试接口
     */
    @GetMapping("/test")
    public Result<String> hello() {
        return Result.success("Hello，用户Web端！");
    }
}
