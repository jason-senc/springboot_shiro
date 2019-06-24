package com.sencloud.shiro.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * @Author: jason
 * @Description: 自定义filter
 * @Date: Created in 11:24 2019/6/20
 */
public class CustomRolesOrAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        // 获取当前访问路径所需要的角色集合
        String[] rolesArray = (String[]) mappedValue;
        // 没有角色限制可以直接访问
        if(rolesArray == null || rolesArray.length == 0){
            return true;
        }
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        // 当前subject是roles中的任意一个，则有权限访问
        for (String role : roles) {
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
