package com.yxs.oss.adapter;

import com.yxs.oss.entity.FileInfo;
import com.yxs.oss.utils.MinioUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class MinioAdapter implements StorageAdapter{

    @Resource
    private MinioUtil minioUtil;

    @Override
    public List<String> getAllBucket() {
        return minioUtil.getAllBucket();
    }

    @Override
    public List<FileInfo> getFilesInBucket(String bucket) {
        return Collections.emptyList();
    }

    @Override
    public void uploadFile(MultipartFile uploadFile, String bucket, String objectName) {

    }

    @Override
    public void createBucket(String bucket) {

    }

    @Override
    public InputStream downloadFile(String bucket, String objectName) {
        return null;
    }

    @Override
    public void deleteFile(String bucket, String objectName) {

    }

    @Override
    public void deleteBucket(String bucket) {

    }

    @Override
    public String getFileUrl(String bucket, String objectName) {
        return "";
    }
}
