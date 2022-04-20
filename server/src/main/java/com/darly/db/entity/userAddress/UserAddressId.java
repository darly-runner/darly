package com.darly.db.entity.userAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressId implements Serializable{
    private Long userId;
    private Long addressId;
}
