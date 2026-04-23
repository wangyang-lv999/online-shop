package com.wy.shop.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private Integer status; // 1-正常，0-禁用
    private LocalDateTime createTime;
}
