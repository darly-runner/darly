package com.darly.api.service.event;

import com.darly.db.entity.event.UserEvent;

import java.util.List;

public interface UserEventService {
    List<UserEvent> getUserEventList(Long userId);
}
