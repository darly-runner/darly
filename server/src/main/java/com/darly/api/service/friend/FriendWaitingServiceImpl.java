package com.darly.api.service.friend;

import com.darly.db.entity.friend.FriendWaiting;
import com.darly.db.repository.friend.FriendWaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("friendWaitingService")
@RequiredArgsConstructor
public class FriendWaitingServiceImpl implements FriendWaitingService {
    private final FriendWaitingRepository friendWaitingRepository;

    @Override
    public boolean isFriendWaiting(Long userId, Long friendId) {
        return friendWaitingRepository.existsByFriendOneAndFriendTwo_UserId(userId, friendId);
    }

    @Override
    public void createFriendWaiting(Long userId, Long friendId) {
        friendWaitingRepository.save(FriendWaiting.builder().friendOne(userId).friendTwo(friendId).build());
    }
}
