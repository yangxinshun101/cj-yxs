package com.yxs.auth.application.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.google.common.base.Preconditions;
import com.yxs.auth.application.convert.AuthUserConvert;
import com.yxs.auth.application.entity.AuthUserDTO;
import com.yxs.auth.common.entity.Result;
import com.yxs.auth.domain.entity.AuthUserBO;
import com.yxs.auth.domain.service.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录测试 
 */
@RestController
@Slf4j
@RequestMapping("/user/")
public class UserController {

    @Resource
    private UserDomainService userDomainService;

    /**
     * 用户登录接口
     * @param validCode
     * @return
     */
    @RequestMapping("doLogin")
    public Result<SaTokenInfo> doLogin(String validCode) {

        try {
            Preconditions.checkNotNull(validCode, "验证码不能为空");

            //从根据验证码去查询OpenId
            return Result.ok(userDomainService.doLogin(validCode));
        }catch (Exception e){
            log.error("UserController.doLogin.error:{}", e.getMessage(), e);
            return Result.fail("登录失败");
        }


    }

    /**
     * 注册用户
     * @param authUserDTO
     * @return
     */
    @PostMapping("register")
    public Result register(AuthUserDTO authUserDTO){

        try {
            Preconditions.checkNotNull(authUserDTO.getUserName(), "用户名openId不能为空");

            AuthUserBO authUserBO = AuthUserConvert.INSTANCE.authUserDTOToBO(authUserDTO);

            return Result.ok(userDomainService.register(authUserBO));
        }catch (Exception e){
            log.error("UserController.register.error:{}", e.getMessage(), e);
            return Result.fail("注册失败");
        }

    }




    // 查询登录状态  ---- http://localhost:8081/acc/isLogin
    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }
    
    // 查询 Token 信息  ---- http://localhost:8081/acc/tokenInfo
    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }
    
    // 测试注销  ---- http://localhost:8081/acc/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }


}
