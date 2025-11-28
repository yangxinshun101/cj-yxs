package com.yxs.subject.domain.service.Impl;

import com.yxs.subject.common.enums.IsDeletedFlagEnum;
import com.yxs.subject.domain.convert.SubjectCategoryBOConvert;
import com.yxs.subject.domain.entity.SubjectCategoryBO;
import com.yxs.subject.domain.service.SubjectCategoryDomainService;
import com.yxs.subject.infra.basic.entity.SubjectCategory;
import com.yxs.subject.infra.basic.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        //打印日志，记录传入参数信息
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl add subjectCategoryBO: {}", subjectCategoryBO);
        }
        //将传入参数进行转换，变为infra层可调用的参数
        SubjectCategory subjectCategory = SubjectCategoryBOConvert.INSTANCE.
                convertBOToCategory(subjectCategoryBO);
        //设置未删除标识
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());
        //调用infra层进行数据插入操作
        subjectCategoryService.insert(subjectCategory);
    }


    /**
     * 查询题目分类列表
     * 调用infra层进行数据库的查询
     *
     * @param subjectCategoryBO
     * @return
     */
    @Override
    public List<SubjectCategoryBO> queryCategoryList(SubjectCategoryBO subjectCategoryBO) {

        //将BO数据转换为infra层可调用的entity数据进行查询
        SubjectCategory subjectCategory = SubjectCategoryBOConvert.INSTANCE.
                convertBOToCategory(subjectCategoryBO);

        //设置未删除标识，查询时只查未删除的数据
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());

        //拿到infra层数据类型后调用，infra层查询方法进行查询
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategoryParentList(subjectCategory);

        //拿到infra层的数据，进行转换为BO数据返回
        List<SubjectCategoryBO> subjectCategoryBOList = SubjectCategoryBOConvert.INSTANCE.
                convertCategoryListToBOList(subjectCategoryList);

        //转换完成后，打印日志进行记录。便于后期排查问题
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl queryCategoryList subjectCategoryBOList: {}", subjectCategoryBOList);
        }

        return subjectCategoryBOList;
    }

    @Override
    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        //直接转换为infra层所需要的数据类型
        SubjectCategory subjectCategory = SubjectCategoryBOConvert.INSTANCE.
                convertBOToCategory(subjectCategoryBO);

        //直接就是去调用infra层的更新方法进行更新
        int count = subjectCategoryService.update(subjectCategory);

        return count > 0;

    }

    @Override
    public Boolean delete(SubjectCategoryBO subjectCategoryBO) {
        //转化数据
        SubjectCategory subjectCategory = SubjectCategoryBOConvert.INSTANCE.
                convertBOToCategory(subjectCategoryBO);

        //设置删除标识
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());

        //调用infra层输出方法进行删除
        int count = subjectCategoryService.update(subjectCategory);

        return count > 0;
    }
}
