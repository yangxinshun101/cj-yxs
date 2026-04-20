package com.yxs.practice.service.service;

import com.yxs.practice.api.req.GetPracticeSubjectsReq;
import com.yxs.practice.api.vo.PracticeSetVO;
import com.yxs.practice.api.vo.PracticeSubjectListVO;
import com.yxs.practice.api.vo.PracticeSubjectVO;
import com.yxs.practice.api.vo.SpecialPracticeVO;
import com.yxs.practice.service.entity.dto.PracticeSubjectDTO;

import java.util.List;


public interface PracticeSetService {
    List<SpecialPracticeVO> getSpecialPracticeContent();

    PracticeSetVO addPractice(PracticeSubjectDTO practiceSubjectDTO);

    PracticeSubjectListVO getSubjects(GetPracticeSubjectsReq req);

    PracticeSubjectVO getPracticeSubject(PracticeSubjectDTO dto);
}
