package com.darly.db.repository.crew;

import com.darly.db.entity.crew.CrewFeed;
import com.darly.db.entity.crew.CrewFeedId;
import com.darly.db.entity.feed.FeedMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CrewFeedRepository extends JpaRepository<CrewFeed, CrewFeedId> {
    @Query(value = "select f.feed_id as feedId, fi.feed_image as feedImage from tb_crew_feed cf " +
            "inner join tb_feed f " +
            "on f.feed_id = cf.feed_id " +
            "inner join tb_feed_image fi " +
            "on f.feed_id = fi.feed_id " +
            "where cf.crew_id = :crewId " +
            "group by f.feed_id ",
            countQuery = "select count(fi.feed_id) from tb_feed_image fi\n" +
                    "inner join tb_crew_feed cf\n" +
                    "on cf.feed_id = fi.feed_id\n" +
                    "where cf.crew_id = :crewId\n" +
                    "group by fi.feed_id",
            nativeQuery = true)
    Page<FeedMapping> findByCrewId(@Param("crewId") Long crewId, Pageable page);
}
