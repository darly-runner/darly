package com.darly.api.service.match;

import com.darly.db.entity.match.MatchResultMapping;
import com.darly.db.repository.match.MatchResultRepository;
import com.darly.db.repository.match.MatchResultRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("matchResultService")
@RequiredArgsConstructor
public class MatchResultServiceImpl implements MatchResultService{
    private final MatchResultRepository matchResultRepository;
    private final MatchResultRepositorySupport matchResultRepositorySupport;

    @Override
    public List<MatchResultMapping> getMatchResultList(Long matchId) {
        return matchResultRepositorySupport.getMatchResultList(matchId);
    }
}
