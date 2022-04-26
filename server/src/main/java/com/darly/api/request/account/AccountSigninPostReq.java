package com.darly.api.request.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel("AccountSinginPostRequest")
public class AccountSigninPostReq {
    @ApiModelProperty(name="addresses", example="[1,3,5]")
    private List<Long> addresses;
    @ApiModelProperty(name="userNickname", example="흐앙쥬금털썩")
    private String userNickname;

    @Builder
    public AccountSigninPostReq(List<Long> addresses, String userNickname) {
        this.addresses = addresses;
        this.userNickname = userNickname;
    }
}
