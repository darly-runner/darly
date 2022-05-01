package com.darly.db.repository.feed;

import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.feed.FeedMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from tb_feed " +
            "where crew_id = :crewId ", nativeQuery = true)
    void deleteByCrewId(@Param(value = "crewId") Long crewId);

    @Query(value = "select f.feed_id as feedId, ANY_VALUE(fi.feed_image) as feedImage from tb_feed f " +
            "inner join tb_feed_image fi " +
            "on f.feed_id = fi.feed_id " +
            "where f.crew_id = :crewId " +
            "group by f.feed_id " +
            "order by f.feed_id desc ",
            countQuery = "select count(f.feed_id) from tb_feed f \n" +
                    "where f.crew_id = :crewId\n" +
                    "group by f.feed_id",
            nativeQuery = true)
    Page<FeedMapping> findByCrewId(@Param("crewId")Long crewId, Pageable page);

    List<Feed> findByCrew_CrewId(Long crewId);
}
