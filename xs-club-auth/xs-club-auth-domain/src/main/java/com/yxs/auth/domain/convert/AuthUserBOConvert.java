package com.yxs.auth.domain.convert;


import com.yxs.auth.domain.entity.AuthUserBO;
import com.yxs.auth.infra.basic.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserBOConvert {

    AuthUserBOConvert INSTANCE = Mappers.getMapper(AuthUserBOConvert.class);

     AuthUser authUserBOToEntity(AuthUserBO authUserBO);

    AuthUserBO convertEntityToBO(AuthUser authUser);
}
