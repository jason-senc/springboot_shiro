package com.sencloud.shiro.domain;

import java.io.Serializable;

/**
 * @Author: jason
 * @Description:
 * @Date: Created in 17:43 2019/6/18
 */
public class Permission implements Serializable {

    private Integer id;
    private String name;
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
