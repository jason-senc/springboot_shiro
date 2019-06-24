package com.sencloud.shiro.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:40 2019/6/18
 */
public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private Date createTime;
    private String salt;

    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
