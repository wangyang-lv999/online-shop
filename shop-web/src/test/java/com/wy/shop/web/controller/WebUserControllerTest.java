package com.wy.shop.web.controller;

import com.wy.shop.common.entity.User;
import com.wy.shop.web.mapper.WebUserMapper;
import com.wy.shop.web.service.WebUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest // SpringBoot测试注解，自动加载Spring容器
public class WebUserControllerTest {

    @Autowired
    private WebUserMapper webUserMapper;

    @Autowired
    private WebUserService webUserService;

    // 测试1：根据ID查询用户
    @Test
    public void testSelectUserById() {
        User user = webUserMapper.selectUserById(1L);
        System.out.println("查询到的用户：" + user);
    }

    // 测试2：查询所有启用的用户
    @Test
    public void testSelectAllActiveUser() {
        List<User> userList = webUserMapper.selectAllActiveUser();
        userList.forEach(System.out::println);
    }

    // 测试3：用户注册
    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUsername("zhaoliu");
        user.setPassword("123456");
        user.setNickname("赵六");
        user.setAge(24);
        user.setEmail("zhaoliu@example.com");
        Boolean success = webUserService.registerUser(user);
        System.out.println("注册结果：" + success);
        System.out.println("新增用户的自增ID：" + user.getUserId());
    }
}