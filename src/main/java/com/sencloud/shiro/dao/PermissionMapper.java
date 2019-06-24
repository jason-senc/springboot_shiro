package com.sencloud.shiro.dao;

import com.sencloud.shiro.domain.Permission;
import com.sencloud.shiro.domain.Role;
import com.sencloud.shiro.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:47 2019/6/18
 */
public interface PermissionMapper {

    @Select("select p.id as id ,p.name as name,p.url as url from role_permission rp " +
            "left join permission p on rp.permission_id = p.id where rp.role_id= #{roleId}")
    List<Permission> findPermissionListByRoleId(@Param("roleId") Integer roleId);

}
