package com.darly.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel("UserAddressPutRequest")
public class UserAddressPutReq {
    @ApiModelProperty(name="addresses", example="[1,3,5]")
    List<Long> addresses;

    @Builder
    public UserAddressPutReq(List<Long> addresses) {
        this.addresses = addresses;
    }
}
