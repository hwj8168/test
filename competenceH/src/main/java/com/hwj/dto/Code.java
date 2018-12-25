package com.hwj.dto;

public enum Code {
    SUCCESS(1, "SUCCESS"),
    Fail(0,"FAIL");
    private int code;
    String message;
    Code(int code,String message){
        this.code=code;
        this.message=message;
    }

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
}
