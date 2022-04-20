package com.darly.api.service.event;


import com.darly.api.request.event.EventPostReq;
import com.darly.db.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> getEventList();

    Event createEvent(EventPostReq eventPostReq, Long userId);
}
