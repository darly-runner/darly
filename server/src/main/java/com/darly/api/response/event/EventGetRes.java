package com.darly.api.response.event;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.Event;
import com.darly.db.entity.EventOne;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@ApiModel("EventGetResponse")
public class EventGetRes extends BaseResponseBody {
    @ApiModelProperty(name="eventId", example="1")
    private Long eventId;
    @ApiModelProperty(name="userNickname", example="흐앙쥬금털썩")
    private String userNickname;
    @ApiModelProperty(name="eventTitle", example="이벤트제목1")
    private String eventTitle;
    @ApiModelProperty(name="eventContent", example="이벤트내용1")
    private String eventContent;
    @ApiModelProperty(name="eventImage", example="이벤트이미지1")
    private String eventImage;
    @ApiModelProperty(name="LocalDateTime", example="시간")
    private String eventDate;

    public static EventGetRes of(EventOne eventOne, Integer statusCode, String message) {
        return EventGetRes.builder()
                .statusCode(statusCode)
                .message(message)
                .eventId(eventOne.getEventId())
                .userNickname(eventOne.getUserNickname())
                .eventTitle(eventOne.getEventTitle())
                .eventImage(eventOne.getEventImage())
                .eventDate(eventOne.getEventDate())
                .eventContent(eventOne.getEventContent())
                .build();
    }

    @Builder
    public EventGetRes(Integer statusCode, String message, Long eventId, String userNickname,
                       String eventTitle, String eventContent, String eventImage, String eventDate) {
        super(statusCode, message);
        this.eventId = eventId;
        this.userNickname = userNickname;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImage = eventImage;
        this.eventDate = eventDate;
    }
}
