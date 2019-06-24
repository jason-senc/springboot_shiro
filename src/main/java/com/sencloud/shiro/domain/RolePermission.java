package com.sencloud.shiro.domain;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:45 2019/6/18
 */
public class RolePermission {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
