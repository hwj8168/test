package com.hwj.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: hwj
 * @Date: 2018/12/13 18:05
 * @Description:
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Description: 将token存到redis中
     */
    public boolean set(String key, Object value) {
        try {
            //设置过期时间为30分钟
            redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @Description:取出token 如果存在则将过期时间取出加10分钟
     */
    public Object get(String key) {
        if (redisTemplate.opsForValue().get(key) != null) {
            //如果token存在 则过期时间增加10分钟
            //得到剩余存活时间（分钟）
            long time = redisTemplate.getExpire(key) / 60;
            //设置新的过期时间
            redisTemplate.expire(key, time + 10, TimeUnit.MINUTES);
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }
}