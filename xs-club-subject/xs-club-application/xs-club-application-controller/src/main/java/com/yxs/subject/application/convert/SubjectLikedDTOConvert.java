package com.yxs.subject.application.convert;

import com.yxs.subject.application.dto.SubjectLikedDTO;
import com.yxs.subject.domain.entity.SubjectLikedBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectLikedDTOConvert {

    SubjectLikedDTOConvert INSTANCE = Mappers.getMapper(SubjectLikedDTOConvert.class);


    SubjectLikedBO convertDTOToBO(SubjectLikedDTO subjectLikedDTO);
}
