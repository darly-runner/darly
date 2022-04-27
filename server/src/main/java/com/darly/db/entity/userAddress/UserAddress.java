package com.darly.db.entity.userAddress;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name="tb_user_address")
public class UserAddress {
    @EmbeddedId
    private UserAddressId userAddressId;

    @Builder
    public UserAddress(UserAddressId userAddressId) {
        this.userAddressId = userAddressId;
    }
}
