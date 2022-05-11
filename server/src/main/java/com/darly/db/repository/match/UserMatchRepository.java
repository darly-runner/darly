package com.darly.db.repository.match;

import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.match.UserMatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMatchRepository extends JpaRepository<UserMatch, UserMatchId> {
//    @Query(value="select * from tb_user u\n" +
//            "inner join tb_user_match um\n" +
//            "on u.user_id = um.user_id\n" +
//            "where um.match_id = :matchId", nativeQuery = true)
    List<UserMatch> findAllByUserMatchId_Match_MatchId(Long id);
}
