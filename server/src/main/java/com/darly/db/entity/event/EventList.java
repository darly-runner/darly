package com.darly.db.entity.event;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * SELECT event_title, event_content, event_image, event_date, user_nickname from tb_event, tb_user where tb_event.user_id = tb_user.user_id;
 * 위의 query문으로 만들어진 테이블을 임시적으로 저장하기 위한 entity
 * */
@Getter
public class EventList {
    private Long eventId;
    private String eventTitle;
    private String userNickname;
    private String eventImage;
    private Long eventDate;

    public EventList(Long eventId, String eventTitle, String userNickname, String eventImage, Long eventDate) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.userNickname = userNickname;
        this.eventImage = eventImage;
        this.eventDate = eventDate;
    }
}
