package com.darly.api.request.event;

import com.darly.db.entity.Event;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel("EventPatchRequest")
public class EventPatchReq {
    @ApiModelProperty(name="eventTitle", example="이벤트제목1")
    String eventTitle;
    @ApiModelProperty(name="eventContent", example="이벤트내용1")
    String eventContent;
    @ApiModelProperty(name="eventImage", example="이벤트이미지1")
    String eventImage;

    public static Event ofPatch(Event event, String eventTitle, String eventContent, String eventImage) {
        return Event.builder()
                .eventId(event.getEventId())
                .user(event.getUser())
                .eventTitle(eventTitle)
                .eventContent(eventContent)
                .eventImage(eventImage)
                .eventDate(event.getEventDate())
                .build();
    }

    @Builder
    public EventPatchReq(String eventTitle, String eventContent, String eventImage) {
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImage = eventImage;
    }
}
