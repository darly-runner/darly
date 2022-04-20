package com.darly.api.response.event;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.EventList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel("EventsGetResponse")
public class EventsGetRes extends BaseResponseBody {

    @ApiModelProperty(name="eventList", example="event1, event2, event3 ...")
    List<EventList> eventList;

    public static EventsGetRes of(List<EventList> eventList, Integer statusCode, String message) {
        return EventsGetRes.builder()
                .statusCode(statusCode)
                .message(message)
                .eventList(eventList)
                .build();
    }

    @Builder
    public EventsGetRes(Integer statusCode, String message, List<EventList> eventList) {
        super(statusCode, message);
        this.eventList = eventList;
    }
}
