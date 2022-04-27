package com.darly.db.entity.crew;

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
public class CrewAddressId implements Serializable {
    private Long crewId;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder
    public CrewAddressId(Long crewId, Long addressId) {
        this.crewId = crewId;
        this.address = Address.builder().addressId(addressId).build();
    }
}
