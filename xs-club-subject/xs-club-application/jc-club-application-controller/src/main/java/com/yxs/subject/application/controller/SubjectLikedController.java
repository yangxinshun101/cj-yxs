package com.yxs.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.yxs.subject.application.convert.SubjectLikedDTOConvert;
import com.yxs.subject.application.dto.SubjectLikedDTO;
import com.yxs.subject.common.entity.PageResult;
import com.yxs.subject.common.entity.Result;
import com.yxs.subject.common.util.LoginUtil;
import com.yxs.subject.domain.entity.SubjectLikedBO;
import com.yxs.subject.domain.service.SubjectLikedDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 题目点赞表 controller
 *
 * @author jingdianjichi
 * @since 2024-01-07 23:08:45
 */
@RestController
@RequestMapping("/subjectLiked/")
@Slf4j
public class SubjectLikedController {

    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    /**
     * 新增题目点赞表
     */
    @RequestMapping("add")
    public Result<Boolean> add(@RequestBody SubjectLikedDTO subjectLikedDTO) {

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLikedController.add.dto:{}", JSON.toJSONString(subjectLikedDTO));
            }
            Preconditions.checkNotNull(subjectLikedDTO.getSubjectId(), "题目id不能为空");
            Preconditions.checkNotNull(subjectLikedDTO.getStatus(), "点赞状态不能为空");
            String loginId = LoginUtil.getLoginId();
            subjectLikedDTO.setLikeUserId(loginId);
            Preconditions.checkNotNull(subjectLikedDTO.getLikeUserId(), "点赞人不能为空");
            SubjectLikedBO SubjectLikedBO = SubjectLikedDTOConvert.INSTANCE.convertDTOToBO(subjectLikedDTO);
            subjectLikedDomainService.add(SubjectLikedBO);
            return Result.success(true);
        } catch (Exception e) {
            log.error("SubjectLikedController.register.error:{}", e.getMessage(), e);
            return Result.fail("新增题目点赞表失败");
        }

    }


    /**
     * 查询我的点赞列表
     */
    @PostMapping("/getSubjectLikedPage")
    public Result<PageResult<SubjectLikedDTO>> getSubjectLikedPage(@RequestBody SubjectLikedDTO subjectLikedDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectLikedPage.dto:{}", JSON.toJSONString(subjectLikedDTO));
            }
            SubjectLikedBO subjectLikedBO = SubjectLikedDTOConvert.INSTANCE.convertDTOToBO(subjectLikedDTO);
            subjectLikedBO.setPageNo(subjectLikedDTO.getPageNo());
            subjectLikedBO.setPageSize(subjectLikedDTO.getPageSize());
            PageResult<SubjectLikedBO> boPageResult = subjectLikedDomainService.getSubjectLikedPage(subjectLikedBO);
            return Result.success(boPageResult);
        } catch (Exception e) {
            log.error("SubjectCategoryController.getSubjectLikedPage.error:{}", e.getMessage(), e);
            return Result.fail("分页查询我的点赞失败");
        }
    }



}
