package com.yxs.subject.domain.redis;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public String buildKey(String... key) {

        StringBuilder builderKey = new StringBuilder();

        for (String k : key) {
            builderKey.append(k).append(".");
        }
        return builderKey.toString();
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }


    public void putHash(String subjectLikedKey, String hashKey, Object status) {
        redisTemplate.opsForHash().put(subjectLikedKey, hashKey, status);
    }

    public void increment(String key, Integer count) {
        redisTemplate.opsForValue().increment(key, count);

    }

    public Integer getInt(String countKey) {
        return (Integer) redisTemplate.opsForValue().get(countKey);
    }

    public Boolean exist(String likedDetailKey) {
        return redisTemplate.hasKey(likedDetailKey);
    }

    public Map<Object, Object> getHashAndDelete(String subjectLabeledKey) {
        Map<Object, Object> entries = new HashMap<>();
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(subjectLabeledKey, ScanOptions.NONE);
        while(scan.hasNext()){
            Map.Entry<Object, Object> entry = scan.next();
            entries.put(entry.getKey(), entry.getValue());
            redisTemplate.opsForHash().delete(subjectLabeledKey,entry.getKey());
        }
        return entries;
    }
}
