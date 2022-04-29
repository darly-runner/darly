package com.darly.api.service.match;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.MatchTitleMapping;
import com.darly.db.entity.user.User;
import com.darly.db.repository.match.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

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

    @Override
    public Match createCrewMatch(Long crewId, Long userId, MatchCreatePostReq matchCreatePostReq) {
        return matchRepository.save(Match.builder()
                .crew(Crew.builder().crewId(crewId).build())
                .host(User.builder().userId(userId).build())
                .matchTitle(matchCreatePostReq.getMatchTitle())
                .matchMaxPerson(matchCreatePostReq.getMatchMaxPerson())
                .matchGoalDistance(matchCreatePostReq.getMatchGoalDistance())
                .matchDate(getTimestamp())
                .matchStatus('W')
                .build());
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

}
