package com.darly.db.entity.crew;

import com.darly.db.entity.address.Address;
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
public class CrewAddressId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "crew_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Crew crew;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder
    public CrewAddressId(Long crewId, Long addressId) {
        this.crew = Crew.builder().crewId(crewId).build();
        this.address = Address.builder().addressId(addressId).build();
    }
}
