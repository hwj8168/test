package com.hwj.dto;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {
    public int code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ResponseData putDataValue(String key, Object value) {
        data.put(key, value);
        return this;
    }
}
