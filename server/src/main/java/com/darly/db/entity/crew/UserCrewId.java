package com.darly.db.entity.crew;

import com.darly.db.entity.user.User;
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
    private Long crewId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserCrewId(Long crewId, Long userId) {
        this.crewId = crewId;
        this.user = User.builder().userId(userId).build();
    }
}
