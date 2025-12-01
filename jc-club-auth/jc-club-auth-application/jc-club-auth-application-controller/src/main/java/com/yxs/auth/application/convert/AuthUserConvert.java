package com.yxs.auth.application.convert;


import com.yxs.auth.application.entity.AuthUserDTO;
import com.yxs.auth.domain.entity.AuthUserBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserConvert {

    AuthUserConvert INSTANCE = Mappers.getMapper(AuthUserConvert.class);

     AuthUserBO authUserDTOToBO(AuthUserDTO authUserDTO);
}
