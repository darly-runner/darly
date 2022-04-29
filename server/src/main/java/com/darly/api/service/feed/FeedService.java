package com.darly.api.service.feed;

import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.feed.FeedMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedService {
    void deleteByCrewId(Long crewId);
    Feed createFeed(Long userId, Long crewId, String feedTitle, String feedContent);
    Page<FeedMapping> getFeedList(Long crewId, Pageable page);
}
