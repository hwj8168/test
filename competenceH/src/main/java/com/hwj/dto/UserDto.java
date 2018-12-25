package com.hwj.dto;

import com.hwj.entity.Resource;

import java.util.List;

/**
 * @Auther: hwj
 * @Date: 2018/12/11 16:00
 * @Description:
 */
public class UserDto {
    private int id;
    private String email;
    private String type;
    private List<ResourceDto> resourceList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResourceDto> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourceDto> resourceList) {
        this.resourceList = resourceList;
    }
}