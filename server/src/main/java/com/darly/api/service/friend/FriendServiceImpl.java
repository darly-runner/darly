package com.darly.api.service.friend;

import com.darly.db.entity.friend.Friend;
import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.entity.friend.FriendWaiting;
import com.darly.db.repository.friend.FriendRepository;
import com.darly.db.repository.friend.FriendRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("friendService")
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final FriendRepositorySupport friendRepositorySupport;

    @Override
    public List<FriendTitleMapping> getFriendList(Long userId) {
        return friendRepositorySupport.findFriendTitleList(userId);
    }

    @Override
    public boolean isFriend(Long userId, Long friendId) {
        return friendRepository.existsByFriendOneAndFriendTwo_UserId(userId, friendId);
    }

    @Override
    public void createFriend(Long userId, Long friendId) {
        friendRepository.save(Friend.builder().friendOne(userId).friendTwo(friendId).build());
        friendRepository.save(Friend.builder().friendOne(friendId).friendTwo(userId).build());
    }

    @Override
    public boolean deleteFriend(Long userId, Long friendId) {
        Optional<Friend> friend = friendRepository.getByFriendOneAndFriendTwo_UserId(userId, friendId);
        if (friend.isPresent())
            friendRepository.delete(friend.get());
        friend = friendRepository.getByFriendOneAndFriendTwo_UserId(friendId, userId);
        if (friend.isPresent()) {
            friendRepository.delete(friend.get());
            return true;
        }
        return false;
    }
}
