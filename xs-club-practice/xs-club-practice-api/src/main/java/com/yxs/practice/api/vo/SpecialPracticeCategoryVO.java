package com.yxs.practice.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpecialPracticeCategoryVO {

    private String categoryName;

    private Long categoryId;

    private List<SpecialPracticeLabelVO> labelList;
}
