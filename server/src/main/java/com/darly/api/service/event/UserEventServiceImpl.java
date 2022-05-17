package com.darly.api.service.event;

import com.darly.db.entity.event.UserEvent;
import com.darly.db.entity.user.User;
import com.darly.db.repository.event.UserEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userEventService")
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {
    private final UserEventRepository userEventRepository;

    @Override
    public List<UserEvent> getUserEventList(Long userId) {
        return userEventRepository.findByUserEventId_User(User.builder().userId(userId).build());
    }
}
