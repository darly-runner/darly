package com.darly.db.repository.user;

import com.darly.db.entity.user.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findUserBadgesByUser_UserId(Long userId);
}

