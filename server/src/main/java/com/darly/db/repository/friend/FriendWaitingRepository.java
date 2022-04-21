package com.darly.db.repository.friend;

import com.darly.db.entity.friend.Friend;
import com.darly.db.entity.friend.FriendWaiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendWaitingRepository  extends JpaRepository<FriendWaiting, Long> {
    boolean existsByFriendOneAndFriendTwo_UserId(Long userId, Long friendId);
}
