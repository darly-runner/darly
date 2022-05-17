package com.darly.db.repository.event;

import com.darly.db.entity.event.UserEvent;
import com.darly.db.entity.event.UserEventId;
import com.darly.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, UserEventId> {
    List<UserEvent> findByUserEventId_User(User user);
}
