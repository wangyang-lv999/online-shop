package com.wy.shop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wy.shop.common.entity.User; // 引用公共模块的用户实体类
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 前台用户DAO层接口：定义数据库操作的方法
 * 只负责定义数据库操作，不写业务逻辑，SQL写在对应的XML文件里
 */
// @Mapper：MyBatis核心注解，必须加
// 作用：告诉MyBatis这是Mapper接口，启动时自动生成动态代理实现类，交给Spring管理
@Mapper
public interface WebUserMapper extends BaseMapper<User> {

    /**
     * 根据用户ID查询用户信息
     * @Param：给参数起别名，XML中通过#{userId}就能获取这个参数的值
     * 多个参数必须加@Param，单个参数建议加，提升代码可读性
     */
    User selectUserById(@Param("userId") Long userId);

    /**
     * 查询所有启用的用户（前台只展示状态为1的用户）
     * 返回值：List<User>，查询到的所有用户集合
     */
    List<User> selectAllActiveUser();

    /**
     * 新增用户（用户注册）
     * @param user 包含用户信息的实体类对象
     * @return int类型：受影响的行数，新增成功返回1，失败返回0
     */
    int insertUser(User user);

    /**
     * 根据用户名查询用户（注册时判断用户名是否重复）
     */
    User selectUserByUsername(@Param("username") String username);
}