package com.darly.api.service.feed;

import com.darly.api.request.feed.FeedUpdatePatchReq;
import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.feed.FeedMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FeedService {
    void deleteByCrewId(Long crewId);
    Feed createFeed(Long userId, Long crewId, String feedTitle, String feedContent);
    Page<FeedMapping> getFeedList(Long crewId, Pageable page);
    Optional<Feed> getFeedDetail(Long feedId);
    boolean updateFeed(Long feedId, FeedUpdatePatchReq feedUpdatePatchReq);
    boolean deleteByFeedId(Long feedId);
}
