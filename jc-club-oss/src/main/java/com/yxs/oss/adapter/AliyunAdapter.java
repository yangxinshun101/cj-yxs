package com.yxs.oss.adapter;

import com.yxs.oss.entity.FileInfo;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class AliyunAdapter implements StorageAdapter{
    @Override
    public List<String> getAllBucket() {
        return Collections.emptyList();
    }

    @Override
    public List<FileInfo> getFilesInBucket(String bucket) {
        return Collections.emptyList();
    }

    @Override
    public void uploadFile(String bucket, String objectName, InputStream inputStream) {

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
