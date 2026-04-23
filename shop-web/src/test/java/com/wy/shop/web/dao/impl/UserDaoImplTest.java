package com.wy.shop.web.dao.impl;

import com.wy.shop.common.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoImplTest {

    @Autowired
    private UserDaoImpl userDao;

    // 每个测试前重置数据，保证测试独立性
    @BeforeEach
    void setUp() {
        userDao.initData();
    }

    // 测试：查询存在的用户名
    @Test
    void selectByUsername_Exist() {
        // 执行
        User user = userDao.selectByUsername("zhangsan");

        // 验证
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("张三", user.getNickname());
        assertEquals("13800138000", user.getPhone());
    }

    // 测试：查询不存在的用户名
    @Test
    void selectByUsername_NotExist() {
        // 执行
        User user = userDao.selectByUsername("nonexist");

        // 验证
        assertNull(user);
    }

    // 测试：新增用户
    @Test
    void insert_Success() {
        // 准备
        User newUser = new User();
        newUser.setUsername("wangwu");
        newUser.setPassword("123456");
        newUser.setNickname("王五");
        newUser.setPhone("13700137000");

        // 执行
        boolean result = userDao.insert(newUser);

        // 验证
        assertTrue(result);
        assertEquals(3, userDao.selectAll().size());

        User insertedUser = userDao.selectByUsername("wangwu");
        assertNotNull(insertedUser);
        assertEquals(3L, insertedUser.getId());
        assertEquals(1, insertedUser.getStatus());
        assertNotNull(insertedUser.getCreateTime());
    }

    // 测试：查询所有用户
    @Test
    void selectAll() {
        // 执行
        List<User> userList = userDao.selectAll();

        // 验证
        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertEquals("zhangsan", userList.get(0).getUsername());
        assertEquals("lisi", userList.get(1).getUsername());
    }

    // 测试：返回对象副本（保护内部数据）
    @Test
    void selectByUsername_ReturnCopy() {
        // 执行
        User user = userDao.selectByUsername("zhangsan");
        user.setNickname("修改后的名字");

        // 验证：内部数据未被修改
        User userAgain = userDao.selectByUsername("zhangsan");
        assertEquals("张三", userAgain.getNickname());
    }
}