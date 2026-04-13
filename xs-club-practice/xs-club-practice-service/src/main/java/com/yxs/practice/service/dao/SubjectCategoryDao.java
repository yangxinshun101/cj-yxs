package com.yxs.practice.service.dao;

import com.yxs.practice.service.entity.dto.CategoryDTO;
import com.yxs.practice.service.entity.po.CategoryPO;
import com.yxs.practice.service.entity.po.PrimaryCategoryPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目分类(SubjectCategory)表数据库访问层
 *
 * @author makejava
 * @since 2025-11-06 20:26:57
 */
public interface SubjectCategoryDao {

    // 根据练习集Code值获取所在的一级分类Id
    List<PrimaryCategoryPO> getPrimaryCategory(@Param("item") List<Integer> practiceSetIds);

    CategoryPO getCategoryById(Integer parentId);

    List<CategoryPO> selectCategoryByPrimaryCategory(CategoryDTO categoryDTO);
}

