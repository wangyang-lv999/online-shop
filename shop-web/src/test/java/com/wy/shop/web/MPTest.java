package com.wy.shop.web;

import com.wy.shop.common.entity.Product;
import com.wy.shop.common.entity.User;
import com.wy.shop.web.service.WebProductService;
import com.wy.shop.web.service.WebUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MPTest {

    @Autowired
    private WebUserService webUserService;

    @Autowired
    private WebProductService webProductService;

    // 测试1：根据ID查询用户
    @Test
    public void testGetUserById() {
        User user = webUserService.getById(1L);
        System.out.println("查询结果：" + user);
    }

    // 测试2：查询所有启用的用户
    @Test
    public void testGetActiveUserList() {
        List<User> userList = webUserService.lambdaQuery()
                .eq(User::getStatus, 1)
                .list();
        userList.forEach(System.out::println);
    }

    // 测试3：用户注册
    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUsername("qianqi");
        user.setPassword("123456");
        user.setNickname("钱七");
        user.setAge(26);
        Boolean success = webUserService.registerUser(user);
        System.out.println("注册结果：" + success);
    }

    // 测试4：模糊查询商品
    @Test
    public void testLikeProduct() {
        List<Product> productList = webProductService.getOnSaleProductList("手机");
        productList.forEach(System.out::println);
    }
}

