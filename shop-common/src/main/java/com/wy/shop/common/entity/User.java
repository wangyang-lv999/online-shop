package com.wy.shop.common.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户公共实体类：和数据库sys_user表一一对应
 * 放在公共模块，前台和后台都能直接用
 */
// 自动生成get、set、toString等方法，不用手动写
@Data
// 自动生成无参构造方法
@NoArgsConstructor
// 自动生成全参构造方法
@AllArgsConstructor
// 【MP注解】标记对应数据库表名，因为配置了table-prefix: sys_，这里只需要写user即可
//@TableName("user") //自定义表名，覆盖全局配置
@TableName
public class User {
    /**
     * 用户ID，对应数据库的user_id字段，主键自增
     */
    // 【MP注解】标记主键字段，指定主键自增策略
    @TableId(type = IdType.AUTO)
    //注意：如果数据库里的字段名就是 user_id，MP 的自动驼峰转换会识别它。但如果数据库里字段名是 id，而实体类叫 userId，则需要写成 @TableId(value = "id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名，对应数据库的username字段，唯一
     */
    private String username;

    /**
     * 密码，对应数据库的password字段
     */
    private String password;

    /**
     * 昵称，对应数据库的nickname字段
     */
    private String nickname;

    /**
     * 年龄，对应数据库的age字段
     */
    private Integer age;

    /**
     * 邮箱，对应数据库的email字段
     */
    private String email;

    /**
     * 状态：1=启用，0=禁用，对应数据库的status字段
     */
    private Integer status;

    /**
     * 创建时间，对应数据库的create_time字段
     */
    private LocalDateTime createTime;

    /**
     * 更新时间，对应数据库的update_time字段
     */
    private LocalDateTime updateTime;
}
