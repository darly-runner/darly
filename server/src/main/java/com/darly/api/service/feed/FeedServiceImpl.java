package com.darly.api.service.feed;

import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.feed.FeedMapping;
import com.darly.db.entity.user.User;
import com.darly.db.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service("feedService")
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;

    @Override
    public void deleteByCrewId(Long crewId) {
        List<Feed> feedList = feedRepository.findByCrew_CrewId(crewId);
        for (Feed feed: feedList) {
            feedImageService.deleteByFeedId(feed.getFeedId());
            feedRepository.delete(feed);
        }
    }

    @Override
    public Feed createFeed(Long userId, Long crewId, String feedTitle, String feedContent) {
        return feedRepository.save(Feed.builder()
                .user(User.builder().userId(userId).build())
                .crew(Crew.builder().crewId(crewId).build())
                .feedTitle(feedTitle)
                .feedContent(feedContent)
                .feedDate(getTimestamp())
                .build());
    }

    @Override
    public Page<FeedMapping> getFeedList(Long crewId, Pageable page) {
        return feedRepository.findByCrewId(crewId, page);
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

}