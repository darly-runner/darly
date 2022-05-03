package com.darly.db.entity.crew;


import com.darly.db.entity.address.Address;
import com.darly.db.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "tb_crew")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crewId;
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String crewName;
    private String crewDesc;
    private String crewNotice;
    private Character crewJoin;
    private String crewImage;
}
