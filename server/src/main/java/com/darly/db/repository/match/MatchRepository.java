package com.darly.db.repository.match;

import com.darly.db.entity.match.Match;
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
}
