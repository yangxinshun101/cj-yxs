package com.yxs.subject.domain.service;

import com.yxs.subject.domain.entity.SubjectLabelBO;

import java.util.List;

public interface SubjectLabelDomainService {

    /**
     * 新增题目分类
     * @param subjectLabelBO
     */
    Boolean add (SubjectLabelBO subjectLabelBO);

    /**
     * 删除题目分类（逻辑删除）
     * @param subjectLabelBO
     * @return
     */
    Boolean delete(SubjectLabelBO subjectLabelBO);

    /**
     * 更新题目分类
     * @param subjectLabelBO
     * @return
     */
    Boolean update(SubjectLabelBO subjectLabelBO);


    /**
     * 根据分类id（categoryId）查询标签
     * @param subjectLabelBO
     * @return
     */
    List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO);
}
