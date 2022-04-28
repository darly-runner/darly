package com.darly.api.service.crew;

import com.darly.db.entity.feed.FeedMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrewFeedService {
    Page<FeedMapping> getCrewFeedList(Long crewId, Pageable page);
}
