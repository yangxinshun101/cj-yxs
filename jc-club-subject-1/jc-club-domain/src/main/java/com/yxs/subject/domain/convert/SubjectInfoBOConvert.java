package com.yxs.subject.domain.convert;


import com.yxs.subject.domain.entity.SubjectInfoBO;
import com.yxs.subject.domain.entity.SubjectOptionBO;
import com.yxs.subject.infra.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoBOConvert {

    SubjectInfoBOConvert INSTANCE = Mappers.getMapper(SubjectInfoBOConvert.class);

    SubjectInfo convertBoToInfo(SubjectInfoBO subjectInfoBO);

    SubjectInfoBO convertOptionToBo(SubjectOptionBO subjectOptionBO);

    SubjectInfoBO convertOptionAndInfoToBo(SubjectOptionBO subjectOptionBO,SubjectInfo subjectInfo);

    List<SubjectInfoBO> convertListInfoToBO(List<SubjectInfo> subjectInfoList);

}
