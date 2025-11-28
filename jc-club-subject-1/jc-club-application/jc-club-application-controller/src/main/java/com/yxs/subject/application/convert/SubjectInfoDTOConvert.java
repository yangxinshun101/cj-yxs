package com.yxs.subject.application.convert;

import com.yxs.subject.application.dto.SubjectAnswerDTO;
import com.yxs.subject.application.dto.SubjectInfoDTO;
import com.yxs.subject.domain.entity.SubjectAnswerBO;
import com.yxs.subject.domain.entity.SubjectInfoBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoDTOConvert {

    SubjectInfoDTOConvert INSTANCE = Mappers.getMapper(SubjectInfoDTOConvert.class);


    SubjectInfoBO convertDTOToBO(SubjectInfoDTO subjectInfoDTO);

    List<SubjectAnswerBO> convertListDTOToBO(List<SubjectAnswerDTO> optionList);

    SubjectInfoDTO convertBOToDTO(SubjectInfoBO boResult);
}
