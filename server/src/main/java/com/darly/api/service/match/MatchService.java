package com.darly.api.service.match;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.MatchTitleMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchService {
    void setNullByCrewId(Long crewId);
    Page<Match> getCrewMatchList(Long crewId, Pageable page);
    Match createCrewMatch(Long crewId, Long userId, MatchCreatePostReq matchCreatePostReq);
}
