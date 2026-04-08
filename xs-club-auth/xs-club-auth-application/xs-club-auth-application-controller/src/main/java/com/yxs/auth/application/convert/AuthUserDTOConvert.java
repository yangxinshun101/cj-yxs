package com.yxs.auth.application.convert;


import com.yxs.auth.application.entity.AuthUserDTO;
import com.yxs.auth.domain.entity.AuthUserBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserDTOConvert {

    AuthUserDTOConvert INSTANCE = Mappers.getMapper(AuthUserDTOConvert.class);

     AuthUserBO authUserDTOToBO(AuthUserDTO authUserDTO);

    AuthUserDTO convertBOToDTO(AuthUserBO userInfo);
}
