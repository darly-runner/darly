package com.darly.db.entity.crew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_crew_address")
public class CrewAddress {
    @EmbeddedId
    CrewAddressId crewAddressId;
}
