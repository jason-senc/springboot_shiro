package com.sencloud.shiro.service.impl;

import com.sencloud.shiro.dao.RoleMapper;
import com.sencloud.shiro.dao.UserMapper;
import com.sencloud.shiro.domain.Role;
import com.sencloud.shiro.domain.User;
import com.sencloud.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 10:04 2019/6/19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User findAllUserInfoByUsrename(String username) {

        // 业务方法里面加缓存，也可以

        User user = userMapper.findByUsername(username);
        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }

    @Override
    public User findSimpleUserInfoById(Integer userId) {
        return userMapper.findById(userId);
    }

    @Override
    public User findSimpleUserInfoByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
