package com.yxs.subject.infra.basic.service;

import com.yxs.subject.infra.basic.entity.SubjectMapping;

import java.util.List;

/**
 * 题目分类关系表(SubjectMapping)表服务接口
 *
 * @author makejava
 * @since 2025-11-16 12:19:47
 */
public interface SubjectMappingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectMapping queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    SubjectMapping insert(SubjectMapping subjectMapping);

    /**
     * 修改数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    SubjectMapping update(SubjectMapping subjectMapping);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 在Mapper表中通过CategoryID查询LabelID列表
     * @param subjectMapping
     * @return
     */
    List<SubjectMapping> queryLabelIdListByCategoryId(SubjectMapping subjectMapping);

    void batchInsert(List<SubjectMapping> mappingList);

    List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping);
}
