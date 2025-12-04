package com.yxs.auth.domain.service;


import com.yxs.auth.infra.basic.entity.AuthRole;

import java.util.List;

public interface PermissionDomainService {

    List<String> getPermission(String userName);
}
