package com.wy.shop.web.dao.impl;

import com.wy.shop.common.entity.User;
import com.wy.shop.web.dao.UserDao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository // 关键：告诉Spring这是Dao层组件，会被自动创建为Bean
public class UserDaoImpl implements UserDao {

    // 模拟数据库用户表
    private final List<User> userTable = new ArrayList<>();

    // 构造方法：项目启动时自动执行，初始化测试数据
//    public UserDaoImpl() {
//        userTable.add(new User(1L, "zhangsan", "123456", "张三", "13800138000", 1, LocalDateTime.now()));
//        userTable.add(new User(2L, "lisi", "123456", "李四", "13900139000", 1, LocalDateTime.now()));
//    }
    public UserDaoImpl() {
        initData(); // 构造方法还是调用它，原有功能完全不变
    }

    // ✅ 只加这一个方法，供测试用
    public void initData() {
        userTable.clear();
        userTable.add(new User(1L, "zhangsan", "123456", "张三", "13800138000", 1, LocalDateTime.now()));
        userTable.add(new User(2L, "lisi", "123456", "李四", "13900139000", 1, LocalDateTime.now()));
    }

    @Override
    public User selectByUsername(String username) {
        for (User user : userTable) {
            if (user.getUsername().equals(username)) {
                // 返回对象副本，防止外部修改内部数据
                return new User(user.getId(), user.getUsername(), user.getPassword(),
                        user.getNickname(), user.getPhone(), user.getStatus(), user.getCreateTime());
            }
        }
        return null;
    }

    @Override
    public boolean insert(User user) {
        // 模拟数据库自增主键
        Long maxId = userTable.stream()
                .map(User::getId)
                .max(Long::compareTo)
                .orElse(0L);
        user.setId(maxId + 1);
        user.setCreateTime(LocalDateTime.now());
        // ✅ 只有当status为null时，才设置默认值1

        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        return userTable.add(user);
    }

    @Override
    public List<User> selectAll() {
        // 返回新集合，防止外部修改内部数据
        return new ArrayList<>(userTable);
    }


}