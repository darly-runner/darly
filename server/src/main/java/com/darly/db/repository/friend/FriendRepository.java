package com.darly.db.repository.friend;

import com.darly.db.entity.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
//    <T> List<T> findByFriendOne(Long friendId, Class<T> type);
}
