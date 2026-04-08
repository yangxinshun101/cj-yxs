package com.yxs.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yxs.gateway.entity.AuthPermission;
import com.yxs.gateway.entity.AuthRole;
import com.yxs.gateway.redis.RedisUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisUtils redisUtil;
    private String authPermissionPrefix = "auth.permission";
    private String authRolePrefix = "auth.role";

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String key = redisUtil.buildKey(authPermissionPrefix, loginId.toString());
        String value = redisUtil.get(key);

        if (StringUtils.isBlank(value)) {
            return Collections.emptyList();
        }

        //如果不是空，则进行反序列化，反回一个对象数组
        List<AuthPermission> authPermissionList = new Gson().fromJson(value, new TypeToken<List<AuthPermission>>() {
        }.getType());

        // 返回此 loginId 拥有的权限列表
        return authPermissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());

    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        String key = redisUtil.buildKey(authRolePrefix, loginId.toString());
        String value = redisUtil.get(key);

        if (StringUtils.isBlank(value)) {
            return Collections.emptyList();
        }
        List<AuthRole> authRoleList = new Gson().fromJson(value, new TypeToken<List<AuthRole>>() {
        }.getType());

        //如果不是空，则进行反序列化，反回一个对象数组
        return authRoleList.stream().map(AuthRole::getRoleKey).collect(Collectors.toList());

    }

}

