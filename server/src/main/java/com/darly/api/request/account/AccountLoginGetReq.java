package com.darly.api.request.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@ApiModel("AccountLoginGetRequest")
public class AccountLoginGetReq {
    @ApiModelProperty(name="tokenId", example="token")
    private String tokenId;
}
