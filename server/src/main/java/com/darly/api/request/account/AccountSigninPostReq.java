package com.darly.api.request.account;

import lombok.Getter;

import java.util.List;

@Getter
public class AccountSigninPostReq {
    private List<Long> addresses;
    private String userNickname;
}
