package com.darly.db.entity.crew;

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
@Table(name = "tb_user_crew")
public class UserCrew {
    @EmbeddedId
    UserCrewId userCrewId;
}
