package com.yxs.subject.application.convert;

import com.yxs.subject.application.dto.SubjectLabelDTO;
import com.yxs.subject.domain.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelDTOConvert {

    SubjectLabelDTOConvert INSTANCE = Mappers.getMapper(SubjectLabelDTOConvert.class);

    SubjectLabelBO convertSubjectLableBOToDTO(SubjectLabelDTO subjectLableDTO);

    SubjectLabelDTO convertSubjectLableDTOToBO(SubjectLabelBO subjectLableBO);

    List<SubjectLabelDTO> convertBOToLabelDTOList(List<SubjectLabelBO> resultList);
}
