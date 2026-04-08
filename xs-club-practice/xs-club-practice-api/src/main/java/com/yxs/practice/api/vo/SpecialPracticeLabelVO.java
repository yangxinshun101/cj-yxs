package com.yxs.practice.api.vo;

import lombok.Data;

@Data
public class SpecialPracticeLabelVO {
    private Long id;

    /**
     * 分类id-标签ID
     */
    private String assembleId;

    private String labelName;
}
