package com.darly.api.service.event;


import com.darly.api.request.event.EventPatchReq;
import com.darly.api.request.event.EventPostReq;
import com.darly.db.entity.event.Event;
import com.darly.db.entity.event.EventList;
import com.darly.db.entity.event.EventOne;

import java.util.List;

public interface EventService {
    List<EventList> getEventList();

    Event createEvent(EventPostReq eventPostReq, Long userId);

    EventOne getEvent(Long eventId);

    void deleteEvent(Long eventId);

    void patchEvent(EventPatchReq eventPatchReq, Long eventId);
}
