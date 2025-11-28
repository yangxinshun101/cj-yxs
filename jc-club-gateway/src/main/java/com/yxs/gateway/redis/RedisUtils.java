package com.yxs.gateway.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    public String buildKey(String ... key){

        StringBuilder builderKey = new StringBuilder();

        for (String k : key) {
            builderKey.append(k).append(".");
        }
        return builderKey.toString();
    }

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }


}
