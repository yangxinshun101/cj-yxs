package com.yxs.subject.domain.service;

import com.yxs.subject.domain.entity.SubjectCategoryBO;

import java.util.List;

public interface SubjectCategoryDomainService {

    /**
     * 新增题目分类
     * @param subjectCategoryBO
     */
    void add (SubjectCategoryBO subjectCategoryBO);

    List<SubjectCategoryBO> queryCategoryList(SubjectCategoryBO subjectCategoryBO);

    Boolean update(SubjectCategoryBO subjectCategoryBO);

    Boolean delete(SubjectCategoryBO subjectCategoryBO);
}
