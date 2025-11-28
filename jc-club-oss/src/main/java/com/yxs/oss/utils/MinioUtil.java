package com.yxs.oss.utils;

import com.yxs.oss.entity.FileInfo;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    /**
     * 新建Bucket
     */
    @SneakyThrows
    public void createBucket(String bucket) {
        boolean exists = minioClient.bucketExists(new BucketExistsArgs().builder().bucket(bucket).build());

        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    /**
     * 上次文件
     */
    @SneakyThrows
    public void uploadFile(String bucket, String objectName, InputStream inputStream) {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(objectName).
                stream(inputStream, -1, 5242889L).build());
    }

    /**
     * 列出所有Bucket
     */
    @SneakyThrows
    public List<String> getAllBucket() {
        List<Bucket> bucketList = minioClient.listBuckets();
        return bucketList.stream().map(Bucket::name).collect(Collectors.toList());
    }

    /**
     * 列出当前Bucket下的所有文件
     */
    @SneakyThrows
    public List<FileInfo> getFilesInBucket(String bucket) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build());

        List<FileInfo> fileInfoList = new ArrayList<>();

        for (Result<Item> result : results) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setDirectory(result.get().isDir());
            fileInfo.setEtag(result.get().etag());
            fileInfo.setObjectName(result.get().objectName());
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }

    /**
     * 下载文件
     */
    @SneakyThrows
    public InputStream downloadFile(String bucket, String objectName) {
        GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(objectName).build());

        return object;
    }

    /**
     * 删除Bucket
     */
    @SneakyThrows
    public void deleteBucket(String bucket) {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
    }

    /**
     * 删除文件
     */
    @SneakyThrows
    public void deleteFile(String bucket, String objectName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
    }

    /**
     * 获取URl地址
     */
    @SneakyThrows
    public String getFileUrl(String bucket, String objectName){
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().
                bucket(bucket).object(objectName).method(Method.GET).build());
        return presignedObjectUrl;
    }


}
