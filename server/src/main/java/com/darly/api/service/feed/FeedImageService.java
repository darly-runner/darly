package com.darly.api.service.feed;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedImageService {
    void createFeedImage(Long feedId, List<MultipartFile> feedImageList);
}
