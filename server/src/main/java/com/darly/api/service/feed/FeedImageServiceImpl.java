package com.darly.api.service.feed;

import com.darly.api.service.file.FileProcessService;
import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.feed.FeedImage;
import com.darly.db.repository.feed.FeedImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("feedImageService")
@RequiredArgsConstructor
public class FeedImageServiceImpl implements FeedImageService {
    private final FeedImageRepository feedImageRepository;
    private final FileProcessService fileProcessService;

    @Override
    public void createFeedImage(Long feedId, MultipartFile feedImage) {
        Feed feed = Feed.builder().feedId(feedId).build();
        feedImageRepository.save(FeedImage.builder()
                .feed(feed)
                .feedImage(fileProcessService.uploadImage(feedImage, "feed"))
                .build());
    }

    @Override
    public void deleteByFeedId(Long feedId) {
        List<FeedImage> feedImageList = feedImageRepository.findByFeed_FeedId(feedId);
        for (FeedImage feedImage : feedImageList) {
            fileProcessService.deleteImage(feedImage.getFeedImage());
            feedImageRepository.delete(feedImage);
        }
    }

    @Override
    public List<String> getFeedImages(Long feedId) {
        return feedImageRepository.findFeedImageTitleByFeedId(feedId);
    }
}
