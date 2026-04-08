package com.yxs.auth.application.context;

import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginContextHandler {
    public static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void set(String key, Object value) {
        getThreadMap().put(key, value);
    }

    public static Object get(String key) {
        return getThreadMap().get(key);
    }

    public static String getLoginId(){
        return (String) get("loginId");
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }

    public static Map<String, Object> getThreadMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (ObjectUtils.isEmpty(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }
}
