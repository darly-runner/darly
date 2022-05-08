package com.darly.api.request.friend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("FriendSearchPostReq")
public class FriendSearchPostReq {
    @ApiModelProperty(name="userNickname", example="userNickname")
    private String userNickname;
}
