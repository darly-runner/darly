package com.darly.api.service.match;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.request.match.MatchPatchReq;
import com.darly.api.response.match.MatchInRes;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.MatchRUser;
import com.darly.db.entity.user.UserNowMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchService {
    void setNullByCrewId(Long crewId);
    Page<Match> getCrewMatchList(Long crewId, Pageable page);
    Match createCrewMatch(Long crewId, Long userId, MatchCreatePostReq matchCreatePostReq);

    MatchInRes getMatchInfo(Long matchId, Long userId);

    Character matchOut(Long matchId, Long userId);
    void patchMatchInfo(Long matchId, MatchPatchReq matchPatchReq);

    void userReady(Long matchId, Long userId, Character isReady);
    void matchStart(Long matchId);

    MatchInRes getMatchRefresh(Long matchId, Long userId);

    List<MatchRUser> randomMatch(Long userId);

    List<UserNowMapping> nowUsers(Long matchId);
}
