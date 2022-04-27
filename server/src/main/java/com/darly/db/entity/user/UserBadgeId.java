package com.darly.db.entity.user;

import com.darly.db.entity.badge.Badge;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserBadgeId implements Serializable {

    private User user;
    private Badge badge;

    public UserBadgeId() {}

    public UserBadgeId(User user, Badge badge) {
        this.user = user;
        this.badge = badge;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
