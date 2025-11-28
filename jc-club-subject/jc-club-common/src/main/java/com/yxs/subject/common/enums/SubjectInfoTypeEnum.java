package com.yxs.subject.common.enums;

import lombok.Getter;

/**
 * 题目枚举类
 */
@Getter
public enum SubjectInfoTypeEnum {

    RADIO(1,"单选"),
    MULTIPLE(2,"多选"),
    JUDGE(3,"判断"),
    BRIEF(4,"简答");

    public int code;
    public String SubjectType;


    SubjectInfoTypeEnum(int code, String subjectType){
        this.code = code;
        this.SubjectType = subjectType;
    }

    public static SubjectInfoTypeEnum getByCode(int code){
        for (SubjectInfoTypeEnum value : SubjectInfoTypeEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }
}
