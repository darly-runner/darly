package com.darly.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel("UserPostFeedRequest")
@Builder
public class UserPostFeedReq {
    @ApiModelProperty(name="userFeedImage", example="string")
    String userFeedImage;
}
