package com.darly.api.service.friend;

import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.entity.friend.FriendWaiting;
import com.darly.db.repository.friend.FriendWaitingRepository;
import com.darly.db.repository.friend.FriendWaitingRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("friendWaitingService")
@RequiredArgsConstructor
public class FriendWaitingServiceImpl implements FriendWaitingService {
    private final FriendWaitingRepository friendWaitingRepository;
    private final FriendWaitingRepositorySupport friendWaitingRepositorySupport;

    @Override
    public boolean isFriendWaiting(Long userId, Long friendId) {
        return friendWaitingRepository.existsByFriendOneAndFriendTwo_UserId(userId, friendId);
    }

    @Override
    public void createFriendWaiting(Long userId, Long friendId) {
        friendWaitingRepository.save(FriendWaiting.builder().friendOne(userId).friendTwo(friendId).build());
    }

    @Override
    public List<FriendTitleMapping> getFriendWaitingList(Long userId) {
        return friendWaitingRepositorySupport.findFriendWaitingTitleList(userId);
    }
}
