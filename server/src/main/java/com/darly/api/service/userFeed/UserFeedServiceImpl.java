package com.darly.api.service.userFeed;

import com.darly.db.repository.userFeed.UserFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("userFeedService")
@RequiredArgsConstructor
public class UserFeedServiceImpl implements UserFeedService{
    private final UserFeedRepository userFeedRepository;

    @Override
    public Page<String> getUserFeedList(Long friendId, Pageable page) {
        return userFeedRepository.findByUserId(friendId, page);
    }
}
