package com.yxs.practice.service.enums;

public enum PracticeEnum {
    PRACTICE_RADIO(1, "单选"),
    PRACTICE_MULTIPLE(2, "多选");

    private final int code;
    private final String description;

    PracticeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PracticeEnum valueOfCode(int code) {
        for (PracticeEnum practiceEnum : PracticeEnum.values()) {
            if (practiceEnum.code == code) {
                return practiceEnum;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
