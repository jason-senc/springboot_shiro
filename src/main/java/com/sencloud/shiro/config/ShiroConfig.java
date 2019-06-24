package com.sencloud.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 13:38 2019/6/19
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        System.out.println("执行shiroFilterFactoryBean");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置shiroFilterFactoryBean
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 需要登录的接口，如果访问某个接口，需要登录却没登录，则调用此接口(如果不是前后台分离，则跳转页面)
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

        // 登录成功，跳转url，如果前后台分离则没这个调用
        shiroFilterFactoryBean.setSuccessUrl("/");

        // 没有权限，未授权就会调用此方法， 先验证登录--》 在验证是否有权限
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permit");


        //设置自定义filter
        Map<String,Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("roleOrFilter",new CustomRolesOrAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        // 拦截器路径，坑一： 部分路径无法拦截，时有时无，因为使用hashmap，无序的。
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 退出过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        // 匿名可以访问，也就是游客模式
        filterChainDefinitionMap.put("/pub/**", "anon");
        // 登录用户才可以访问
        filterChainDefinitionMap.put("/authc/**", "authc");
        // 管理员才可以访问
        filterChainDefinitionMap.put("/admin/**", "roleOrFilter[editor,admin]");
        // 有编辑权限才可以访问
        filterChainDefinitionMap.put("/video/update", "perms[video_update]");

        // 坑二： 过滤器链是顺序执行，从上到下，一般将/**  放到最下面

        // authc: url必须通过认证才可以访问
        // anon : url 可以匿名访问
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义SecurityManager
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 如果不是前后台分离，则不必设置下面的sessionManager
        securityManager.setSessionManager(sessionManager());

        // 使用自定义的cacheManager
        securityManager.setCacheManager(redisCacheManager());

        // 设置realm（推荐放在最后，不然某些情况会不生效）
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * 自定义realm
     * @return
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());

        return customRealm;
    }

    /**
     * 密码加解密规则
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置散列算法，这里使用的事MD5算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列次数，好比散列两次，相当于md5(md5(xxxx))
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 自定义SessionManager
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        // 超时时间，默认30分钟，会话超时；单位为毫秒
        // customSessionManager.setGlobalSessionTimeout(20000);

        // 配置session持久化
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }

    /**
     * 配置redisManager
     *
     */
    public RedisManager getRedisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.1.190");
        redisManager.setPort(6379);
        return redisManager;
    }

    /**
     * 配置redisCacheManager
     * @return
     */
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getRedisManager());

        // 设置过期时间，单位为秒 ，也可以在权限更新时删掉
        redisCacheManager.setExpire(20);
        return redisCacheManager;
    }

    /**
     * 自定义session持久化
     * @return
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());

        // 设置sessionid生成器
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());

        return redisSessionDAO;
    }

    /**
     * 管理shiro一些bean的生命周期 即bean初始化 与销毁
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * api controller层面
     * 加入注解的使用，不加入这个AOP注解不生效(shiro的注解 例如 @RequiresGuest)
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 用来扫描上下文寻找所有的Advistor(通知器), 将符合条件的Advisor应用到切入点的Bean中，
     * 需要在LifecycleBeanPostProcessor创建后才可以创建
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
