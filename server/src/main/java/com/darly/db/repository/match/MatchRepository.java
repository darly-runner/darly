package com.darly.db.repository.match;

import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.MatchTitleMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Transactional
    @Modifying
    @Query(value = "update tb_match " +
            "set crew_id = null " +
            "where crew_id = :crewId", nativeQuery = true)
    void setNullByCrewId(@Param("crewId") Long crewId);

//    @Query(value = "select u.user_nickname as hostNickname, u.user_image as hostImage, m.match_id as matchId, m.match_title as matchTitle, m.match_max_person as matchMaxPerson, m.match_cur_person as matchCurPerson, m.match_goal_distance as matchGoalDistance, m.match_date as matchDate, m.match_start_time as matchStartTime, m.match_status as matchStatus \n" +
//            "from tb_match m\n" +
//            "inner join tb_user u \n" +
//            "on m.host_id = u.user_id\n" +
//            "where m.crew_id = :crewId\n" +
//            "and m.match_status != 'U'\n" +
//            "order by match_status desc",
//            countQuery = "select count(u.user_nickname)" +
//                    "from tb_match m\n" +
//                    "inner join tb_user u \n" +
//                    "on m.host_id = u.user_id\n" +
//                    "where m.crew_id = :crewId\n" +
//                    "and m.match_status != 'U'",
//            nativeQuery = true)
    Page<Match> findByCrew_CrewId(Long crewId, Pageable page);

    Match findByMatchId(Long Id);
}
