package com.darly.api.request.user;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel("UserNicknamePostRequest")
public class UserNicknamePostReq {
    private String userNickname;
}
