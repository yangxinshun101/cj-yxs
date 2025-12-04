package com.yxs.auth.domain.service.Impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yxs.auth.domain.redis.RedisConfig;
import com.yxs.auth.domain.redis.RedisUtils;
import com.yxs.auth.domain.service.PermissionDomainService;
import com.yxs.auth.infra.basic.entity.AuthPermission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionDomainServiceImpl implements PermissionDomainService {

    @Resource
    private RedisUtils redisUtils;

    private String rolePermissionPrefix = "auth.permission";

    @Override
    public List<String> getPermission(String userName) {
        String key = redisUtils.buildKey(rolePermissionPrefix, userName);
        String rolePermissionValue = redisUtils.get(key);
        log.info("PermissionDomainServiceImpl.getPermission.key:{},value:{}", key, rolePermissionValue);

        if (StringUtils.isBlank(rolePermissionValue)) {
            return Collections.emptyList();
        }

        List<AuthPermission> authPermissionList = new Gson().fromJson(rolePermissionValue,
                new TypeToken<List<AuthPermission>>() {
                }.getType());


        return authPermissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());

    }
}
