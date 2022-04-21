package com.darly.db.entity;

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
    private LocalDateTime eventDate;

    @Builder
    public EventOne(Long eventId, String eventTitle, String eventContent, String userNickname, String eventImage, LocalDateTime eventDate) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.userNickname = userNickname;
        this.eventImage = eventImage;
        this.eventDate = eventDate;
    }
}
