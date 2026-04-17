package com.yxs.practice.service.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.yxs.practice.api.common.Result;
import com.yxs.practice.api.req.GetPracticeSubjectListReq;
import com.yxs.practice.api.vo.PracticeSetVO;
import com.yxs.practice.api.vo.SpecialPracticeVO;
import com.yxs.practice.service.entity.dto.PracticeSubjectDTO;
import com.yxs.practice.service.service.PracticeSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/practiceSet/set")
public class PracticeSetController {

    @Resource
    private PracticeSetService practiceSetService;

    /**
     * 在进入专项训练页面时，获取可选择训练的专项练习的一级二级和标签内容
     * @return
     */
    @GetMapping("/getSpecialPracticeContent")
    public Result<List<SpecialPracticeVO>> getSpecialPracticeContent() {
        try{
            List<SpecialPracticeVO> specialPracticeContent = practiceSetService.getSpecialPracticeContent();

            if (log.isInfoEnabled()){
                log.info("PracticeSetController.getSpecialPracticeContent.specialPracticeContent:{}",
                        specialPracticeContent);
            }
                return Result.ok(specialPracticeContent);
        }catch (Exception e){
            log.error("PracticeSetController.getSpecialPracticeContent.error:{}", e.getMessage(), e);
            return Result.fail("获取专项训练内容失败");
        }
    }

    /**
     * 开始练习
     */
    @GetMapping("/addPractice")
    public Result<PracticeSetVO> addPractice(@RequestBody GetPracticeSubjectListReq req) {
        if (log.isInfoEnabled())
            log.info("PracticeSetController.addPractice.req:{}", JSON.toJSONString(req));

        try {
            //先校验参数
            Preconditions.checkArgument(Objects.isNull(req), "参数不能为空");
            Preconditions.checkArgument(Objects.isNull(req.getAssembleIds()), "标签Ids不能为空");

            //准备查询联系的入参
            PracticeSubjectDTO practiceSubjectDTO = new PracticeSubjectDTO();
            practiceSubjectDTO.setAssembleIds(req.getAssembleIds());
            PracticeSetVO practiceSetVO = practiceSetService.addPractice(practiceSubjectDTO);

            if (log.isInfoEnabled()) {
                log.info("获取练习题目列表出参{}", JSON.toJSONString(practiceSetVO));
            }
            return Result.ok(practiceSetVO);

        }catch (IllegalArgumentException e){
            log.error("PracticeSetController.addPractice.error:{}", e.getMessage(), e);
            return Result.fail("参数错误");
        }catch (Exception e){
            log.error("PracticeSetController.addPractice.error:{}", e.getMessage(), e);
            return Result.fail("开始练习失败");
        }

    }
}
