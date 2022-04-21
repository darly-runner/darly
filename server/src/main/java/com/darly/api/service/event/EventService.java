package com.darly.api.service.event;


import com.darly.api.request.event.EventPostReq;
import com.darly.db.entity.Event;
import com.darly.db.entity.EventList;
import com.darly.db.entity.EventOne;

import java.util.List;

public interface EventService {
    List<EventList> getEventList();

    Event createEvent(EventPostReq eventPostReq, Long userId);

    EventOne getEvent(Long eventId);

    void deleteEvent(Long eventId);
}
