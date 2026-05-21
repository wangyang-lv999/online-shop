package com.wy.shop.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.shop.common.entity.User;
import com.wy.shop.common.service.FileUploadService;
import com.wy.shop.web.mapper.UserMapper;
import com.wy.shop.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 登录
    @Override
    public User login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);

        User user = baseMapper.selectOne(wrapper);

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new RuntimeException("账号已禁用");
        }
        return user;
    }

    // 注册
    @Override
    public boolean register(User user) {
        // 判断用户名是否存在
        Long count = baseMapper.selectCount(
                new QueryWrapper<User>().eq("username", user.getUsername())
        );

        if (count > 0) {
            throw new RuntimeException("用户名已存在");
        }

        user.setStatus(1);
        return baseMapper.insert(user) > 0;
    }
    @Autowired
    private FileUploadService fileUploadService; //注入


    @Override
    public void updateProfile(User user, MultipartFile avatarFile) throws IOException {
        if (avatarFile != null && !avatarFile.isEmpty()) {
            // 删除旧头像
            User oldUser = getById(user.getUserId());
            if (oldUser.getAvatar() != null) {
                fileUploadService.deleteFile(oldUser.getAvatar());
            }
            // 上传新头像
            String avatarUrl = fileUploadService.uploadAvatar(avatarFile);
            user.setAvatar(avatarUrl);
        }
        updateById(user);
    }
}
