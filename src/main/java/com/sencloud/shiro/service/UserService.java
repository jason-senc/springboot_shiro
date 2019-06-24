package com.sencloud.shiro.service;

import com.sencloud.shiro.domain.User;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 10:01 2019/6/19
 */
public interface UserService {
    /**
     * 获取全部用户信息 包括角色 权限
     * @param username
     * @return
     */
    User findAllUserInfoByUsrename(String username);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    User findSimpleUserInfoById(Integer id);

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User findSimpleUserInfoByUsername(String username);


}
