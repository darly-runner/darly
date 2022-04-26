package com.darly.db.entity.crew;

import com.darly.db.entity.User;
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
public class UserCrewId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserCrewId(Long crewId, Long userId) {
        this.crew = Crew.builder().crewId(crewId).build();
        this.user = User.builder().userId(userId).build();
    }
}
