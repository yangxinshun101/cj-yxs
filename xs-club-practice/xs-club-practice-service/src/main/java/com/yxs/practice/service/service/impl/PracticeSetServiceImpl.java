package com.yxs.practice.service.service.impl;

import com.yxs.practice.api.enums.IsDeletedFlagEnum;
import com.yxs.practice.api.enums.SubjectInfoTypeEnum;
import com.yxs.practice.api.req.GetPracticeSubjectsReq;
import com.yxs.practice.api.vo.*;
import com.yxs.practice.service.dao.*;
import com.yxs.practice.service.entity.dto.CategoryDTO;
import com.yxs.practice.service.entity.dto.PracticeSubjectDTO;
import com.yxs.practice.service.entity.po.*;
import com.yxs.practice.service.service.PracticeSetService;
import com.yxs.practice.service.util.LoginUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;


@Service
public class PracticeSetServiceImpl implements PracticeSetService {

    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    @Resource
    private SubjectMappingDao subjectMappingDao;

    @Resource
    private SubjectLabelDao subjectLabelDao;

    @Resource
    private SubjectDao subjectDao;

    @Resource
    private PracticeSetDao practiceSetDao;

    @Resource
    private PracticeSetDetailDao practiceSetDetailDao;


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
            specialPracticeLabelVO.setAssembleId(id + "-" + subjectMappingPO.getLabelId());
            SubjectLabelPO subjectLabelPO = subjectLabelDao.queryById(subjectMappingPO.getId());
            specialPracticeLabelVO.setLabelName(subjectLabelPO.getLabelName());
            specialPracticeLabelVOList.add(specialPracticeLabelVO);
        });

        return specialPracticeLabelVOList;
    }

    @Override
    public PracticeSetVO addPractice(PracticeSubjectDTO practiceSubjectDTO) {
        PracticeSetVO practiceSetVO = new PracticeSetVO();
        //第一步：查出配置好的题目内容。
        List<PracticeSubjectDetailVO> practiceList = getPracticeList(practiceSubjectDTO);

        //第二步：记录练习表面到set表
        PracticeSetPO practiceSetPO = new PracticeSetPO();
        practiceSetPO.setSetType(1);
        List<String> assembleIds = practiceSubjectDTO.getAssembleIds();
        Set<Long> categoryIdSet = new HashSet<>();
        assembleIds.forEach(assembleId -> {
            Long categoryId = Long.valueOf(assembleId.split("-")[0]);
            categoryIdSet.add(categoryId);
        });
        StringBuffer setName = new StringBuffer();
        int i = 1;
        for (Long categoryId : categoryIdSet) {
            if (i > 2) {
                break;
            }
            CategoryPO categoryPO = subjectCategoryDao.getCategoryById(categoryId.intValue());
            setName.append(categoryPO.getCategoryName());
            setName.append("、");
            i = i + 1;
        }
        setName.deleteCharAt(setName.length() - 1);
        if (i == 2) {
            setName.append("专项练习");
        } else {
            setName.append("等专项练习");
        }
        practiceSetPO.setSetName(setName.toString());
        String labelId = assembleIds.get(0).split("-")[1];
        SubjectLabelPO labelPO = subjectLabelDao.queryById(Long.valueOf(labelId));
        practiceSetPO.setPrimaryCategoryId(labelPO.getCategoryId());
        practiceSetPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        practiceSetPO.setCreatedBy(LoginUtil.getLoginId());
        practiceSetPO.setCreatedTime(new Date());
        practiceSetDao.add(practiceSetPO);
        Long practiceSetId = practiceSetPO.getId();

        //第三步：记录详细题目编号到set_detail表
        practiceList.forEach(e -> {
            PracticeSetDetailPO detailPO = new PracticeSetDetailPO();
            detailPO.setSetId(practiceSetId);
            detailPO.setSubjectId(e.getSubjectId());
            detailPO.setSubjectType(e.getSubjectType());
            detailPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            detailPO.setCreatedBy(LoginUtil.getLoginId());
            detailPO.setCreatedTime(new Date());
            practiceSetDetailDao.add(detailPO);
        });
        practiceSetVO.setSetId(practiceSetId);

        return practiceSetVO;
    }

    private List<PracticeSubjectDetailVO> getPracticeList(PracticeSubjectDTO practiceSubjectDTO) {
        List<PracticeSubjectDetailVO> practiceList = new ArrayList<>();

        //防重
        List<Long> excludeSubject = new ArrayList<>();

        //设置题目数量，之后优化到nacos动态配置
        Integer radioSubjectCount = 10;
        Integer multipleSubjectCount = 6;
        Integer judgeSubjectCount = 4;
        Integer totalSubjectCount = 20;

        //查询单选
        practiceSubjectDTO.setSubjectCount(radioSubjectCount);
        practiceSubjectDTO.setSubjectType(SubjectInfoTypeEnum.RADIO.getCode());
        assembleList(practiceSubjectDTO, practiceList, excludeSubject);

        //查询多选
        practiceSubjectDTO.setSubjectCount(multipleSubjectCount);
        practiceSubjectDTO.setSubjectType(SubjectInfoTypeEnum.MULTIPLE.getCode());
        assembleList(practiceSubjectDTO, practiceList, excludeSubject);
        //查询判断
        practiceSubjectDTO.setSubjectCount(judgeSubjectCount);
        practiceSubjectDTO.setSubjectType(SubjectInfoTypeEnum.JUDGE.getCode());
        assembleList(practiceSubjectDTO, practiceList, excludeSubject);
        //补充题目
        if (practiceList.size() == totalSubjectCount) {
            return practiceList;
        }
        Integer remainCount = totalSubjectCount - practiceList.size();
        practiceSubjectDTO.setSubjectCount(remainCount);
        practiceSubjectDTO.setSubjectType(1);
        assembleList(practiceSubjectDTO, practiceList, excludeSubject);


        return practiceList;
    }

    private void assembleList(PracticeSubjectDTO practiceSubjectDTO, List<PracticeSubjectDetailVO> list, List<Long> excludeSubject) {
        practiceSubjectDTO.setExcludeSubjectIds(excludeSubject);
        List<SubjectPO> subjectPOList = subjectDao.getPracticeSubject(practiceSubjectDTO);
        subjectPOList.forEach(e -> {
            PracticeSubjectDetailVO vo = new PracticeSubjectDetailVO();
            vo.setSubjectId(e.getId());
            vo.setSubjectType(e.getSubjectType());
            excludeSubject.add(e.getId());
            list.add(vo);
        });
    }

    @Override
    public PracticeSubjectListVO getSubjects(GetPracticeSubjectsReq req) {
        Long setId = req.getSetId();
        PracticeSubjectListVO practiceSubjectListVO = new PracticeSubjectListVO();

        //根据练题Id查询当前练习题的所有题目Id；
        List<PracticeSetDetailPO> practiceSetDetailPOS = practiceSetDetailDao.selectBySetId(setId);
        if (CollectionUtils.isEmpty(practiceSetDetailPOS)) {
            return practiceSubjectListVO;
        }

        //根据查询出的所以题目Id
        List<PracticeSubjectDetailVO> practiceSubjectDetailVOList = new ArrayList<>();
        practiceSetDetailPOS.forEach(e -> {
            PracticeSubjectDetailVO practiceSubjectDetailVO = new PracticeSubjectDetailVO();
            practiceSubjectDetailVO.setSubjectId(e.getSubjectId());
            practiceSubjectDetailVO.setSubjectType(e.getSubjectType());
            practiceSubjectDetailVOList.add(practiceSubjectDetailVO);
        });

        practiceSubjectListVO.setSubjectList(practiceSubjectDetailVOList);
        PracticeSetPO practiceSetPO = practiceSetDao.selectById(setId);
        practiceSubjectListVO.setTitle(practiceSetPO.getSetName());

        return practiceSubjectListVO;
    }

    @Override
    public PracticeSubjectVO getPracticeSubject(PracticeSubjectDTO dto) {

        return null;
    }


}
