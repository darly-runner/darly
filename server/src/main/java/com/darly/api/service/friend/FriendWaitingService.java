package com.darly.api.service.friend;

import com.darly.db.entity.friend.FriendTitleMapping;

import java.util.List;

public interface FriendWaitingService {
    boolean isFriendWaiting(Long userId, Long friendId);
    void createFriendWaiting(Long userId, Long friendId);
    List<FriendTitleMapping> getFriendWaitingList(Long userId);
}
