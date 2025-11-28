package com.yxs.subject.domain.handler.suject;

import com.yxs.subject.common.enums.SubjectInfoTypeEnum;
import com.yxs.subject.domain.entity.SubjectInfoBO;
import com.yxs.subject.domain.entity.SubjectOptionBO;

public interface SubjectTypeHandler {

    //先根据类型code拿到对应的处理器
    SubjectInfoTypeEnum getHandlerType();

    //添加实际的题目
    void add(SubjectInfoBO subjectInfoBO);


    SubjectOptionBO query(int i);
}
