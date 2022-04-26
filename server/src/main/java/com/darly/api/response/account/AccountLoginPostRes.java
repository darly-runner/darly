package com.darly.api.response.account;

import com.darly.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AccountLoginPostResponse")
public class AccountLoginPostRes extends BaseResponseBody {
    @ApiModelProperty(name="accessToken", example="Bearer token")
    private String accessToken;

    public static AccountLoginPostRes of(Integer statusCode, String message, String accessToken) {
        AccountLoginPostRes res = new AccountLoginPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        return res;
    }
}
