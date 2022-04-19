package com.darly.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@IdClass(UserBadgeId.class)
@Table(name = "tb_user_badge")
public class UserBadge {

    @Id
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "BADGE_ID")
    private Badge badge;

    @Override
    public String toString() {
        return "UserBadge{" +
                "user=" + user +
                ", badge=" + badge +
                '}';
    }
}
