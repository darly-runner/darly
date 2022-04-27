package com.darly.api.response.address;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.address.Address;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel("AddressesGetResponse")
public class AddressesGetRes extends BaseResponseBody {
    @ApiModelProperty(name="addresses", example="[주소1, 주소2, 주소3]")
    List<Address> addresses;

    public static AddressesGetRes of(List<Address> addresses, Integer statusCode, String message) {
        return AddressesGetRes.builder()
                .addresses(addresses)
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    @Builder
    public AddressesGetRes(Integer statusCode, String message, List<Address> addresses) {
        super(statusCode, message);
        this.addresses = addresses;
    }
}
