package com.darly.db.repository.feed;

import com.darly.db.entity.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from f, cf " +
            "using tb_feed as f " +
            "left join tb_crew_feed as cf " +
            "on f.feed_id = cf.feed_id " +
            "where cf.crew_id = :crewId ", nativeQuery = true)
    void deleteByCrewId(@Param(value = "crewId") Long crewId);
}
