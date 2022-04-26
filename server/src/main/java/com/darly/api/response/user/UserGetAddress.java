package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.address.Address;
import com.darly.db.entity.address.AddressNameMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel("UserGetAddress")
public class UserGetAddress extends BaseResponseBody {
    List<AddressNameMapping> addresses;

    public static UserGetAddress of(List<AddressNameMapping> addresses, Integer statusCode, String message) {
        return UserGetAddress.builder()
                .addresses(addresses)
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    @Builder
    public UserGetAddress(Integer statusCode, String message, List<AddressNameMapping> addresses) {
        super(statusCode, message);
        this.addresses = addresses;
    }
}
