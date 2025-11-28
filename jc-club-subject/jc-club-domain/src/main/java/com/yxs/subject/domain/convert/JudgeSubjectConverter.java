package com.yxs.subject.domain.convert;

import com.yxs.subject.domain.entity.SubjectAnswerBO;
import com.yxs.subject.infra.basic.entity.SubjectJudge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JudgeSubjectConverter {

    JudgeSubjectConverter INSTANCE = Mappers.getMapper(JudgeSubjectConverter.class);
    
    SubjectAnswerBO convertEntityToBo(SubjectJudge subjectJudge);

    List<SubjectAnswerBO> convertEntityToBoList(List<SubjectJudge> subjectJudgeList);

}