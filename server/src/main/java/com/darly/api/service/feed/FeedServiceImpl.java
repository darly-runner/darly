package com.darly.api.service.feed;

import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.user.User;
import com.darly.db.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

@Service("feedService")
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;

    @Override
    public void deleteByCrewId(Long crewId) {
        feedRepository.deleteByCrewId(crewId);
    }

    @Override
    public Feed createFeed(Long userId, String feedTitle, String feedContent) {
        return feedRepository.save(Feed.builder()
                .user(User.builder().userId(userId).build())
                .feedTitle(feedTitle)
                .feedContent(feedContent)
                .feedDate(getTimestamp())
                .build());
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

}
