package com.yxs.oss.entity;

import lombok.Data;

@Data
public class FileInfo {

    private String objectName;
    private String etag;
    private boolean isDirectory;
}
