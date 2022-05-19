package com.darly.api.controller;

import com.darly.api.service.app.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class AppController {

    private final AppService appService;

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile() {
        // Load file as Resource
        String fileName = "darly.apk";
        Resource file = appService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
//        }

        // Fallback to the default content type if type could not be determined
//        if (contentType == null) {
        contentType = "application/octet-stream";
//        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}