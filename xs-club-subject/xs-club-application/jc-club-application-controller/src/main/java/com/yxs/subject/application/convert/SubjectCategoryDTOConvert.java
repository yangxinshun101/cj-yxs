package com.yxs.subject.application.convert;

import com.yxs.subject.application.dto.SubjectCategoryDTO;
import com.yxs.subject.application.dto.SubjectLabelDTO;
import com.yxs.subject.domain.entity.SubjectCategoryBO;
import com.yxs.subject.domain.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryDTOConvert {

    SubjectCategoryDTOConvert INSTANCE = Mappers.getMapper(SubjectCategoryDTOConvert.class);

    SubjectCategoryBO subjectCategoryDTOToBO (SubjectCategoryDTO subjectCategoryDTO);

    List<SubjectCategoryDTO> convertCategoryBOListToDTOList(List<SubjectCategoryBO> subjectCategoryBOList);

    SubjectCategoryDTO convertBoToCategoryDTO(SubjectCategoryBO bo);

}
