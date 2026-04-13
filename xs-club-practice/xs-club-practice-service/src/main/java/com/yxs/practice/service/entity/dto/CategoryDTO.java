package com.yxs.practice.service.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    private Integer id;

    private List<Integer> subjectTypeList;

    private String categoryName;

    private Integer categoryType;

    private Integer parentId;
}
