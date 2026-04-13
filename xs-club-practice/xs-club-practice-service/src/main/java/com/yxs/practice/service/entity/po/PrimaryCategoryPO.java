package com.yxs.practice.service.entity.po;

import lombok.Data;

@Data
public class PrimaryCategoryPO {

    private Integer id;

    private String categoryName;

    private Integer categoryType;

    private Integer parentId;

}
