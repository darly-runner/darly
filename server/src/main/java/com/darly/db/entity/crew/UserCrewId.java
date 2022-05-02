package com.darly.db.entity.crew;

import com.darly.db.entity.user.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserCrewId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "crew_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Crew crew;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public UserCrewId(Long userId, Long crewId) {
        this.user = User.builder().userId(userId).build();
        this.crew = Crew.builder().crewId(crewId).build();
    }
}
