package com.darly.api.response.account;

import com.darly.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginPostRes extends BaseResponseBody {
    private String accessToken;

    public static AccountLoginPostRes of(Integer statusCode, String message, String accessToken) {
        AccountLoginPostRes res = new AccountLoginPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        return res;
    }
}
