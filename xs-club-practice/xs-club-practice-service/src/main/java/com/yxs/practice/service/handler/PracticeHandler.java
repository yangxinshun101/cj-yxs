package com.yxs.practice.service.handler;

import com.yxs.practice.api.vo.PracticeSubjectVO;
import com.yxs.practice.service.entity.dto.PracticeSubjectDTO;
import com.yxs.practice.service.entity.po.SubjectPO;
import com.yxs.practice.service.enums.PracticeEnum;

public interface PracticeHandler {
    PracticeEnum getPracticeEnum();
    PracticeSubjectVO add(PracticeSubjectVO practiceSubjectVO, SubjectPO dto);
}
