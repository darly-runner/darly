package com.darly.api.service.friend;

import com.darly.db.entity.friend.FriendTitleMapping;

import java.util.List;

public interface FriendService {
    List<FriendTitleMapping> getFriendList(Long userId);
    boolean isFriend(Long userId, Long friendId);
}
