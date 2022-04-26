package com.darly.api.response.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoAccount {
    private boolean hasEmail;
    private String email;
}
