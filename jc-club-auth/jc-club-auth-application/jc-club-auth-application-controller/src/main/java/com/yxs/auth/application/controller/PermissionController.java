package com.yxs.auth.application.controller;

import com.google.common.base.Preconditions;
import com.yxs.auth.common.entity.Result;
import com.yxs.auth.domain.service.PermissionDomainService;
import com.yxs.auth.infra.basic.entity.AuthRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/permission/")
@Slf4j
public class PermissionController {

    @Resource
    private PermissionDomainService permissionDomainService;

    @RequestMapping("getPermission")
    public Result<AuthRole> getPermission(String userName) {
        try {
            if (log.isInfoEnabled()){
                log.info("PermissionController.getPermission.dto:{}",userName);
            }
            Preconditions.checkNotNull(userName, "openId不能为空");
            return Result.ok(permissionDomainService.getPermission(userName));

        }catch (Exception e){
            log.error("PermissionController.getPermission.error:{}", e.getMessage(), e);
            return Result.fail("获取权限失败");
        }
    }
}
