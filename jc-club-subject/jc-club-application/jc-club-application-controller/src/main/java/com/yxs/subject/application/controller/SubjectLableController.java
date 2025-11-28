package com.yxs.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.yxs.subject.application.convert.SubjectLabelDTOConvert;
import com.yxs.subject.application.dto.SubjectLabelDTO;
import com.yxs.subject.common.entity.Result;
import com.yxs.subject.domain.entity.SubjectLabelBO;
import com.yxs.subject.domain.service.SubjectLabelDomainService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subject/label")
@Slf4j
public class SubjectLableController {

    private SubjectLabelDomainService subjectLabelDomainService;

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try{
            /**
             * 在controller层的逻辑就是校验参数合法性
             */
            if (log.isInfoEnabled()) {
                log.info("SubjectLableController.add.subjectLabelDTO:{}", subjectLabelDTO);
            }
            // 校验参数合法性
            Preconditions.checkNotNull(subjectLabelDTO.getCategoryId(), "分类id不能为空");

            //进行类型转换
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConvert.INSTANCE.
                    convertSubjectLableBOToDTO(subjectLabelDTO);

            //调用实际插入的业务逻辑
            Boolean result = subjectLabelDomainService.add(subjectLabelBO);

            return Result.success(result.toString());
        }catch(Exception e){
            log.error("SubjectLableController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增标签失败");
        }

    }



    @PostMapping("/delete")
    public Result delete (@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            //打印日志，记录传入参数信息
            if (log.isInfoEnabled()) {
                log.info("SubjectController.delete.subjectLabelDTO:{}", JSON.toJSON(subjectLabelDTO));
            }
            //校验传入参数的合法性
            Preconditions.checkNotNull(subjectLabelDTO.getId(), "id不能为空");

            //将传入参数进行转换，变为domain层可调用的参数
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConvert.INSTANCE.
                    convertSubjectLableBOToDTO(subjectLabelDTO);

            //调用domain层进行业务逻辑处理
            Boolean result = subjectLabelDomainService.delete(subjectLabelBO);

            return Result.success(result.toString());
        }catch (Exception e) {
            log.error("SubjectController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除分类失败");
        }
    }


    @PostMapping("/update")
    public Result update (@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            //打印日志，记录传入参数信息
            if (log.isInfoEnabled()) {
                log.info("SubjectController.update.subjectLabelDTO:{}", JSON.toJSON(subjectLabelDTO));
            }
            //校验传入参数的合法性（这两个必须校验，categoryId可以不传）
            Preconditions.checkNotNull(subjectLabelDTO.getId(), "id不能为空");
            Preconditions.checkNotNull(subjectLabelDTO.getSortNum(), "排序号不能为空");

            //将传入参数进行转换，变为domain层可调用的参数
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConvert.INSTANCE.
                    convertSubjectLableBOToDTO(subjectLabelDTO);

            //调用domain层进行业务逻辑处理
            Boolean result = subjectLabelDomainService.update(subjectLabelBO);

            return Result.success(result.toString());
        }catch (Exception e) {
            log.error("SubjectController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新分类失败");
        }
    }

    /**
     * 查询分类下的标签信息
     */
    @PostMapping("/queryLabelByCategoryId")
    public Result<List<SubjectLabelDTO>> queryLabelByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            //打印日志，记录传入参数信息
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.queryLabelByCategoryId.dto:{}",
                        JSON.toJSONString(subjectLabelDTO));
            }
            //校验传入参数的合法性
            Preconditions.checkNotNull(subjectLabelDTO.getCategoryId(), "分类id不能为空");

            //将传入参数进行转换，变为domain层可调用的参数
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConvert.INSTANCE.convertSubjectLableBOToDTO(subjectLabelDTO);

            //调用domain层进行业务逻辑处理
            List<SubjectLabelBO> resultList = subjectLabelDomainService.queryLabelByCategoryId(subjectLabelBO);

            //进行类型转换
            List<SubjectLabelDTO> subjectLabelDTOS = SubjectLabelDTOConvert.INSTANCE.convertBOToLabelDTOList(resultList);
            return Result.success(subjectLabelDTOS);
        } catch (Exception e) {
            log.error("SubjectLabelController.queryLabelByCategoryId.error:{}", e.getMessage(), e);
            return Result.fail("查询分类下标签失败");
        }
    }
}
