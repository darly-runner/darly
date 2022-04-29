package com.darly.db.repository.friend;

import com.darly.db.entity.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
//    <T> List<T> findByFriendOne(Long friendId, Class<T> type);
    boolean existsByFriendOne_UserIdAndFriendTwo_UserId(Long userId, Long friendId);
    Optional<Friend> getByFriendOne_UserIdAndFriendTwo_UserId(Long userId, Long friendId);
    long countByFriendOne_UserId(Long userId);
}
