package com.yxs.oss.service;

import com.yxs.oss.adapter.StorageAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private StorageAdapter storageAdapter;


    FileService(StorageAdapter storageAdapter){
        this.storageAdapter = storageAdapter;
    }

    /**
     * 列出所有桶
     */
    public List<String> getAllBucket() {
        return storageAdapter.getAllBucket();
    }

    /**
     * 获取文件路径
     */
    public String getUrl(String bucketName,String objectName) {
        return storageAdapter.getFileUrl(bucketName,objectName);
    }

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile uploadFile, String bucket, String objectName){

        UUID uuid = UUID.randomUUID();
        String name = objectName+"/"+uuid.toString()+ uploadFile.getOriginalFilename();
        storageAdapter.uploadFile(uploadFile,bucket,name);
        return storageAdapter.getFileUrl(bucket, name);
    }

    /**
     * 创建桶
     */
    public void createBucket(String bucket) {
        storageAdapter.createBucket(bucket);
    }

    /**
     * 列出当前桶下的所有文件
     */
}
