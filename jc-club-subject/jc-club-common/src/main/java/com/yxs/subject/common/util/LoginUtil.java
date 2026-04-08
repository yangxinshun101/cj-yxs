package com.yxs.subject.common.util;

import com.yxs.subject.common.context.LoginContextHolder;

public class LoginUtil {

    public static String getLoginId(){
        return LoginContextHolder.getThreadLocal().get("loginId").toString();
    }
}
