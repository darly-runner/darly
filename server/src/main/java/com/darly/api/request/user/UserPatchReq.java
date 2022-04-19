package com.darly.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserPatchRequest")
public class UserPatchReq {
    @ApiModelProperty(name="userNickname", example="김싸피")
    private String userNickname;
    @ApiModelProperty(name="userImage", example="userImageURL")
    private String userImage;
}
