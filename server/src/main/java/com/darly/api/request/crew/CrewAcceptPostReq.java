package com.darly.api.request.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("CrewAcceptPostRequest")
public class CrewAcceptPostReq {
    @ApiModelProperty(name = "userId", example = "1")
    private Long userId;
}
