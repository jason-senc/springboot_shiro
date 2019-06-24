package com.sencloud.shiro.dao;

import com.sencloud.shiro.domain.Role;
import com.sencloud.shiro.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:47 2019/6/18
 */
public interface RoleMapper {

    @Select("select r.id as id, r.name as name ,r.description as description from user_role ur " +
            "left join role r on ur.role_id = r.id where ur.user_id= #{userId}")
    @Results(
        value = {
                @Result(id = true,column = "id",property = "id"),
                @Result(column = "name",property = "name"),
                @Result(column = "description",property = "description"),
                @Result(column = "id",property = "permissionList",
                        many = @Many(select = "com.sencloud.shiro.dao.PermissionMapper.findPermissionListByRoleId",fetchType = FetchType.DEFAULT))

        }

    )
    List<Role> findRoleListByUserId(@Param("userId") Integer userId);
}
