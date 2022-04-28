package com.darly.api.request.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@ApiModel("AccountSigninPostRequest")
public class AccountSigninPostReq {
    @ApiModelProperty(name = "addresses", example = "[1,3,5]")
    private List<Long> addresses;
    @ApiModelProperty(name = "userNickname", example = "userNickname")
    private String userNickname;
}
