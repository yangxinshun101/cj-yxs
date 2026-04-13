package com.yxs.practice.service.controller;


import com.alibaba.fastjson.JSON;
import com.yxs.practice.api.common.Result;
import com.yxs.practice.api.vo.SpecialPracticeVO;
import com.yxs.practice.service.service.PracticeSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/practiceSet/set")
public class PracticeSetController {

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
}
