package com.yxs.subject.domain.service;

import com.yxs.subject.common.entity.PageResult;
import com.yxs.subject.domain.entity.SubjectInfoBO;

public interface SubjectInfoDomainService {

    void add(SubjectInfoBO subjectInfoBO);

    PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO);

    SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO);
}
