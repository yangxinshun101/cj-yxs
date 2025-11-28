package com.yxs.subject.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 简答题(SubjectBrief)实体类
 *
 * @author makejava
 * @since 2025-11-16 15:15:29
 */
@Data
public class SubjectBriefDTO implements Serializable {
/**
     * 主键
     */
    private Long id;
/**
     * 题目id
     */
    private Integer subjectId;
/**
     * 题目答案
     */
    private String subjectAnswer;
/**
     * 创建人
     */
    private String createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 更新人
     */
    private String updateBy;
/**
     * 更新时间
     */
    private Date updateTime;

    private Integer isDeleted;



}

