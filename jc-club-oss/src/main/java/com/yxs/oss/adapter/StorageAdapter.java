package com.yxs.oss.adapter;

import com.yxs.oss.entity.FileInfo;

import java.io.InputStream;
import java.util.List;

public interface StorageAdapter {

    List<String> getAllBucket();

    List<FileInfo> getFilesInBucket(String bucket);

    void uploadFile(String bucket, String objectName, InputStream inputStream);

    void createBucket(String bucket);

    InputStream downloadFile(String bucket, String objectName);

    void deleteFile(String bucket, String objectName);

    void deleteBucket(String bucket);

    String getFileUrl(String bucket, String objectName);
}
