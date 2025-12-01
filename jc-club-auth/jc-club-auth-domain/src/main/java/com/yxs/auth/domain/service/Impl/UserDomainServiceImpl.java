package com.yxs.auth.domain.service.Impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.gson.Gson;
import com.yxs.auth.common.enums.AuthUserStatusEnum;
import com.yxs.auth.common.enums.IsDeletedFlagEnum;
import com.yxs.auth.domain.constant.AuthConstant;
import com.yxs.auth.domain.entity.AuthUserBO;
import com.yxs.auth.domain.service.UserDomainService;
import com.yxs.auth.infra.basic.entity.*;
import com.yxs.auth.infra.basic.service.*;
import com.yxs.auth.domain.redis.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDomainServiceImpl implements UserDomainService {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    @Resource
    private AuthPermissionService authPermissionService;

    private String LOGIN_PREFIX = "loginCode";

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    @Override
    public SaTokenInfo doLogin(String validCode) {
        String loginKey = redisUtils.buildKey(LOGIN_PREFIX, validCode);
        String openId = redisUtils.get(loginKey);

        if (openId.isEmpty()) {
            return null;
        }

        //调用注册流程
        AuthUserBO authUserBO = new AuthUserBO();
        authUserBO.setUserName(openId);
        Boolean register = register(authUserBO);

        StpUtil.login(openId);

        return StpUtil.getTokenInfo();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {


        AuthUser authUser = new AuthUser();
        authUser.setUserName(authUserBO.getUserName());
        //为防止多次注册，先根据传入的username查一下数据库
        List<AuthUser> authUsers = authUserService.queryByCondition(authUser);
        if (authUsers.size() > 0) {
            return null;
        }
        if (StringUtils.isBlank(authUser.getAvatar())) {
            authUser.setAvatar("http://117.72.10.84:9000/user/icon/微信图片_20231203153718(1).png");
        }
        if (StringUtils.isBlank(authUser.getNickName())){
            authUser.setNickName("ikun");

        }
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());
        AuthUser insert = authUserService.insert(authUser);

        //添加用户角色
        AuthRole authRole = new AuthRole();
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        AuthRole roleResult = authRoleService.queryByCondition(authRole);
        Long roleId = roleResult.getId();
        Long userId = authUser.getId();
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(userId);
        authUserRole.setRoleId(roleId);
        authUserRole.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());
        authUserRoleService.insert(authUserRole);

        String roleKey = redisUtils.buildKey(authRolePrefix, authUser.getUserName());
        List<AuthRole> roleList = new LinkedList<>();
        roleList.add(authRole);
        redisUtils.set(roleKey, new Gson().toJson(roleList));

        AuthRolePermission authRolePermission = new AuthRolePermission();
        authRolePermission.setRoleId(roleId);
        List<AuthRolePermission> rolePermissionList = authRolePermissionService.
                queryByCondition(authRolePermission);

        List<Long> permissionIdList = rolePermissionList.stream()
                .map(AuthRolePermission::getPermissionId).collect(Collectors.toList());
        //根据roleId查权限
        List<AuthPermission> permissionList = authPermissionService.queryByRoleList(permissionIdList);
        String permissionKey = redisUtils.buildKey(authPermissionPrefix, authUser.getUserName());
        redisUtils.set(permissionKey, new Gson().toJson(permissionList));

        return Objects.isNull( insert);

    }
}
