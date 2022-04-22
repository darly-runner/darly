package com.darly.api.request.event;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel("EventPostRequest")
public class EventPostReq {
    @ApiModelProperty(name="eventTitle", example="이벤트1")
    private String eventTitle;
    @ApiModelProperty(name="eventContent", example="이벤트내용1")
    private String eventContent;
    @ApiModelProperty(name="eventImage", example="이벤트이미지1")
    private String eventImage;

    @Builder
    public EventPostReq(String eventTitle, String eventContent, String eventImage) {
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImage = eventImage;
    }
}
