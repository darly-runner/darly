package com.darly.db.repository.event;

import com.darly.api.response.event.EventsGetRes;
import com.darly.db.entity.EventList;
import com.darly.db.entity.QEvent;
import com.darly.db.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QEvent qEvent = QEvent.event;
    QUser qUser = QUser.user;


    public List<EventList> findEvents() {
        return jpaQueryFactory.select(Projections.constructor(EventList.class, qEvent.eventId, qEvent.eventTitle, qEvent.user.userNickname, qEvent.eventDate))
                .from(qEvent, qUser)
                .where(qEvent.user.userId.eq(qUser.userId))
                .fetch();
    }
}
