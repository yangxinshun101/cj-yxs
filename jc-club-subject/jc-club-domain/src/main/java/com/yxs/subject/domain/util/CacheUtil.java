package com.yxs.subject.domain.util;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class CacheUtil<K, V> {

    private Cache<String,String> cache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build();


    //根据传进来的key去查缓存中是否有值，如果有吐出去，如果没有则执行functional接口去获取值并放入缓存
    //先写一个List的缓存
    public List<V> getResult(String key, Class<V> clazz, Function<String,List<V>> functional){
        String ifPresent = cache.getIfPresent(key);
        List<V> list = new ArrayList<>();
        if (!StringUtils.isBlank(ifPresent)){
            list = JSON.parseArray(ifPresent, clazz);
            return list;
        }else {
        list  = functional.apply(key);
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        cache.put(key,JSON.toJSONString(list));
        }
        return list;
    }


}
