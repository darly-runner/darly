package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel("UserNicknamePostResponse")
public class UserNicknamePostRes extends BaseResponseBody {
    private Boolean isOk;

    @Builder
    public UserNicknamePostRes(Integer statusCode, String message, Boolean isOk) {
        super(statusCode, message);
        this.isOk = isOk;
    }
}
