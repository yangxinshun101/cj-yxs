package com.yxs.oss.service;

import com.yxs.oss.adapter.StorageAdapter;
import org.springframework.stereotype.Service;

@Service
public class MinioService {

    private StorageAdapter storageAdapter;


    MinioService(StorageAdapter storageAdapter){
        this.storageAdapter = storageAdapter;
    }



}
