package com.darly.api.service.feed;

import com.darly.db.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("feedService")
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;

    @Override
    public void deleteByCrewId(Long crewId) {
        feedRepository.deleteByCrewId(crewId);
    }
}
