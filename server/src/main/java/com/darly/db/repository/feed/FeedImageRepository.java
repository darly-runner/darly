package com.darly.db.repository.feed;

import com.darly.db.entity.feed.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {
    List<FeedImage> findByFeed_FeedId(Long feedId);
}
