package com.darly.api.service.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.darly.common.model.s3.FileFolder;

import java.io.InputStream;

/**
 * UploadService에 의존관계를 맺음으로써 AWS S3가 아닌 다른 서비스를 이용하거나
 * 다른방법으로 업로드를 구현할 때 유연하게 변경 가능
 */
public interface FileService {
    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);
    void deleteFile(String fileName);
    String getFileUrl(String fileName);
    String getFileFolder(FileFolder fileFolder);
}
