package com.darly.api.response.account;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoAccount {
    private boolean hasEmail;
    private String email;
}
