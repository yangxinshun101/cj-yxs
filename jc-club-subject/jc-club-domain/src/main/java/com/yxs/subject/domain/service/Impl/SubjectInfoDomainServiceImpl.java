package com.yxs.subject.domain.service.Impl;

import com.alibaba.fastjson.JSON;
import com.yxs.subject.common.entity.PageResult;
import com.yxs.subject.common.enums.CategoryTpyeEnum;
import com.yxs.subject.common.enums.IsDeletedFlagEnum;
import com.yxs.subject.domain.convert.SubjectInfoBOConvert;
import com.yxs.subject.domain.convert.SubjectLabelBOConvert;
import com.yxs.subject.domain.entity.SubjectInfoBO;
import com.yxs.subject.domain.entity.SubjectLabelBO;
import com.yxs.subject.domain.entity.SubjectOptionBO;
import com.yxs.subject.domain.handler.suject.SubjectTypeHandler;
import com.yxs.subject.domain.handler.suject.SubjectTypeHandlerFactory;
import com.yxs.subject.domain.service.SubjectInfoDomainService;
import com.yxs.subject.domain.service.SubjectLabelDomainService;
import com.yxs.subject.infra.basic.entity.SubjectCategory;
import com.yxs.subject.infra.basic.entity.SubjectInfo;
import com.yxs.subject.infra.basic.entity.SubjectLabel;
import com.yxs.subject.infra.basic.entity.SubjectMapping;
import com.yxs.subject.infra.basic.service.SubjectCategoryService;
import com.yxs.subject.infra.basic.service.SubjectInfoService;
import com.yxs.subject.infra.basic.service.SubjectLabelService;
import com.yxs.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    @Resource
    private SubjectInfoService subjectInfoService;
    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;
    @Resource
    private SubjectMappingService subjectMappingService;
    @Resource
    private SubjectLabelService subjectLabelService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.add.bo:{}", JSON.toJSONString(subjectInfoBO));
        }
        SubjectInfo subjectInfo = SubjectInfoBOConvert.INSTANCE.convertBoToInfo(subjectInfoBO);
        subjectInfo.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());
        subjectInfoService.insert(subjectInfo);
        SubjectTypeHandler handlerByTypeCode = subjectTypeHandlerFactory.
                getHandlerByTypeCode(subjectInfo.getSubjectType());
        subjectInfoBO.setId(subjectInfo.getId());
        handlerByTypeCode.add(subjectInfoBO);
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        List<SubjectMapping> mappingList = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfo.getId());
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMapping.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());
                mappingList.add(subjectMapping);
            });
        });
        subjectMappingService.batchInsert(mappingList);
    }
    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
            PageResult<SubjectInfoBO> pageResult = new PageResult<>();
            pageResult.setPageNo(subjectInfoBO.getPageNo());
            pageResult.setPageSize(subjectInfoBO.getPageSize());
            int start = (subjectInfoBO.getPageNo() - 1) * subjectInfoBO.getPageSize();
            SubjectInfo subjectInfo = SubjectInfoBOConvert.INSTANCE.convertBoToInfo(subjectInfoBO);
            int count = subjectInfoService.countByCondition(subjectInfo, subjectInfoBO.getCategoryId()
                    , subjectInfoBO.getLabelId());
            if (count == 0) {
                return pageResult;
            }
            List<SubjectInfo> subjectInfoList = subjectInfoService.queryPage(subjectInfo, subjectInfoBO.getCategoryId()
                    , subjectInfoBO.getLabelId(), start, subjectInfoBO.getPageSize());
            List<SubjectInfoBO> subjectInfoBOS = SubjectInfoBOConvert.INSTANCE.convertListInfoToBO(subjectInfoList);
            subjectInfoBOS.forEach(info -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(info.getId());
                List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
                List<Long> labelIds = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
                List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIds);
                List<String> labelNames = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
                info.setLabelName(labelNames);
            });
            pageResult.setRecords(subjectInfoBOS);
            pageResult.setTotal(count);
            return pageResult;
    }

    @Override
    public SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO) {
        SubjectInfo subjectInfo = subjectInfoService.queryById(subjectInfoBO.getId());
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandlerByTypeCode(subjectInfo.getSubjectType());
        SubjectOptionBO optionBO = handler.query(subjectInfo.getId().intValue());
        SubjectInfoBO bo = SubjectInfoBOConvert.INSTANCE.convertOptionAndInfoToBo(optionBO, subjectInfo);
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfo.getId());
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.NOT_DELETED.getCode());
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        List<String> labelNameList = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
        bo.setLabelName(labelNameList);
        return bo;
    }
}
