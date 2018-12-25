package com.hwj.entity;

/**
 * @Auther: hwj
 * @Date: 2018/12/11 14:43
 * @Description:
 */
public class Resource {
    private int id;
    private String name;
    private String type;
    private String url;
    private String nation;
    private String isValid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", nation='" + nation + '\'' +
                ", isValid='" + isValid + '\'' +
                '}';
    }
}