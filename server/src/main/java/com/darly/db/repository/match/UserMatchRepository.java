package com.darly.db.repository.match;

import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.match.UserMatchId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMatchRepository extends JpaRepository<UserMatch, UserMatchId> {
    List<UserMatch> findAllByUserMatchId_Match_MatchId(Long id);
}
