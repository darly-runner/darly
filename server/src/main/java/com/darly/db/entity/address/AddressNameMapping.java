package com.darly.db.entity.address;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class AddressNameMapping {
    private String addressName;

    @QueryProjection
    public AddressNameMapping(String addressName) {
        this.addressName = addressName;
    }
}
