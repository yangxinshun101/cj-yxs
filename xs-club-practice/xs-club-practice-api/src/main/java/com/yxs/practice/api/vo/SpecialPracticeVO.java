package com.yxs.practice.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpecialPracticeVO {

    private String primaryCategoryName;

    private Long primaryCategoryId;

    private List<SpecialPracticeCategoryVO> categoryList;

}
