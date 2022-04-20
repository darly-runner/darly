package com.darly.api.service.event;


import com.darly.api.request.event.EventPostReq;
import com.darly.db.entity.Event;
import com.darly.db.entity.EventList;

import java.util.List;

public interface EventService {
    List<EventList> getEventList();

    Event createEvent(EventPostReq eventPostReq, Long userId);
}
