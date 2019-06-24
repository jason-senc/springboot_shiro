package com.sencloud.shiro.controller;

import com.sencloud.shiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 16:11 2019/6/19
 */
@RestController
@RequestMapping("/authc")
public class OrderController {

    @RequestMapping("/video/play_record")
    public JsonData findMyPayRecord(){
        Map<String,String> recordMap = new HashMap<>();
        recordMap.put("Mysql零基础入门到实战","第1章第2集");
        recordMap.put("Redis高并发高可用集群","第2章第3集");
        recordMap.put("Zookeeper + dubbo视频教程","第3章第12集");
        recordMap.put("微服务springcloud 实战","第4章第2集");
        return JsonData.buildSuccess(recordMap);
    }

}
