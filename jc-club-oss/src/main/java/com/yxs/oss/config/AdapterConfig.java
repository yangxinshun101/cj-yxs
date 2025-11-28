package com.yxs.oss.config;

import com.yxs.oss.adapter.AliyunAdapter;
import com.yxs.oss.adapter.MinioAdapter;
import com.yxs.oss.adapter.StorageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class AdapterConfig {

    @Value("${oss.adapter.type}")
    private String adapterType;


    public String getAdapterType() {
        return adapterType;
    }

    @Bean
    public StorageAdapter storageAdapter() {
        if ("minio".equals(adapterType)) {
            return new MinioAdapter();
        } else if ("aliyun".equals(adapterType)) {
            return new AliyunAdapter();
        } else {
            throw new IllegalArgumentException("未找到对应的文件存储处理器");

        }
    }
}
