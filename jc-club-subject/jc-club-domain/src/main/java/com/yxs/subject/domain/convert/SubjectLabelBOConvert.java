package com.yxs.subject.domain.convert;

import com.yxs.subject.domain.entity.SubjectCategoryBO;
import com.yxs.subject.domain.entity.SubjectLabelBO;
import com.yxs.subject.infra.basic.entity.SubjectCategory;
import com.yxs.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelBOConvert {
    SubjectLabelBOConvert INSTANCE = Mappers.getMapper(SubjectLabelBOConvert.class);

    SubjectLabel convertBOToLabel(SubjectLabelBO subjectLabelBO);
    
    SubjectLabelBO convertLabelToBO(SubjectLabel subjectLabel);


    List<SubjectLabelBO> convertLabelListToBOList(List<SubjectLabel> subjectLabelsList);
}