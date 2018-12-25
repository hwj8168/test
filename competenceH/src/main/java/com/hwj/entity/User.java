package com.hwj.entity;

import java.util.List;

/**
 * @Auther: hwj
 * @Date: 2018/12/11 14:43
 * @Description:
 */
public class User {
    private int id;
    private String email;
    private String password;
    private String type;
    private String isValid;
    private String salt;
    private List<Resource> resourceList;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", isValid='" + isValid + '\'' +
                ", salt='" + salt + '\'' +
                ", resourceList=" + resourceList +
                '}';
    }
}