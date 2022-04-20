package com.darly.db.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventList {
    private String eventTitle;
    private String eventContent;
    private String eventImage;
    private String userNickname;
    private LocalDateTime eventDate;

    public EventList(String eventTitle, String eventContent, String eventImage, String userNickname, LocalDateTime eventDate) {
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImage = eventImage;
        this.userNickname = userNickname;
        this.eventDate = eventDate;
    }
}
