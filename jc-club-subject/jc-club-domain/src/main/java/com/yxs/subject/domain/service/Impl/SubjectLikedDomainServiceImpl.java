package com.yxs.subject.domain.service.Impl;

import com.alibaba.fastjson.JSON;

import com.yxs.subject.common.entity.PageResult;
import com.yxs.subject.common.enums.IsDeletedFlagEnum;
import com.yxs.subject.common.enums.SubjectLikedStatusEnum;
import com.yxs.subject.common.util.LoginUtil;
import com.yxs.subject.domain.convert.SubjectLikedBOConverter;
import com.yxs.subject.domain.entity.SubjectLikedBO;
import com.yxs.subject.domain.redis.RedisUtils;
import com.yxs.subject.domain.service.SubjectLikedDomainService;
import com.yxs.subject.infra.basic.entity.SubjectInfo;
import com.yxs.subject.infra.basic.entity.SubjectLiked;
import com.yxs.subject.infra.basic.service.SubjectInfoService;
import com.yxs.subject.infra.basic.service.SubjectLikedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 题目点赞表 领域service实现了
 *
 * @author jingdianjichi
 * @since 2024-01-07 23:08:45
 */
@Service
@Slf4j
public class SubjectLikedDomainServiceImpl implements SubjectLikedDomainService {

    @Resource
    private SubjectLikedService subjectLikedService;

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private RedisUtils redisUtil;

private static final String SUBJECT_LABELED_KEY= "subject.liked";
private static final String SUBJECT_LABELED_COUNT_KEY= "subject.liked.count";
private static final String SUBJECT_LABELED_DETAIL_KEY= "subject.liked.detail";

    @Override
    public void add(SubjectLikedBO subjectLikedBO) {
        Long subjectId = subjectLikedBO.getSubjectId();
        String likedUserId = LoginUtil.getLoginId();
        Integer status = subjectLikedBO.getStatus();
        String likedKey = buildSubjectLikedKey(subjectId.toString(), likedUserId);
        //这里不做单个用户重复对某一题目点赞的落库处理，交给数据库中统一使用主键冲突时修改的方式去处理
        redisUtil.putHash(SUBJECT_LABELED_KEY,likedKey, status);

        String likedCountKey = SUBJECT_LABELED_COUNT_KEY + subjectId;
        String likedDetailKey = SUBJECT_LABELED_DETAIL_KEY + subjectId + likedUserId;

        //如果是点赞，将题目点赞总数加一，并记录点赞详情
        if (SubjectLikedStatusEnum.LIKED.code == status){
            redisUtil.increment(likedCountKey,1);
            redisUtil.set(likedDetailKey, status);
        }else{
            redisUtil.increment(likedCountKey,-1);
            redisUtil.delete(likedDetailKey);
        }
    }

    @Override
    public Boolean isLiked(String subjectId, String userId) {
        String likedDetailKey = SUBJECT_LABELED_DETAIL_KEY + subjectId + userId;
        return redisUtil.exist(likedDetailKey);
    }

    @Override
    public Integer getLikedCount(String subjectId) {
        String likedCountKey = SUBJECT_LABELED_COUNT_KEY + subjectId;
        Integer result = Integer.valueOf( redisUtil.get(likedCountKey));
        if (result <= 0){
            return 0;
        }
        return result;

    }

    @Override
    public Boolean update(SubjectLikedBO subjectLikedBO) {
        return null;
    }

    @Override
    public Boolean delete(SubjectLikedBO subjectLikedBO) {
        return null;
    }

    private String buildSubjectLikedKey(String subjectId, String userId) {
        return subjectId + ":" + userId;
    }


    @Override
    public void syncLiked() {
        Map<Object, Object> likedMap = redisUtil.getHashAndDelete(SUBJECT_LABELED_KEY);
        if(Objects.isNull(likedMap) ){
            log.info("没有需要同步的题目点赞数据");
            return;
        }
        //为同步到数据库的数据，做数据准备；将所有信息放到List中，最后批量插入数据库
        List<SubjectLiked> subjectLikedList = new ArrayList<>();
        likedMap.forEach((key,value)->{
            String keyStr = (String) key;
            Integer status = (Integer) value;
            String[] keyArr = keyStr.split(":");
            Long subjectId = Long.valueOf(keyArr[0]);
            String likeUserId = keyArr[1];

            SubjectLiked subjectLiked = new SubjectLiked();
            subjectLiked.setSubjectId(subjectId);
            subjectLiked.setLikeUserId(likeUserId);
            subjectLiked.setStatus(status);
            subjectLiked.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());

            subjectLikedList.add(subjectLiked);
        });

        subjectLikedService.batchInsert(subjectLikedList);

    }

    @Override
    public PageResult<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO) {
        return null;
    }

    @Override
    public void syncLikedByMsg(SubjectLikedBO subjectLikedBO) {

    }


}
