package com.darly.api.response.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventEntity {
    private Long eventId;
    private String eventTitle;
    private String eventContent;
    private String eventImage;
    private String eventDate;

    @Builder
    public EventEntity(Long eventId, String eventTitle, String eventContent, String eventImage, String eventDate) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImage = eventImage;
        this.eventDate = eventDate;
    }
}
