package com.yxs.subject.infra.basic.service.impl;

import com.yxs.subject.infra.basic.entity.SubjectLabel;
import com.yxs.subject.infra.basic.mapper.SubjectLabelDao;
import com.yxs.subject.infra.basic.service.SubjectLabelService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 题目标签表(SubjectLabel)表服务实现类
 *
 * @author makejava
 * @since 2025-11-13 20:09:50
 */
@Service("subjectLabelService")
public class SubjectLabelServiceImpl implements SubjectLabelService {
    @Resource
    private SubjectLabelDao subjectLabelDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectLabel queryById(Long id) {
        return this.subjectLabelDao.queryById(id);
    }



    /**
     * 新增数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(SubjectLabel subjectLabel) {
        return subjectLabelDao.insert(subjectLabel);
    }

    /**
     * 修改数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SubjectLabel subjectLabel) {
        return subjectLabelDao.update(subjectLabel);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.subjectLabelDao.deleteById(id);
    }

    /**
     * 查询一级分类
     * 通过分类ID查询标签列表
     * @param subjectLabel
     * @return
     */
    @Override
    public List<SubjectLabel> queryListByCategoryId(SubjectLabel subjectLabel) {
        return this.subjectLabelDao.queryListByCategoryId(subjectLabel);
    }

    @Override
    public List<SubjectLabel> batchQueryById(List<Long> labelIdList) {
        return subjectLabelDao.batchQueryById(labelIdList);
    }
}
