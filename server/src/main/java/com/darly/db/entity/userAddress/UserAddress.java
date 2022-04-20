package com.darly.db.entity.userAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_user_address")
public class UserAddress {
    @EmbeddedId
    private UserAddressId userAddressId;
}
