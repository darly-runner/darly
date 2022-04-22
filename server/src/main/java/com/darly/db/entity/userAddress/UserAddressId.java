package com.darly.db.entity.userAddress;

import com.darly.db.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressId implements Serializable{
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder
    public UserAddressId(Long userId, Long addressId){
        this.userId = userId;
        this.address = Address.builder().addressId(addressId).build();
    }
}
