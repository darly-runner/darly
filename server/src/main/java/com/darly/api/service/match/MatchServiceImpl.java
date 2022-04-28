package com.darly.api.service.match;

import com.darly.db.entity.match.MatchTitleMapping;
import com.darly.db.repository.match.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("matchService")
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
//    private final MatchRepositorySupport matchRepositorySupport;

    @Override
    public void setNullByCrewId(Long crewId) {
        matchRepository.setNullByCrewId(crewId);
    }

    @Override
    public Page<MatchTitleMapping> getCrewMatchList(Long crewId, Pageable page) {
        return matchRepository.findByCrewId(crewId, page);
    }
}
