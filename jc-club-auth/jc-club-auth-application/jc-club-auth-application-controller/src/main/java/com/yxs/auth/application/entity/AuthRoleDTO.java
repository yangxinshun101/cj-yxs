package com.yxs.auth.application.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (AuthRoleDTO)
 *
 * @author makejava
 * @since 2025-11-29 01:04:46
 */
@Data
public class AuthRoleDTO implements Serializable {

    private Long id;
/**
     * 角色名称
     */
    private String roleName;
/**
     * 角色唯一标识
     */
    private String roleKey;
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
/**
     * 是否被删除 0未删除 1已删除
     */
    private Integer isDeleted;



}

