package com.yxs.subject.common.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginContextHolder {

    private static final InheritableThreadLocal<Map<String,Object>> THREAD_LOCAL
            = new InheritableThreadLocal<>();

    public static void set(String key, Object value){
        Map<String, Object> threadLocal = getThreadLocal();
        threadLocal.put(key, value);
    }

    public static Map<String, Object> getThreadLocal(){
        Map<String, Object> context = THREAD_LOCAL.get();
        if(context == null){
            context = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(context);
        }
        return context;
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
