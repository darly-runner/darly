package com.darly.api.service.event;

import com.darly.api.request.event.EventPatchReq;
import com.darly.api.request.event.EventPostReq;
import com.darly.api.service.file.FileProcessService;
import com.darly.db.entity.event.Event;

import com.darly.db.entity.event.EventOne;
import com.darly.db.entity.user.User;
import com.darly.db.repository.event.EventRepository;
import com.darly.db.repository.event.EventRepositorySupport;
import com.darly.db.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service("EventService")
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepositorySupport eventRepositorySupport;

    @Autowired
    private FileProcessService fileProcessService;

    @Override
    public List getEventList() {
        return eventRepositorySupport.findEvents();
    }

    @Override
    public Event createEvent(EventPostReq eventPostReq, Long userId) {
        User user = userRepository.findById(userId).get();
        LocalDateTime today = LocalDateTime.now();
        String url = null;
        if (eventPostReq.getEventImage() != null) {
            url = fileProcessService.uploadImage(eventPostReq.getEventImage(), "event");
        }

        Event event = Event.builder()
                        .eventTitle(eventPostReq.getEventTitle())
                        .eventContent(eventPostReq.getEventContent())
                        .eventImage(url)
                        .user(user)
                        .eventDate(getTimestamp(today))
                        .build();

        return eventRepository.save(event);
    }

    @Override
    public EventOne getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).get();

        String eventTitle = event.getEventTitle();
        String eventContent = event.getEventContent();
        String eventImage = event.getEventImage();
        String eventDate =new SimpleDateFormat("yyyy/MM/dd a KK:mm").format(new Date(event.getEventDate() * 1000));
        String userNickname = event.getUser().getUserNickname();

        return new EventOne(eventId, eventTitle, eventContent, userNickname, eventImage, eventDate);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).get();

        if(event.getEventImage() != null) {
            fileProcessService.deleteImage(event.getEventImage());
        }
        eventRepository.deleteById(eventId);
    }

    @Override
    public void patchEvent(EventPatchReq eventPatchReq, Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        String url = null;

        if(eventPatchReq.getEventImage() != null) {
            url = fileProcessService.uploadImage(eventPatchReq.getEventImage(), "event");
        }

        Event patchEvent = EventPatchReq.ofPatch(event, eventPatchReq.getEventTitle(),
                eventPatchReq.getEventContent(), url);

        eventRepository.save(patchEvent);
    }

    private Long getTimestamp(LocalDateTime today) {
        return Timestamp.valueOf(today).getTime() / 1000;
    }
}
