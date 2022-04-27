package com.darly.api.service.match;

import com.darly.db.repository.match.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("matchService")
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{
    private final MatchRepository matchRepository;

    @Override
    public void setNullByCrewId(Long crewId) {
        matchRepository.setNullByCrewId(crewId);
    }
}
