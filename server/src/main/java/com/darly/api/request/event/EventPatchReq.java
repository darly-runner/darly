package com.darly.api.request.event;

import com.darly.db.entity.event.Event;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel("EventPatchRequest")
public class EventPatchReq {
    @ApiModelProperty(name="eventTitle", example="이벤트제목1")
    private String eventTitle;
    @ApiModelProperty(name="eventContent", example="이벤트내용1")
    private String eventContent;
    @Nullable
    @ApiModelProperty(name="eventImage", example="이벤트이미지1")
    private MultipartFile eventImage;

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
}
