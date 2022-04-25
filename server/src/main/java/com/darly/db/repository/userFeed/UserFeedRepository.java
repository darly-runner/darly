package com.darly.db.repository.userFeed;

import com.darly.db.entity.userFeed.UserFeed;
import com.darly.db.entity.userFeed.UserFeedImageMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {
    @Query(value = "select user_feed_image from tb_user_feed where user_id = :userId", nativeQuery = true)
    Page<String> findByUserId(@Param(value = "userId") Long userId, Pageable pageRequest);
}
