package com.darly.db.repository.feed;

import com.darly.db.entity.feed.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {
    //    <T>List<T> findByFeed_FeedId(Long feedId, Class<T> type);
    List<FeedImage> findByFeed_FeedId(Long feedId);

    @Query(value = "select feed_image from tb_feed_image " +
            "where feed_id = :feedId", nativeQuery = true)
    List<String> findFeedImageTitleByFeedId(@Param("feedId") Long feedId);
}
