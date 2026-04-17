package com.yxs.practice.service.dao;

import com.yxs.practice.service.entity.dto.PracticeSubjectDTO;
import com.yxs.practice.service.entity.po.SubjectPO;

import java.util.List;

public interface SubjectDao {


    /**
     * 获取练习面试题目
     */
    List<SubjectPO> getPracticeSubject(PracticeSubjectDTO dto);

    SubjectPO selectById(Long subjectId);


}