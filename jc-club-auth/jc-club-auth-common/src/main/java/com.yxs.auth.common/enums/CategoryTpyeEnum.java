package com.yxs.auth.common.enums;

import lombok.Getter;

@Getter
public enum CategoryTpyeEnum {
    PARIMARY(1,"一级岗位大类"),
    SECOND(2,"二级岗位分类");

    private Integer code;
    private String desc;

    CategoryTpyeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public static CategoryTpyeEnum getByCode(Integer codeVal){
        for(CategoryTpyeEnum typeEnum : CategoryTpyeEnum.values()){
            if(typeEnum.code.equals(codeVal)){
                return typeEnum;
            }
        }
        return null;
    }
}
