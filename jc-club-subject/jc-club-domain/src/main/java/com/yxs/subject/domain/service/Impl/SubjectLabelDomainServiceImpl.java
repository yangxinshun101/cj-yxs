package com.yxs.subject.domain.service.Impl;

import com.yxs.subject.common.enums.CategoryTpyeEnum;
import com.yxs.subject.common.enums.IsDeletedFlagEnum;
import com.yxs.subject.domain.convert.SubjectCategoryBOConvert;
import com.yxs.subject.domain.convert.SubjectLabelBOConvert;
import com.yxs.subject.domain.entity.SubjectCategoryBO;
import com.yxs.subject.domain.entity.SubjectLabelBO;
import com.yxs.subject.domain.service.SubjectLabelDomainService;
import com.yxs.subject.infra.basic.entity.SubjectCategory;
import com.yxs.subject.infra.basic.entity.SubjectLabel;
import com.yxs.subject.infra.basic.entity.SubjectMapping;
import com.yxs.subject.infra.basic.service.SubjectCategoryService;
import com.yxs.subject.infra.basic.service.SubjectLabelService;
import com.yxs.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {

    @Resource
    private SubjectLabelService subjectLabelService;
    @Resource
    private SubjectCategoryService subjectCategoryService;
    @Resource
    private SubjectMappingService subjectMappingService;

    /**
     * 新增题目分类
     * @param subjectLabelBO
     * @return
     */
    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        //打印日志，记录传入参数信息，看看有没有转换成功
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl add subjectCategoryBO: {}", subjectLabelBO);
        }
        //将传入参数进行转换，变为infra层可调用的参数
        SubjectLabel subjectLabel = SubjectLabelBOConvert.INSTANCE.
                convertBOToLabel(subjectLabelBO);
        //设置未删除标识
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());
        //调用infra层进行数据插入操作
        int count = subjectLabelService.insert(subjectLabel);

        return count > 0;
    }

    /**
     *  删除题目分类
     * @param subjectLabelBO
     * @return
     */
    @Override
    public Boolean delete(SubjectLabelBO subjectLabelBO) {
        //转化数据
        SubjectLabel subjectLabel = SubjectLabelBOConvert.INSTANCE.
                convertBOToLabel(subjectLabelBO);

        //设置删除标识
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());

        //打印日志，记录传入参数信息和删除标志位有没有设置成功
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.delete subjectLabel: {}", subjectLabel);
        }

        //调用infra层输出方法进行删除
        int count = subjectLabelService.update(subjectLabel);

        return count > 0;
    }

    /**
     * 更新题目分类
     * @param subjectLabelBO
     * @return
     */
    @Override
    public Boolean update(SubjectLabelBO subjectLabelBO) {
        //打印日志，记录传入参数信息，看看有没有转换成功
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl.update.subjectCategoryBO: {}", subjectLabelBO);
        }
        //直接转换为infra层所需要的数据类型
        SubjectLabel subjectLabel = SubjectLabelBOConvert.INSTANCE.
                convertBOToLabel(subjectLabelBO);

        //直接就是去调用infra层的更新方法进行更新
        int count = subjectLabelService.update(subjectLabel);

        return count > 0;

    }

    /**
     * 查询分类下标签
     * @param subjectLabelBO
     * @return
     */
    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {

        SubjectCategory subjectCategory = subjectCategoryService.queryById(subjectLabelBO.getCategoryId());
        //如果传入的是一级分类ID，则直接返回标签信息
        if (subjectCategory.getCategoryType() == CategoryTpyeEnum.PARIMARY.getCode()){
            //直接查Label表返回所有Label信息
            SubjectLabel subjectLabel = new SubjectLabel();
            subjectLabel.setCategoryId(subjectLabelBO.getCategoryId());
            subjectCategory.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());

            //调用infra层进行数据查询
            List<SubjectLabel> subjectLabels = subjectLabelService.queryListByCategoryId(subjectLabel);

            //依旧进行类型转换，返回BO数据
            List<SubjectLabelBO> subjectLabelBOS = SubjectLabelBOConvert.INSTANCE.
                    convertLabelListToBOList(subjectLabels);

            return subjectLabelBOS;
        }
        //如果传入的是二级分类ID，则先根据分类ID去查Mapper表，查出所有的标签ID，再去查Label表，补齐便签信息
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectLabelBO.getCategoryId());
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());

        //根据CategoryId和IsDeleted去查所有Label表的映射ID
        List<SubjectMapping> subjectMappingList = subjectMappingService.queryLabelIdListByCategoryId(subjectMapping);

        if(CollectionUtils.isEmpty(subjectMappingList)){
            return Collections.emptyList();
        }

        //根据映射ID去查Label表，补齐标签信息
        List<Long> labelIdList = subjectMappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabel = subjectLabelService.batchQueryById(labelIdList);

        //将subjectLabel转换为BO传回去
        List<SubjectLabelBO> subjectLabelBOS = SubjectLabelBOConvert.INSTANCE.
                convertLabelListToBOList(subjectLabel);

        return subjectLabelBOS;
    }


}
