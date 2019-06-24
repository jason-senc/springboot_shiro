package com.sencloud.shiro.controller;

import com.sencloud.shiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:14 2019/6/19
 */
@RestController
@RequestMapping("video")
public class VideoController {

    @RequestMapping("update")
    public JsonData updateVideo(){
        return JsonData.buildSuccess("video更新成功");
    }
}
