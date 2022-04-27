package com.darly.db.entity.address;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_address")
@Builder
public class Address {
    @Id
    private Long addressId;
    private String addressName;

}
