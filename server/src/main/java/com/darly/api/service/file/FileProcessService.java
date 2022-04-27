package com.darly.api.service.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service("fileProcessService")
public class FileProcessService {
    private final FileService s3Service;

    // Multipart를 통해 전송된 파일을 업로드
    public String uploadImage(MultipartFile file, String folder) {
        String fileName = folder + "/"  + createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try (InputStream inputStream = file.getInputStream()) {
            s3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생했습니다 (%s)", file.getOriginalFilename()));
        }
        return s3Service.getFileUrl(fileName);
    }

    // 기존 확장자명을 유지한채 유니크한 파일이름 생성
    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    // 파일의 확장자명 가져오기
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }

    // 파일 삭제
    public void deleteImage(String url) {
        s3Service.deleteFile(getFileName(url));
    }

    private String getFileName(String url) {
        String[] paths = url.split("/");
        return paths[paths.length-2] + "/" + paths[paths.length-1];
    }
}
