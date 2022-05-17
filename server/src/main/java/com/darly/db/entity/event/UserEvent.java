package com.darly.db.entity.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "tb_user_event")
public class UserEvent {
    @EmbeddedId
    private UserEventId userEventId;

    @Builder
    public UserEvent(UserEventId userEventId) {
        this.userEventId = userEventId;
    }
}
