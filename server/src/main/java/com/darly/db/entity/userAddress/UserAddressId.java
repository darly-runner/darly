package com.darly.db.entity.userAddress;

import com.darly.db.entity.address.Address;
import com.darly.db.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder
    public UserAddressId(Long userId, Long addressId) {
        this.user = User.builder().userId(userId).build();
        this.address = Address.builder().addressId(addressId).build();
    }
}
