package com.yxs.practice.service.handler;

import com.yxs.practice.api.vo.PracticeSubjectOptionVO;
import com.yxs.practice.api.vo.PracticeSubjectVO;
import com.yxs.practice.service.dao.SubjectMultipleDao;
import com.yxs.practice.service.dao.SubjectRadioDao;
import com.yxs.practice.service.entity.po.SubjectMultiplePO;
import com.yxs.practice.service.entity.po.SubjectPO;
import com.yxs.practice.service.entity.po.SubjectRadioPO;
import com.yxs.practice.service.enums.PracticeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component
public class PracticeMultipleHandler implements PracticeHandler {

    @Resource
    private SubjectMultipleDao subjectMultipleDao;

    @Override
    public PracticeEnum getPracticeEnum() {
        return PracticeEnum.PRACTICE_MULTIPLE;
    }

    @Override
    public PracticeSubjectVO add(PracticeSubjectVO practiceSubjectVO, SubjectPO dto) {
        List<PracticeSubjectOptionVO> optionList = new LinkedList<>();
        List<SubjectMultiplePO> multipleSubjectPOS = subjectMultipleDao.selectBySubjectId(dto.getId());
        multipleSubjectPOS.forEach(e -> {
            PracticeSubjectOptionVO practiceSubjectOptionVO = new PracticeSubjectOptionVO();
            practiceSubjectOptionVO.setOptionContent(e.getOptionContent());
            practiceSubjectOptionVO.setOptionType(e.getOptionType());
            optionList.add(practiceSubjectOptionVO);
        });
        practiceSubjectVO.setOptionList(optionList);
        return null;
    }
}
