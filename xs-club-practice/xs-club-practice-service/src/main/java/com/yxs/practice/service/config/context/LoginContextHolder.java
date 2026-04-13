package com.yxs.practice.service.config.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginContextHolder {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static Map<String, Object> getThreadLocalMap(){
        Map<String, Object> objectMap = THREAD_LOCAL.get();
        if (objectMap == null || ObjectUtils.isEmpty(objectMap)){
            objectMap = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(objectMap);
        }
        return objectMap;
    }

    public static void set(String key,  Object value){
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        threadLocalMap.put(key,value);
    }

    public static Object getLoginId(){
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        return threadLocalMap.get("loginId");
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
