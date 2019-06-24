package com.sencloud.shiro.dao;

import com.sencloud.shiro.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:47 2019/6/18
 */
public interface UserMapper {

    @Select("select * from user where username= #{username}")
    User findByUsername(String username);

    @Select("select * from user where id= #{userId}")
    User findById(@Param("userId") Integer id);

    @Select("select * from user where username= #{username} and password = #{password}")
    User findByUsernameAndPwd(@Param("username") String username,@Param("password") String password);
}
