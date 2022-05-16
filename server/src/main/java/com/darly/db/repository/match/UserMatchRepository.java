package com.darly.db.repository.match;

import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.match.UserMatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMatchRepository extends JpaRepository<UserMatch, UserMatchId> {

    List<UserMatch> findAllByUserMatchId_Match_MatchId(Long id);
    UserMatch findByUserMatchId_Match_MatchIdAndUserMatchId_User_UserId(Long matchId, Long userId);

    void deleteAllByUserMatchId_Match_MatchId(Long id);
    Integer countAllByUserMatchId_Match_MatchId(Long id);
}
