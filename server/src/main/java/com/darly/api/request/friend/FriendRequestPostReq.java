package com.darly.api.request.friend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("FriendRequestPostReq")
public class FriendRequestPostReq {
    @ApiModelProperty(name="userId", example="1")
    private Long userId;
}
