package com.wy.shop.web.service.impl;

import com.wy.shop.common.entity.User;
import com.wy.shop.web.dao.UserDao;
import com.wy.shop.web.dao.impl.UserDaoImpl;
import com.wy.shop.web.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    // 注入要测试的Service
    @Autowired
    private UserService userService;

    // 注入真实的Dao，用于重置数据
    @Autowired
    private UserDao userDao;

    // 每个测试前重置数据，保证测试独立性
    @BeforeEach
    void setUp() {
        // 因为我们注入的是真实的UserDaoImpl，所以可以直接调用initData()
        ((UserDaoImpl) userDao).initData();
    }

    // 测试：登录成功
    @Test
    void login_Success() {
        // 执行
        User result = userService.login("zhangsan", "123456");

        // 验证
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("张三", result.getNickname());
        assertNull(result.getPassword()); // 验证密码被置空
    }

    // 测试：登录失败-用户不存在
    @Test
    void login_UserNotExist() {
        // 执行并验证抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login("nonexist", "123456");
        });

        // 验证异常信息
        assertEquals("用户不存在", exception.getMessage());
    }

    // 测试：登录失败-密码错误
    @Test
    void login_WrongPassword() {
        // 执行并验证抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login("zhangsan", "wrongpassword");
        });

        // 验证异常信息
        assertEquals("密码错误", exception.getMessage());
    }

    // 测试：登录失败-用户已禁用
    @Test
    void login_UserDisabled() {
        // 先准备一个禁用的用户
        User disabledUser = new User(null, "disableduser", "123456", "禁用用户", "13800000000", 0, null);
        userDao.insert(disabledUser);

        // 执行并验证抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login("disableduser", "123456");
        });

        // 验证异常信息
        assertEquals("用户已被禁用", exception.getMessage());
    }

    // 测试：注册成功
    @Test
    void register_Success() {
        // 准备
        User newUser = new User();
        newUser.setUsername("wangwu");
        newUser.setPassword("123456");
        newUser.setNickname("王五");
        newUser.setPhone("13700137000");

        // 执行
        userService.register(newUser);

        // 验证
        User registeredUser = userDao.selectByUsername("wangwu");
        assertNotNull(registeredUser);
        assertEquals("王五", registeredUser.getNickname());
    }

    // 测试：注册失败-用户名已存在
    @Test
    void register_UsernameExist() {
        // 准备
        User newUser = new User();
        newUser.setUsername("zhangsan"); // 用户名已存在
        newUser.setPassword("123456");

        // 执行并验证抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(newUser);
        });

        // 验证异常信息
        assertEquals("用户名已存在", exception.getMessage());
    }

    // 测试：注册失败-密码长度不足
    @Test
    void register_PasswordTooShort() {
        // 准备
        User newUser = new User();
        newUser.setUsername("wangwu");
        newUser.setPassword("123"); // 密码只有3位

        // 执行并验证抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(newUser);
        });

        // 验证异常信息
        assertEquals("密码长度不能少于6位", exception.getMessage());
    }
}