package com.yxs.practice.service.service.impl;

import com.yxs.practice.api.enums.SubjectInfoTypeEnum;
import com.yxs.practice.api.vo.SpecialPracticeCategoryVO;
import com.yxs.practice.api.vo.SpecialPracticeLabelVO;
import com.yxs.practice.service.dao.SubjectCategoryDao;
import com.yxs.practice.service.dao.SubjectLabelDao;
import com.yxs.practice.service.dao.SubjectMappingDao;
import com.yxs.practice.service.entity.dto.CategoryDTO;
import com.yxs.practice.service.entity.po.CategoryPO;
import com.yxs.practice.service.entity.po.PrimaryCategoryPO;
import com.yxs.practice.api.vo.SpecialPracticeVO;
import com.yxs.practice.service.entity.po.SubjectLabelPO;
import com.yxs.practice.service.entity.po.SubjectMappingPO;
import com.yxs.practice.service.service.PracticeSetService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class PracticeSetServiceImpl implements PracticeSetService {

    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    @Resource
    private SubjectMappingDao subjectMappingDao;

    @Resource
    private SubjectLabelDao subjectLabelDao;

    @Override
    public List<SpecialPracticeVO> getSpecialPracticeContent() {

        List<SpecialPracticeVO> specialPracticeVOList = new ArrayList<>();

        List<Integer> practiceSetIds = new ArrayList<>();
        practiceSetIds.add(SubjectInfoTypeEnum.RADIO.getCode());
        practiceSetIds.add(SubjectInfoTypeEnum.MULTIPLE.getCode());
        practiceSetIds.add(SubjectInfoTypeEnum.JUDGE.getCode());

        //拿着题目的分类Id值去查询info表找到其Id，根据info表的Id去查Mapper表对应的CategoryId。拿到CategoryId去找他的一级分类Id
        List<PrimaryCategoryPO> primaryCategory = subjectCategoryDao.getPrimaryCategory(practiceSetIds);

        //如果一级分类为空，返回一个空集合
        if (CollectionUtils.isEmpty(primaryCategory)) {
            return Collections.emptyList();
        }

        primaryCategory.forEach(primaryCategoryPO -> {
            Integer parentId = primaryCategoryPO.getParentId();
            CategoryPO categoryPO = subjectCategoryDao.getCategoryById(parentId);

            //拿到一级分类的名称值后，set到specialPracticeVO中
            SpecialPracticeVO specialPracticeVO = new SpecialPracticeVO();
            specialPracticeVO.setPrimaryCategoryName(categoryPO.getCategoryName());

            //根据一级分类去查支持出题型的二级分类
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setParentId(parentId);
            categoryPO.setCategoryType(2);
            List<CategoryPO> categoryPOList = subjectCategoryDao.selectCategoryByPrimaryCategory(categoryDTO);

            //如果二级分类为空，返回一个空集合
            if (CollectionUtils.isEmpty(categoryPOList)) {
                return;
            }

            List<SpecialPracticeCategoryVO> specialPracticeCategoryVOList = new ArrayList<>();
            //根据二级分类ID去查二级分类下的Label信息
            categoryPOList.forEach(category -> {
                List<SpecialPracticeLabelVO> specialPracticeLabelVOList = getLabelListVO(category.getId(), practiceSetIds);

            if (CollectionUtils.isEmpty(specialPracticeLabelVOList))
                return;

            SpecialPracticeCategoryVO specialPracticeCategoryVO = new SpecialPracticeCategoryVO();
            specialPracticeCategoryVO.setCategoryName(category.getCategoryName());
            specialPracticeCategoryVO.setCategoryId(category.getId());
            specialPracticeCategoryVO.setLabelList(specialPracticeLabelVOList);
            specialPracticeCategoryVOList.add(specialPracticeCategoryVO);
            });

            specialPracticeVO.setCategoryList(specialPracticeCategoryVOList);
            specialPracticeVOList.add(specialPracticeVO);
        });


        return specialPracticeVOList;
    }

    //根据二级分类去查询其标签信息
    private List<SpecialPracticeLabelVO> getLabelListVO(Long id, List<Integer> practiceSetIds) {
        List<SubjectMappingPO> subjectMappingPOS = subjectMappingDao.getLabelSubjectCount(id, practiceSetIds);
        if (CollectionUtils.isEmpty(subjectMappingPOS)) {
            return Collections.emptyList();
        }

        //根据查询的标签信息去补充返回信息
        List<SpecialPracticeLabelVO> specialPracticeLabelVOList = new ArrayList<>();
        subjectMappingPOS.forEach(subjectMappingPO -> {
            SpecialPracticeLabelVO specialPracticeLabelVO = new SpecialPracticeLabelVO();
            specialPracticeLabelVO.setId(subjectMappingPO.getId());
            specialPracticeLabelVO.setAssembleId(id+ "-" + subjectMappingPO.getLabelId());
            SubjectLabelPO subjectLabelPO = subjectLabelDao.queryById(subjectMappingPO.getId());
            specialPracticeLabelVO.setLabelName(subjectLabelPO.getLabelName());
            specialPracticeLabelVOList.add(specialPracticeLabelVO);
        });

        return specialPracticeLabelVOList;
    }
}
