package com.yxs.practice.service.dao;

import com.yxs.practice.service.entity.po.SubjectMappingPO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 题目分类关系表(SubjectMapping)表数据库访问层
 *
 */
public interface SubjectMappingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<SubjectMappingPO> queryById(Long id);


    List<SubjectMappingPO> getLabelSubjectCount(Long id, @Param("practiceSetIds") List<Integer> practiceSetIds);
}

