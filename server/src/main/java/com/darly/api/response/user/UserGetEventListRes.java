package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.event.Event;
import com.darly.db.entity.event.UserEvent;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel("UserGetEventListResponse")
public class UserGetEventListRes extends BaseResponseBody {

    private List<EventEntity> events;

    @Builder
    public UserGetEventListRes(Integer statusCode, String message, List<UserEvent> events) {
        super(statusCode, message);
        this.events = new ArrayList<>();
        for (UserEvent event : events) {
            Event userEvent = event.getUserEventId().getEvent();
            this.events.add(EventEntity.builder()
                    .eventId(userEvent.getEventId())
                    .eventTitle(userEvent.getEventTitle())
                    .eventContent(userEvent.getEventContent())
                    .eventImage(userEvent.getEventImage())
                    .eventDate(new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date(userEvent.getEventDate() * 1000)))
                    .build());
        }
    }
}
