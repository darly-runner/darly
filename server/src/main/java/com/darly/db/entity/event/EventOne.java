package com.darly.db.entity.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class EventOne {
    private Long eventId;
    private String eventTitle;
    private String eventContent;
    private String userNickname;
    private String eventImage;
    private String eventDate;

    @Builder
    public EventOne(Long eventId, String eventTitle, String eventContent, String userNickname, String eventImage, String eventDate) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.userNickname = userNickname;
        this.eventImage = eventImage;
        this.eventDate = eventDate;
    }
}
