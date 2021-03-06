package com.darly.db.entity.match;

import com.darly.db.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor
public class UserMatchId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;


    @Builder
    public UserMatchId(User user, Match match) {
        this.user = user;
        this.match = match;
    }
}
