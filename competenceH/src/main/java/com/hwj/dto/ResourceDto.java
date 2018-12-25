package com.hwj.dto;

/**
 * @Auther: hwj
 * @Date: 2018/12/14 15:49
 * @Description:
 */
public class ResourceDto {
    private String name;
    private String url;
    private String nation;

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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Override
    public String toString() {
        return "ResourceDto{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }
}