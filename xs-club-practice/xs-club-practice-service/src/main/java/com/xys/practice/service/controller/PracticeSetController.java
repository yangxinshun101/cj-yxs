package com.xys.practice.service.controller;


import com.yxs.practice.api.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/practiceSet/set")
public class PracticeSetController {

    @GetMapping("/getSpecialPracticeContent")
    public Result<List<SpecialPracticeVO>> getSpecialPracticeContent() {
        try{


            return Result.success();

        }catch (Exception e){
            log.error("PracticeSetController.getSpecialPracticeContent.error:{}", e.getMessage(), e);
            return Result.fail("获取专项训练内容失败");
        }
    }
}
