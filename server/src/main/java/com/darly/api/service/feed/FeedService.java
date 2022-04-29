package com.darly.api.service.feed;

import com.darly.db.entity.feed.Feed;

public interface FeedService {
    void deleteByCrewId(Long crewId);
    Feed createFeed(Long userId, String feedTitle, String feedContent);
}
