package com.yxs.auth.domain.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.yxs.auth.domain.entity.AuthUserBO;

public interface UserDomainService {
    SaTokenInfo doLogin(String validCode);

    Boolean register(AuthUserBO authUserBO);
}
