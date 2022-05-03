package com.darly.db.repository.friend;

import com.darly.db.entity.friend.Friend;
import com.darly.db.entity.friend.FriendWaiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendWaitingRepository  extends JpaRepository<FriendWaiting, Long> {
    boolean existsByFriendOne_UserIdAndFriendTwo_UserId(Long userId, Long friendId);
    Optional<FriendWaiting> getByFriendOne_UserIdAndFriendTwo_UserId(Long userId, Long friendId);
}
