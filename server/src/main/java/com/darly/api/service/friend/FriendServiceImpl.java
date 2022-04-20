package com.darly.api.service.friend;

import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.repository.friend.FriendRepository;
import com.darly.db.repository.friend.FriendRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("friendService")
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final FriendRepositorySupport friendRepositorySupport;

    @Override
    public List<FriendTitleMapping> getFriendList(Long userId) {
        return friendRepositorySupport.findFriendTitleList(userId);
    }
}
