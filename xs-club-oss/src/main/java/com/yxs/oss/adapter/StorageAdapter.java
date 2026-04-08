package com.yxs.oss.adapter;

import com.yxs.oss.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface StorageAdapter {

    List<String> getAllBucket();

    List<FileInfo> getFilesInBucket(String bucket);

    void uploadFile(MultipartFile uploadFile, String bucket, String objectName);

    void createBucket(String bucket);

    InputStream downloadFile(String bucket, String objectName);

    void deleteFile(String bucket, String objectName);

    void deleteBucket(String bucket);

    String getFileUrl(String bucket, String objectName);

}
