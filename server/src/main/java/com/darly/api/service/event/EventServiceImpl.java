package com.darly.api.service.event;

import com.darly.api.request.event.EventPostReq;
import com.darly.api.response.event.EventsGetRes;
import com.darly.db.entity.Event;
import com.darly.db.entity.EventList;
import com.darly.db.entity.User;
import com.darly.db.repository.event.EventRepository;
import com.darly.db.repository.event.EventRepositorySupport;
import com.darly.db.repository.user.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("EventService")
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepositorySupport eventRepositorySupport;

    @Override
    public List getEventList() {
        return eventRepositorySupport.findEvents();
    }

    @Override
    public Event createEvent(EventPostReq eventPostReq, Long userId) {
        User user = userRepository.findById(userId).get();


        Event event = Event.builder()
                        .eventTitle(eventPostReq.getEventTitle())
                        .eventContent(eventPostReq.getEventContent())
                        .eventImage(eventPostReq.getEventImage())
                        .user(user)
                        .eventDate(LocalDateTime.now())
                        .build();

        return eventRepository.save(event);
    }
}
