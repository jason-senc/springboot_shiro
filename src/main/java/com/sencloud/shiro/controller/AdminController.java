package com.sencloud.shiro.controller;

import com.sencloud.shiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:03 2019/6/19
 */
@RestController
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping("/video/order")
    public JsonData findMyPayRecord(){
        Map<String,String> recordMap = new HashMap<>();
        recordMap.put("Mysql零基础入门到实战","10元");
        recordMap.put("Redis高并发高可用集群","20元");
        recordMap.put("Zookeeper + dubbo视频教程","30元");
        recordMap.put("微服务springcloud 实战","40元");
        return JsonData.buildSuccess(recordMap);
    }

}
