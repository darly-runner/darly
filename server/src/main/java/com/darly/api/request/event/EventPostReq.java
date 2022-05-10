package com.darly.api.request.event;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel("EventPostRequest")
public class EventPostReq {
    @ApiModelProperty(name="eventTitle", example="이벤트1")
    private String eventTitle;
    @ApiModelProperty(name="eventContent", example="이벤트내용1")
    private String eventContent;
    @Nullable
    @ApiModelProperty(name="eventImage", example="이벤트이미지1")
    private MultipartFile eventImage;
}
