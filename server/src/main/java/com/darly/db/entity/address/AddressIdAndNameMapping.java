package com.darly.db.entity.address;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class AddressIdAndNameMapping {
    private String addressName;
    private Long addressId;

    @QueryProjection
    public AddressIdAndNameMapping(String addressName, Long addressId) {
        this.addressName = addressName;
        this.addressId = addressId;
    }
}
