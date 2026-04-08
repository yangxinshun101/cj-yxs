package com.yxs.subject.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目标签表(SubjectLabelDTO)
 *
 * @author makejava
 * @since 2025-11-13 20:09:50
 */
@Data
public class SubjectLabelDTO implements Serializable {
/**
     * 主键
     */
    private Long id;
/**
     * 标签分类
     */
    private String labelName;
/**
     * 排序
     */
    private Integer sortNum;

    private Long categoryId;






}

