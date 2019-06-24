package com.sencloud.shiro.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 18:01 2019/6/20
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {

    @Override

    public Serializable generateId(Session session) {
        return "sencloud"+UUID.randomUUID().toString().replace("-","");
    }
}
