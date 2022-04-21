package com.darly.api.service.friend;

public interface FriendWaitingService {
    boolean isFriendWaiting(Long userId, Long friendId);
    void createFriendWaiting(Long userId, Long friendId);
}
