package com.darly.api.service.crew;

import com.darly.db.entity.feed.FeedMapping;
import com.darly.db.repository.crew.CrewFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("crewFeedService")
@RequiredArgsConstructor
public class CrewFeedServiceImpl implements CrewFeedService {
    private final CrewFeedRepository crewFeedRepository;

    @Override
    public Page<FeedMapping> getCrewFeedList(Long crewId, Pageable page) {
        return crewFeedRepository.findByCrewId(crewId, page);
    }
}
