package com.darly.api.response.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@ApiModel("KakaoAccount")
public class KakaoAccount {
    @ApiModelProperty(name="hasEmail", example="true")
    private boolean hasEmail;
    @ApiModelProperty(name="email", example="ssafy@ssafy.com")
    private String email;
}
