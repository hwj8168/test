package com.hwj.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.UUID;

/**
 * @Auther: hwj
 * @Date: 2018/12/14 10:00
 * @Description:
 */
public class TokenUtil {
    /**
     * @Description: token的生成方式：1.先得到uuid，再将uuid转为base54编码
     */
    public static String getToken() {
        //得到uuid
        String uid = UUID.randomUUID().toString();
        //将uuid转为base64编码格式
        byte[] b = Base64.encodeBase64(uid.getBytes());
        String result = new String(b);
        return result;
    }
}