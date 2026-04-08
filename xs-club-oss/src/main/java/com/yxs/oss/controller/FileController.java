package com.yxs.oss.controller;

import com.yxs.oss.config.AdapterConfig;
import com.yxs.oss.entity.Result;
import com.yxs.oss.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 文件操作controller
 *
 * @author: ChickenWing
 * @date: 2023/10/14
 */
@RestController
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private AdapterConfig adapterConfig;

    private String bucketName = "jc-club";

    @RequestMapping("/test")
    public String testCreateBucket() throws Exception {
        return adapterConfig.getAdapterType();
    }

    @RequestMapping("/testGetAllBuckets")
    public String testGetAllBuckets() throws Exception {
        List<String> allBucket = fileService.getAllBucket();
        return allBucket.get(0);
    }

    @RequestMapping("/getUrl")
    public String getUrl(String bucketName, String objectName) throws Exception {
        return fileService.getUrl(bucketName, objectName);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile uploadFile, String bucket, String objectName) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("FileController.upload.bucket:{},objectName:{}", bucket, objectName);
        }

        return Result.success(fileService.uploadFile(uploadFile, bucketName, objectName));
    }

}
