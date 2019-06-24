package com.sencloud.shiro.controller;


import com.sencloud.shiro.domain.JsonData;
import com.sencloud.shiro.domain.UserQuery;
import com.sencloud.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 10:14 2019/6/19
 */
@RestController
@RequestMapping("pub")
public class PublicController {

    @Autowired
    private UserService userService;

    @RequestMapping("/need_login")
    public JsonData needLogin(){
      return JsonData.buildSuccess("温馨提示:请使用和对应的账号登录",-2);
    }

    @RequestMapping("/not_permit")
    public JsonData notPermit(){
        return JsonData.buildSuccess("温馨提示:拒绝访问，没有权限",-3);
    }

    @RequestMapping("/index")
    public JsonData index(){
        List<String> videoList = new ArrayList<>();
        videoList.add("Mysql零基础入门到实战");
        videoList.add("Redis高并发高可用集群");
        videoList.add("Zookeeper + dubbo视频教程");
        videoList.add("微服务springcloud 实战");
        return JsonData.buildSuccess(videoList);
    }

    /**
     * 登录
     * @param userQuery
     * @return
     */
    @PostMapping("/login")
    public JsonData login(@RequestBody UserQuery userQuery){
        Subject subject = SecurityUtils.getSubject();
        Map<String,Object> userInfo = new HashMap<>();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userQuery.getName(),userQuery.getPassword());
            subject.login(token);
            userInfo.put("msg","登录成功");
            userInfo.put("session_id",subject.getSession().getId());
            return JsonData.buildSuccess(userInfo);
        }catch(Exception e){
            e.printStackTrace();
            return JsonData.buildError("账号或者密码错误");
        }



    }
}
