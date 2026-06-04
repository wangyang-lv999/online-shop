package com.wy.shop.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应类：所有接口的返回值都用这个类封装
 * 放在公共模块，前台和后台共用
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    // 响应状态码：200=成功，500=失败
    private Integer code;
    // 响应提示信息
    private String msg;
    // 响应的具体数据
    private T data;

    /**
     * 成功响应的静态方法，直接传入要返回的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 成功（无数据）
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 失败响应的静态方法，传入错误提示信息
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }
}