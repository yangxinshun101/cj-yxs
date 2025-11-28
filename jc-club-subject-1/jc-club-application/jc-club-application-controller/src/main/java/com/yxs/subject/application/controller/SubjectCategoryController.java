package com.yxs.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.yxs.subject.application.convert.SubjectCategoryDTOConvert;
import com.yxs.subject.application.dto.SubjectCategoryDTO;
import com.yxs.subject.common.entity.Result;
import com.yxs.subject.domain.entity.SubjectCategoryBO;
import com.yxs.subject.domain.service.SubjectCategoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject/category")
@Slf4j
public class SubjectCategoryController {

    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    /**
     * 新增分类
     */
    @PostMapping("/add")
    public Result add (@RequestBody SubjectCategoryDTO subjectCategoryDTO) {

        try {
            //打印日志，记录传入参数信息
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.subjectCategoryDTO:{}", JSON.toJSON(subjectCategoryDTO));
            }
            //校验传入参数的合法性
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类父级id不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(subjectCategoryDTO.getCategoryName()), "分类名称不能为空");

            //将传入参数进行转换，变为domain层可调用的参数
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConvert.INSTANCE.
                    subjectCategoryDTOToBO(subjectCategoryDTO);

            //调用domain层进行业务逻辑处理
            subjectCategoryDomainService.add(subjectCategoryBO);

            return Result.success();
        }catch (Exception e) {
            log.error("SubjectController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增分类失败");
        }

    }

    /**
     * 查询岗位大类，根据岗位parentId=0去查询岗位大类
     * @param subjectCategoryDTO
     * @return
     */
    @PostMapping("/queryPrimaryCategory")
    public Result<SubjectCategoryDTO> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try{
            if (log.isInfoEnabled()) {
                log.info("SubjectController.queryPrimaryCategory.subjectCategoryDTO:{}", subjectCategoryDTO);
            }

            //对传入数据的parentId进行判空
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "父级分类id不能为空");

            //调用convert类进行转换，将DTO转换为BO
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConvert.INSTANCE.
                    subjectCategoryDTOToBO(subjectCategoryDTO);

            //调用Domain层进行逻辑处理
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategoryList(subjectCategoryBO);

            //对Domain层返回的BO数据进行再次转换为DTO数据
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConvert.INSTANCE.
                    convertCategoryBOListToDTOList(subjectCategoryBOList);

            return Result.success(subjectCategoryDTOList);
        } catch(Exception e){
            log.error("subjectController.queryPrimaryCategory.error:{}", e.getMessage(), e);

            return Result.fail("查询主标题失败");
        }
    }

    /**
     * 查询子分类，根据岗位parentId去查询子分类
     * @param subjectCategoryDTO
     * @return
     */
    @PostMapping("/queryChildCategory")
    public Result<SubjectCategoryDTO> queryChildCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO){
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.queryChildCategory.subjectCategoryDTO:{}", subjectCategoryDTO);
            }

            //对传入数据的parentId进行判空
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "父级分类id不能为空");

            //调用convert类进行转换，将DTO转换为BO
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConvert.INSTANCE.
                    subjectCategoryDTOToBO(subjectCategoryDTO);

            //调用Domain层进行逻辑处理
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategoryList(subjectCategoryBO);

            //对Domain层返回的BO数据进行再次转换为DTO数据
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConvert.INSTANCE.
                    convertCategoryBOListToDTOList(subjectCategoryBOList);

            return Result.success(subjectCategoryDTOList);
        } catch(Exception e){
            log.error("subjectController.queryChildCategory.error:{}", e.getMessage(), e);

            return Result.fail("查询子标题失败");
        }
    }

    /**
     * 更新分类信息
     * @param subjectCategoryDTO
     * @return
     */
    @PostMapping("/update")
    public Result update (@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            //打印日志，记录传入参数信息
            if (log.isInfoEnabled()) {
                log.info("SubjectController.update.subjectCategoryDTO:{}", JSON.toJSON(subjectCategoryDTO));
            }
            //校验传入参数的合法性
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "id不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类父级id不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(subjectCategoryDTO.getCategoryName()), "分类名称不能为空");

            //将传入参数进行转换，变为domain层可调用的参数
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConvert.INSTANCE.
                    subjectCategoryDTOToBO(subjectCategoryDTO);

            //调用domain层进行业务逻辑处理
            Boolean result = subjectCategoryDomainService.update(subjectCategoryBO);

            return Result.success(result.toString());
        }catch (Exception e) {
            log.error("SubjectController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新分类失败");
        }
    }

    /**
     * 根据ID删除分类信息
     * @param subjectCategoryDTO
     * @return
     */
    @PostMapping("/delete")
    public Result delete (@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            //打印日志，记录传入参数信息
            if (log.isInfoEnabled()) {
                log.info("SubjectController.delete.subjectCategoryDTO:{}", JSON.toJSON(subjectCategoryDTO));
            }
            //校验传入参数的合法性
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "id不能为空");

            //将传入参数进行转换，变为domain层可调用的参数
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConvert.INSTANCE.
                    subjectCategoryDTOToBO(subjectCategoryDTO);

            //调用domain层进行业务逻辑处理
            Boolean result = subjectCategoryDomainService.delete(subjectCategoryBO);

            return Result.success(result.toString());
        }catch (Exception e) {
            log.error("SubjectController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除分类失败");
        }
    }



}
